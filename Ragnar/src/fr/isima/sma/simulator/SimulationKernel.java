package fr.isima.sma.simulator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import fr.isima.sma.world.ActiveEntity.AgentType;
import fr.isima.sma.world.ActiveEntity.LifeState;
import fr.isima.sma.world.Bank;
import fr.isima.sma.world.City;
import fr.isima.sma.world.HeadQuarter;
import fr.isima.sma.world.Humanoid;
import fr.isima.sma.world.Sector;
import fr.isima.sma.world.Sector.SectorType;
import fr.isima.sma.world.patterns.Console;

/*
 * 	MVC class
 */
public class SimulationKernel implements Observer {

	protected long 						time;			// temps de simulation
	protected boolean 					isRunning;		// la simu est en cours ?

	protected City 						ragnar;			// ville de la simulation
	protected RagnarView				view;			// vue

	protected List<Event> 			events;
	private List<Event> newEvents;
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
		newEvents = new ArrayList<>();
		play = true;
		stop = false;
		restart = false;
		gui = Boolean.valueOf(Properties.getInstance().getProperty("gui"));

		for(Sector s : ragnar.getSectorByType().get(SectorType.Bank.getValue())) {
			Bank b = (Bank) s;
			b.addObserver(this);
		}
		for(Sector s : ragnar.getSectorByType().get(SectorType.HeroHQ.getValue())) {
			HeadQuarter b = (HeadQuarter) s;
			b.addObserver(this);
		}
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
		Collections.shuffle(events, rand); // Les events sont fait dans un autre ordre
		if(SimulationKernel.c.ticTac()) {
			for (Event event : events) {
				event.proceed(); // Resout l'evenement
			}
			
			List<Humanoid> agents = new ArrayList<Humanoid>(ragnar.getActiveEntities().getAgents());
			Collections.shuffle(agents, rand);
			
			for (Humanoid hum : agents) {
				hum.vieillissement();
				if(hum.getAlive()!= LifeState.DEAD && hum.getInvolved() == null)
					hum.live();
			}
			ragnar.live();
			updateEvents();
		}
		isRunning = false;
	}

	/*
	 *	mise a jour de la liste d'event 
	 */
	private void updateEvents() {
		List<Event> newListe = new ArrayList<Event>();
		for(Event e : events) {
			if(e.getTtl()>=0) {
				newListe.add(e);
			}
		}
		events = newListe;
		
		for(Event e : newListe) {
			// recherche d'un event sur le lieu
			for(Event cur : events) {
				if(cur.getSector().getLocation().getLocationX()==e.getSector().getLocation().getLocationX() &&
					cur.getSector().getLocation().getLocationX()==e.getSector().getLocation().getLocationX()) 
				{
					cur.setEntities(e.getEntities());
					for(Humanoid h : cur.getEntities()) {
						h.setInvolved(cur);
					}
				}
			}
		}
	}

	@Override
	public void update(Observable obs, Object arg) {
		if ( arg instanceof ButtonPressed ) {
			switch ((ButtonPressed)arg) {	// bouton
				case PLAY:
					setPlay(true);
					setStop(false);
					Console.println(arg.toString());
					break;
				case PAUSE:
					setPlay(false);
					Console.println(arg.toString());
					break;
				case STOP:
					setRestart(false);
					setStop(true);
					Console.println(arg.toString());
					break;
				case EXPORT:
					setPlay(false);
					exportResults("manually_exported.out");
					Console.println(arg.toString());
					break;
				case RESTART:
					setStop(false);
					setRestart(true);
					Console.println(arg.toString());
					break;
				case GUI:
					setGui(!isGui());
					this.view.setVisibleGUI(isGui());
					break;
	
				default:
					break;
			}
		} else if(arg instanceof Event) {	// evenements
			Event event = (Event) arg;
			newEvents.add(event);
			switch (event.getType()) {
			case Robery:
				Console.println("Tentative de braquage !");
				break;
			case Release:
				Console.println("Tentative de libération !");
				break;

			default:
				break;
			}
		}
	}
	
	public void exportResults(String filename) {
		try(
				PrintWriter sortie = new PrintWriter (new BufferedWriter (new FileWriter (new File("results\\"+filename))));
		)
		{
		    sortie.println("################################################################");
		    sortie.println("################################################################");
		    sortie.println("");
		    
		    int number[] = new int[4];
		    sortie.println("Vivants\n________________");
		    for(Humanoid h : ragnar.getActiveEntities().getAgents()) {
		    	number[h.getType().getValue()]++;
			    sortie.println(h.toResult());
		    }
		    sortie.println("----------------------------------------------------------------");
		    for(AgentType a : AgentType.values()) {
		    	sortie.print(a+"\t"+number[a.getValue()]);
		    }
		    sortie.println("----------------------------------------------------------------");
		    sortie.println("");

		    number = new int[4];
		    sortie.println("Morts\n________________");
		    for(Humanoid h : ragnar.getDeadAgents()) {
		    	number[h.getType().getValue()]++;
			    sortie.println(h.toResult());
		    }
		    sortie.println("----------------------------------------------------------------");
		    for(AgentType a : AgentType.values()) {
		    	sortie.print(a+"\t"+number[a.getValue()]+"\t");
		    }
		    sortie.println("\n----------------------------------------------------------------");
		    sortie.println("");
		    
		    number = new int[ragnar.getSectorByType().size()];
		    for(List<Sector> list : ragnar.getSectorByType()) {
			    sortie.println(list.get(0).getType() + "\n________________\nTYPE\tMONEY");
			    for(Sector sec : list) {
			    	number[sec.getType().getValue()] += sec.getMoneyAvailable();
				    sortie.println(sec.toResult());
			    }
			    sortie.println("");
		    }
		    sortie.println("----------------------------------------------------------------");
		    for(SectorType a : SectorType.values()) {
		    	sortie.print(a+"\t"+number[a.getValue()]+"\t");
		    }
		    sortie.println("\n----------------------------------------------------------------");
		    sortie.println("");

		    sortie.println("################################################################");
		    sortie.print("################################################################");
		} catch(IOException ex) {
		    ex.printStackTrace();
		}
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
