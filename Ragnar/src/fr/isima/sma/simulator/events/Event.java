package fr.isima.sma.simulator.events;

import java.util.List;

import fr.isima.sma.world.Humanoid;
import fr.isima.sma.world.Sector;

public class Event {
// TODO mettre un time to live et s'il tombe a zero il est fini
	
	protected int ttl; // Time to live of the event
	protected List<Humanoid> entities;	// The entities concerned with the event
	protected EventType type;		// The type of event
	protected Sector sector;		// The sector of event
	
	/**
	 * Constructor
	 * @param entities entities list concerned with the event
	 * @param type the event type
	 */
	public Event(Sector sector, List<Humanoid> entities, EventType type, int ttl) {
		this.entities = entities;
		this.type = type;
		this.ttl = ttl;
		this.setSector(sector);
		for(Humanoid h : entities) {
			h.setInvolved(this);
		}
	}
	
	/**
	 * Launch the event
	 */
	public void proceed() {
		if(ttl==0) {	// on a fini l'event
			for(Humanoid h : entities) {
				h.setInvolved(null);
				h.setWantRobery(false);
				h.setWantRelease(false);
				Humanoid.removeEvent(this);
			}
		} else {	// faire l'event
			switch (type) {
			case Robery:
				(new Action() {

					@Override
					public void live(Event e) {	//TODO a implementer pout les ROBERY
	
						
					}
				}).live(this);
				break;
			case Release:
				(new Action() {

					@Override
					public void live(Event e) {	//TODO a implementer pout les RELEASE
	
						
					}
				}).live(this);
				break;
			case BringToPrison:
				(new Action() {

					@Override
					public void live(Event e) {	//TODO a implementer pout les BRINGTOPRISON
	
						
					}
				}).live(this);
				break;

			default:
				break;
			}
		}
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
	public List<Humanoid> getEntities() {
		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(List<Humanoid> entities) {
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

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}
	

}
