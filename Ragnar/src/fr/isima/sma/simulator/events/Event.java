package fr.isima.sma.simulator.events;

import java.util.ArrayList;

import fr.isima.sma.world.ActiveEntity;

public abstract class Event {
// TODO mettre un time to live et s'il tombe a zero il est fini
	
	protected int ttl; // Time to live of the event
	protected ArrayList<ActiveEntity> entities;	// The entities concerned with the event
	protected EventType type;		// The type of event
	
	/**
	 * Default constructor
	 */
	public Event() {
		this(new ArrayList<>(), EventType.Idle, 1);
	}
	
	/**
	 * Constructor
	 * @param entities entities list concerned with the event
	 * @param type the event type
	 */
	public Event(ArrayList<ActiveEntity> entities, EventType type, int ttl) {
		this.entities = entities;
		this.type = type;
		this.ttl = ttl;
	}
	
	/**
	 * Launch the event
	 */
	public void proceed() {
		
		this.ttl--;
	}

	/**
	 * @return the ttl
	 */
	public int getTtl() {
		return ttl;
	}

	/**
	 * @param ttl the ttl to set
	 */
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

	/**
	 * @return the entities
	 */
	public ArrayList<ActiveEntity> getEntities() {
		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(ArrayList<ActiveEntity> entities) {
		this.entities = entities;
	}

	/**
	 * @return the type
	 */
	public EventType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(EventType type) {
		this.type = type;
	}
	

}
