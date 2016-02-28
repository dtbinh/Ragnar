package fr.isima.sma.simulator.events;

/*
 * 	PATTERN state pour la gestion des évènements
 */

public abstract class Action {
	public abstract void live(Event e);
}
