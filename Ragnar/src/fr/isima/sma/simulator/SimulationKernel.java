package fr.isima.sma.simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import fr.isima.sma.gui.RagnarView;
import fr.isima.sma.resources.Properties;
import fr.isima.sma.simulator.events.Event;
import fr.isima.sma.world.ActiveEntity;
import fr.isima.sma.world.City;

/*
 * 	MVC class
 */
public class SimulationKernel {

	protected long 						time;			// temps de simulation
	protected boolean 					isRunning;		// la simu est en cours ?

	protected City 						ragnar;			// ville de la simulation
	protected RagnarView				view;			// vue

	protected ArrayList<Event> 			events;
	// TODO s'abonner a toutes les entites de la city pour faire ajouter les events automatiquement
	protected static Random 			rand = new Random();
	protected static VirtualClock 		c = new VirtualClock(Integer.valueOf(Properties.getInstance().getProperty("msPerTick")));

	public SimulationKernel(City c, RagnarView v) {
		ragnar = c;
		view = v;
		events = new ArrayList<>();
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
				event.Proceed(); // Launch the event
				clearEvents(); // Clear the next events
				events.remove(0); // Remove this event
			}
			for (int i = 0 ; i < ragnar.getActiveEntities().size() ; i++) {
				ragnar.getActiveEntities().getAgent(i).live();
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

	// TODO methode pour grouper des heros / des vilains et mettre ce groupe dans la liste
	// Comme ca les evenements se font sur les groupes

}
