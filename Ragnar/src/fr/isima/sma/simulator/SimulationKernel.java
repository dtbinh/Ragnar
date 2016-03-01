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

import org.apache.commons.math3.random.MersenneTwister;

import fr.isima.sma.gui.ControlView.ButtonPressed;
import fr.isima.sma.gui.RagnarView;
import fr.isima.sma.resources.Properties;
import fr.isima.sma.simulator.events.Event;
import fr.isima.sma.world.ActiveEntity.AgentType;
import fr.isima.sma.world.ActiveEntity.LifeState;
import fr.isima.sma.world.City;
import fr.isima.sma.world.Humanoid;
import fr.isima.sma.world.Sector;
import fr.isima.sma.world.Sector.SectorType;
import fr.isima.sma.world.Super;
import fr.isima.sma.world.patterns.Console;

/*
 * 	MVC class
 */
public class SimulationKernel implements Observer {
	protected static VirtualClock 		c = new VirtualClock(Integer.valueOf(Properties.getInstance().getProperty("msPerTick")));
	protected static MersenneTwister	rand;			// Generateur aleatoire du SimulationKernel
	
	static { // Initialisation du generateur aleatoire
		rand = new MersenneTwister(Integer.valueOf(Properties.getInstance().getProperty("rand")));
	}
	
	/**
	 * @return the rand
	 */
	public static MersenneTwister getRand() {
		return rand;
	}
	/**
	 * @param rand the rand to set
	 */
	public static void setRand(MersenneTwister rand) {
		SimulationKernel.rand = rand;
	}

	protected List<Event> 				events;			// Evenements a faire
	private boolean 					gui;			// Activer / desactiver l'interface graphique
	// TODO s'abonner a toutes les entites de la city pour faire ajouter les events automatiquement

	protected boolean 					isRunning;		// Indique si la simulation est en cours
	private List<Event> 				newEvents;		// Nouveaux evenements ajoutes lors d'un tour
	
	// Controles de la simulation
	private boolean 					play;			// Lecture
	protected City 						ragnar;			// Ville principale de la simualation
	private boolean 					restart;		// Recommencer
	private boolean 					stop;			// Arret
	
	protected long 						time;			// Temps de simulation

	protected RagnarView				view;			// Vue

	/**
	 * Constructor with parameters
	 * @param c the city of the simulation
	 * @param v the view on the cuty
	 */
	public SimulationKernel(City c, RagnarView v) {
		ragnar = c;
		view = v;
		if(Boolean.valueOf(Properties.getInstance().getProperty("gui"))) {
			v.getControlView().addObserver(this);
		}
		events = new ArrayList<>();
		newEvents = new ArrayList<>();
		play = true;
		stop = false;
		restart = false;
		gui = Boolean.valueOf(Properties.getInstance().getProperty("gui"));
		
		for(List<Sector> ss : ragnar.getSectorByType()) {
			for(Sector s : ss) {
				s.addObserver(this);
			}
		}
	}

	/**
	 * Export the simulation results in a file
	 * @param filename the file in which export the results
	 */
	public void exportResults(String filename) {
		try(
				PrintWriter sortie = new PrintWriter (new BufferedWriter (new FileWriter (new File("results\\"+filename))));
		)
		{
		    sortie.println("################################################################");
		    sortie.println("################################################################");
		    sortie.println("");
		    
		    int number[] = new int[4];		    
		    int money[] = new int[4];
		    sortie.println("Vivants\n________________");
		    for(Humanoid h : ragnar.getActiveEntities().getAgents()) {
		    	number[h.getType().getValue()]++;
		    	money[h.getType().getValue()]+=h.getMoney();
			    sortie.println(h.toResult());
		    }
		    sortie.println("----------------------------------------------------------------");
		    for(AgentType a : AgentType.values()) {
		    	sortie.print(a+"\t"+number[a.getValue()]+"\t");
		    }
		    sortie.println("\n----------------------------------------------------------------");
		    sortie.println("");

		    number = new int[4];
		    sortie.println("Morts\n________________");
		    for(Humanoid h : ragnar.getDeadAgents()) {
		    	number[h.getType().getValue()]++;
		    	money[h.getType().getValue()]+=h.getMoney();
			    sortie.println(h.toResult());
		    }
		    sortie.println("----------------------------------------------------------------");
		    for(AgentType a : AgentType.values()) {
		    	sortie.print(a+"\t"+number[a.getValue()]+"\t");
		    }
		    sortie.println("\n----------------------------------------------------------------");
		    sortie.println("");
		    sortie.println("----------------------------------------------------------------");
		    for(AgentType a : AgentType.values()) {
		    	sortie.print(a+"\t"+money[a.getValue()]+"\t");
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

	/**
	 * @return the gui state
	 */
	public boolean isGui() {
		return gui;
	}

	/**
	 * @return the play state
	 */
	public boolean isPlay() {
		return play;
	}
	
	/**
	 * @return the restart state
	 */
	public boolean isRestart() {
		return restart;
	}

	/**
	 * @return the stop state
	 */
	public boolean isStop() {
		return stop;
	}

	public void setGui(boolean gui) {
		this.gui = gui;
	}

	/**
	 * @param play the play state to set
	 */
	public void setPlay(boolean play) {
		this.play = play;
	}

	/**
	 * @param restart the restart state to set
	 */
	public void setRestart(boolean restart) {
		this.restart = restart;
	}

	/**
	 * @param stop the stop state to set
	 */
	public void setStop(boolean stop) {
		this.stop = stop;
	}

	/**
	 * Shuffle entities and events, then proceed events and entities if the clock allowa it
	 */
	public void simulate() {
		isRunning = true;
		Collections.shuffle(events); // Les events sont fait dans un autre ordre
		if(SimulationKernel.c.ticTac()) {
			for (Event event : events) {
				event.proceed(); // Resout l'evenement
			}
			
			List<Humanoid> agents = new ArrayList<Humanoid>(ragnar.getActiveEntities().getAgents());
			Collections.shuffle(agents);
			
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
				//Console.println("Tentative de braquage !");
				break;
			case Release:
				//Console.println("Tentative de libération !");
				break;
			case Fight:
				//Console.println("Combat en cours !");
				break;
				
			default:
				break;
			}
		}
	}

	/*
	 *	Refresh the event list with the new events
	 */
	private void updateEvents() {
		List<Event> newListe = new ArrayList<Event>();
		for(Event e : events) {
			if(e.getTtl()>=0) {
				newListe.add(e);
			}
		}
		
		events = newListe;
		
		for(Event e : newEvents) {
			events.add(e);
		}
		
		newEvents = null;
		newEvents = new ArrayList<>(); // On vide cette liste
		
		newListe = new ArrayList<Event>();
		
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
				} else {
					newListe.add(e);
					Super.addEvent(e);
				}
			}
		}
		events.addAll(newListe);
		Super.removeAllEvents();
		Super.addAllEvents(events);
	}
	
}
