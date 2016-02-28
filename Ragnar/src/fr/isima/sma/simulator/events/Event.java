package fr.isima.sma.simulator.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import fr.isima.sma.world.Hero;
import fr.isima.sma.world.Humanoid;
import fr.isima.sma.world.Sector;
import fr.isima.sma.world.Super;
import fr.isima.sma.world.Vilain;
import fr.isima.sma.world.ActiveEntity.AgentType;
import fr.isima.sma.world.ActiveEntity.LifeState;

public class Event {
	protected static Random rand = new Random();
	
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
				Super.removeEvent(this);
			}
		} else {	// faire l'event
			switch (type) {
			case Robery:
				(new Action() {

					@Override
					public void live(Event e) {	//TODO a implementer pout les ROBERY
						Sector here = e.getSector();
						
						if(here.getNumberHero() > 0) { // Bagarre
							if(e.getTtl() == 1) { // Resolution du braquage
								// Simuler le combat
								
							}
							
						} else { // Pas de bagarre
							if(e.getTtl() == 1) { // Fin de l'event
								// Ils repartent en se partageant les gains
								int butin = (int)(here.getMoneyAvailable() / here.getNumberVilain());
								for(Humanoid h : e.getEntities()) {
									if(h.getType() == AgentType.VILAIN) {
										h.setMoney(h.getMoney() + butin); // Additionne avec ce qu'il avait
									}
								}
								here.setMoneyAvailable(0); // La banque est vide
							}
						}
						
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
	
	public AgentType resolveFight() {
		AgentType winner = AgentType.HERO; // De base, les gentils gagnent toujours
		int forceHeros = 0;
		int forceVilains = 0;
		
		double heroWinProba = 0.5; // De base
		
		List<Hero> heroes = new ArrayList<>();
		List<Vilain> vilains = new ArrayList<>();
		
		// On additionne les forces de chaque factions
		for(Humanoid h : this.entities) {
			if(h.getType() == AgentType.HERO) {
				forceHeros += ((Super)h).getForce();
				heroes.add((Hero) h);
			} else if (h.getType() == AgentType.VILAIN) {
				forceVilains += ((Super)h).getForce();
				vilains.add((Vilain) h);
			}
		}
		
		// Shuffle les factions pour ensuite determiner les morts (si il y en a)
		Collections.shuffle(heroes);
		Collections.shuffle(vilains);
		
		// Modification de la proba et deces des super
		if(forceHeros > forceVilains) {
			heroWinProba = 0.75;
			
			if (Math.abs(Event.rand.nextGaussian()) < 0.2) { // Un seul meurt
				vilains.get(0).setAlive(LifeState.DEAD); // On tue par accident le premier
			}
			
		} else if (forceHeros < forceVilains) {
			heroWinProba = 0.25;
			
			if (Math.abs(Event.rand.nextGaussian()) < 0.2) { // Un seul meurt
				heroes.get(0).setAlive(LifeState.DEAD); // On tue le premier
			}
			
		}
		
		// Determination de la faction gagnante
		winner = (Math.abs(Event.rand.nextGaussian()) < heroWinProba)? AgentType.HERO : AgentType.VILAIN;
		
		return winner;
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
