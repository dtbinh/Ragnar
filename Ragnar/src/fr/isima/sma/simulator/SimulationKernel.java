package fr.isima.sma.simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import fr.isima.sma.world.*;
import fr.isima.sma.gui.CityView;
import fr.isima.sma.simulator.events.*;

/*
 * 	Classe correspondant au contrôleur du MVC
 */
public class SimulationKernel {
	
	protected long 						time;			// temps de simulation
	protected boolean 					isRunning;	//
	
	protected City 						ragnar;			// ville de la simulation
	protected CityView					view;			// vue
	
	protected ArrayList<ActiveEntity> 	activeEntities;
	protected ArrayList<Event> 			events;
	
	protected static Random 			rand = new Random();
	protected static VirtualClock 		c = new VirtualClock(2000); 

	public SimulationKernel(City c, CityView v) {
		ragnar = c;
		view = v;
		activeEntities = new ArrayList<ActiveEntity>();
	}

	/**
	 * @return the activeEntities
	 */
	public ArrayList<ActiveEntity> getActiveEntities() {
		return activeEntities;
	}

	/**
	 * @param activeEntities the activeEntities to set
	 */
	public void setActiveEntities(ArrayList<ActiveEntity> activeEntities) {
		this.activeEntities = activeEntities;
	}
	
	public void shuffleEntities() {
		Collections.shuffle(this.activeEntities, SimulationKernel.rand);
		
		System.out.println("Dans la liste :");
		for(ActiveEntity e : activeEntities) {
			System.out.println(e);
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
		// TODO gestion des event
		//this.shuffleEntities();
		if(SimulationKernel.c.ticTac()) {
			ragnar.live();
			for(ActiveEntity e : activeEntities) {
				e.live();
			}
		}
	}
	
	// TODO methode pour grouper des heros / des vilains et mettre ce groupe dans la liste
	// Comme ca les evenements se font sur les groupes
	
}
