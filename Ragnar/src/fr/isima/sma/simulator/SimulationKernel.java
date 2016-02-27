package fr.isima.sma.simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import fr.isima.sma.gui.ControlView.ButtonPressed;
import fr.isima.sma.gui.RagnarView;
import fr.isima.sma.resources.Properties;
import fr.isima.sma.simulator.events.Event;
import fr.isima.sma.world.ActiveEntity;
import fr.isima.sma.world.ActiveEntity.LifeState;
import fr.isima.sma.world.City;
import fr.isima.sma.world.Humanoid;
import fr.isima.sma.world.patterns.Console;
import fr.isima.sma.world.patterns.MyObservable;

/*
 * 	MVC class
 */
public class SimulationKernel implements Observer {

	protected long 						time;			// temps de simulation
	protected boolean 					isRunning;		// la simu est en cours ?

	protected City 						ragnar;			// ville de la simulation
	protected RagnarView				view;			// vue

	protected ArrayList<Event> 			events;
	private boolean play;
	private boolean stop;
	private boolean restart;
	private boolean gui;
	// TODO s'abonner a toutes les entites de la city pour faire ajouter les events automatiquement
	protected static Random 			rand = new Random(123);
	protected static VirtualClock 		c = new VirtualClock(Integer.valueOf(Properties.getInstance().getProperty("msPerTick")));

	public SimulationKernel(City c, RagnarView v) {
		ragnar = c;
		view = v;
		v.getControlView().addObserver(this);
		events = new ArrayList<>();
		play = true;
		stop = false;
		restart = false;
		gui = Boolean.valueOf(Properties.getInstance().getProperty("gui"));
	}

	/**
	 * @return the rand
	 */
	public static Random getRand() {
		return rand;
	}

	/**
	 * @param rand the rand to set
	 */
	public static void setRand(Random rand) {
		SimulationKernel.rand = rand;
	}

	/**
	 * Shuffle entities and run them if the clock allows it
	 */
	public void simulate() {
		isRunning = true;
		Collections.shuffle(events); //
		if(SimulationKernel.c.ticTac()) {
			for (Event event : events) {
				event.proceed(); // Launch the event
				clearEvents(); // Clear the next events
				events.remove(0); // Remove this event
			}
			List<Humanoid> agents = new ArrayList<Humanoid>(ragnar.getActiveEntities().getAgents());
			Collections.shuffle(agents, rand);
			
			for (Humanoid hum : agents) {
				hum.vieillissement();
				if(hum.getAlive()!= LifeState.DEAD)
					hum.live();
			}
			ragnar.live();
		}
		isRunning = false;
	}

	/**
	 * Clear the events that have at least one entity from the first event
	 * @param event the event which has been proceed
	 */
	private void clearEvents() {
		if(events.size() > 0) {
			Event event = events.get(0);
			ArrayList<ActiveEntity> eventEntities  = event.getEntities();

			// Reverse to avoid problems when deleting
			for (int i = events.size()-1; i > 0; i--) {
				boolean stop = false;

				Event e = events.get(i);
				for (int a = 0; a < eventEntities.size() && !stop; a++) {
					ActiveEntity actE = eventEntities.get(a);
					if(e.getEntities().contains(actE)) {
						events.remove(i);
						stop = true;
					}
				}
			}
		}
	}

	@Override
	public void update(Observable obs, Object arg) {
		if ( MyObservable.class.isInstance(obs) ) {
			switch ((ButtonPressed)arg) {
				case PLAY:
					setPlay(true);
					Console.println(arg.toString());
					break;
				case PAUSE:
					setPlay(false);
					Console.println(arg.toString());
					break;
				case STOP:
					setStop(true);
					Console.println(arg.toString());
					break;
				case EXPORT:
					setPlay(false);
					exportResults();
					Console.println(arg.toString());
					break;
				case RESTART:
					setStop(true);
					setRestart(true);
					Console.println(arg.toString());
					break;
				case GUI:
					setGui(!isGui());
					Console.println(arg.toString());
					break;
	
				default:
					break;
			}
		}
	}
	
	public void exportResults() {
		
	}

	public boolean isPlay() {
		return play;
	}

	public void setPlay(boolean play) {
		this.play = play;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isRestart() {
		return restart;
	}

	public void setRestart(boolean restart) {
		this.restart = restart;
	}

	public boolean isGui() {
		return gui;
	}

	public void setGui(boolean gui) {
		this.gui = gui;
	}

	// TODO methode pour grouper des heros / des vilains et mettre ce groupe dans la liste
	// Comme ca les evenements se font sur les groupes

}
