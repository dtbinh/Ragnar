package fr.isima.sma.simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.function.Predicate;

import fr.isima.sma.world.*;
import fr.isima.sma.gui.CityView;
import fr.isima.sma.simulator.events.*;

/*
 * 	MVC class
 */
public class SimulationKernel {
	
	protected long 						time;			// temps de simulation
	protected boolean 					isRunning;		// la simu est en cours ?
	
	protected City 						ragnar;			// ville de la simulation
	protected CityView					view;			// vue
	
	protected ArrayList<Event> 			events;
	// TODO s'abonner a toutes les entites de la city pour faire ajouter les events automatiquement
	protected static Random 			rand = new Random();
	protected static VirtualClock 		c = new VirtualClock(2000); 

	public SimulationKernel(City c, CityView v) {
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
		
		for (Event event : events) {
			event.Proceed(); // Launch the event
			clearEvents(); // Clear the next events
			events.remove(0); // Remove this event
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
