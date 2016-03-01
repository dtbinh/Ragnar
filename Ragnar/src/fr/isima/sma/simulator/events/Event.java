package fr.isima.sma.simulator.events;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.random.MersenneTwister;

import fr.isima.sma.resources.Properties;
import fr.isima.sma.world.ActiveEntity.AgentType;
import fr.isima.sma.world.Humanoid;
import fr.isima.sma.world.Sector;
import fr.isima.sma.world.Super;
import fr.isima.sma.world.Vilain;
import fr.isima.sma.world.patterns.Console;

public class Event {
	protected static MersenneTwister rand;	// Generateur aleatoire pour les evenements
	
	static {
		rand = new MersenneTwister(Integer.valueOf(Properties.getInstance().getProperty("rand")));
	}
	
	protected List<Humanoid> 	entities;	// Entitees concernees par l'evenement
	protected Sector 			sector;		// Secteur de l'evenement
	protected int 				ttl; 		// Temps de vie de l'evements
	protected EventType 		type;		// Type d'evenement
	
	/**
	 * Constructor
	 * @param sector the sector concerned with the event
	 * @param entities entities list concerned with the event
	 * @param type the event type
	 * @param ttl the event time to live
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
	 * Add an entity in the current event
	 * @param humanoid The humanoid to add in this event
	 */
	public void addEntity(Humanoid humanoid) {
		this.entities.add(humanoid);
		humanoid.setInvolved(this);
	}
	
	/**
	 * @return the entities
	 */
	public List<Humanoid> getEntities() {
		return entities;
	}
	
	/**
	 * @return the sector
	 */
	public Sector getSector() {
		return sector;
	}
	
	/**
	 * @return the ttl
	 */
	public int getTtl() {
		return ttl;
	}

	/**
	 * @return the type
	 */
	public EventType getType() {
		return type;
	}

	/**
	 * Bring the entities back to home by creating the path to it, it's not an event
	 * @param heroes the entities to bring back home
	 */
	protected void goHome(List<Super> heroes) {
		for(Super h : heroes) {
			// On cree leur path comme ca ils sauront qu'ils doivent y aller tous seuls
			h.findPath(	h.getLocation().getLocationX(),
						h.getLocation().getLocationY(), 
						h.getHome().getLocation().getLocationX(), 
						h.getHome().getLocation().getLocationY(), 
						Humanoid.getCity().getWalkableLegit());
		}
	}

	/**
	 * Launch the event and resolve it
	 */
	public void proceed() {
		if(this.ttl == 0) {	// on a fini l'event
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
					public void live(Event e) {
						
						if(e.getTtl() == 1) { // Fin de l'event
							Sector here = e.getSector();
							boolean success = true; // Emporte les gains ?
							
							if(here.getNumberHero() > 0) { // Bagarre !
								AgentType winner = e.resolveFight();
								
								if(winner == AgentType.HERO) {
									Console.println(Humanoid.getCity().getDate() + " Les héros ont empêché le braquage");
									
									// Emprisonnement
									boolean found = false;
									Sector toGo = null;
									
									// Recherche du QG d'un des heros
									for(int i = 0; i < e.getEntities().size() && !found; i++) {
										Humanoid humanoid = e.getEntities().get(i);
										if((!found) && (humanoid.getType() == AgentType.HERO)) {
											toGo = humanoid.getHome();
											found = true;
										}
									}
//									
//									// On teleporte tout le monde dans le QG et on bloque les vilains
									if(found && toGo!=null) {
										for (Humanoid humanoid : e.getEntities()) {
											if(humanoid.getType() == AgentType.VILAIN) {
												((Vilain) humanoid).setCaptured(true);
											}
											humanoid.setLocation(	toGo.getLocation().getLocationX(),
																	toGo.getLocation().getLocationY());
											
										}
									}
									success = false;
								} else {
									// Creation de la liste des supers
									List<Super> heroes = new ArrayList<>();
									for(Humanoid h : e.getEntities()) {
										if(h.getType() == AgentType.HERO) {
											heroes.add((Super) h);
										}
									}
									
									// On les envoie chez eux
									e.goHome(heroes);
								}
							}
							
							if(success) {
								if(here.getNumberVilain() > 0) { // Le seul braqueur peut mourir donc attention
									Console.println(Humanoid.getCity().getDate() + " Le braquage est réussi !");
									
									// Determination de la part qu'il vont prendre en banque, entre 40 et 60%
									double part = (double)(((double)Event.rand.nextInt(20) + (double)40)/(double)100);
									
									// Distribution des gains
									int butin = (int)((int)(part * here.getMoneyAvailable()) / here.getNumberVilain());
									for(Humanoid h : e.getEntities()) {
										if(h.getType() == AgentType.VILAIN) {
											h.setMoney(h.getMoney() + butin); // Additionne avec ce qu'il avait
											here.setMoneyAvailable(here.getMoneyAvailable() - butin); // On vide la banque petit a petit
										}
									}
								}
							}
							
							// Remise a zero de la preparation
							for(Humanoid humanoid : e.getEntities()) {
								if(humanoid.getType() == AgentType.VILAIN) {
									((Vilain) humanoid).resetRobberyPrep();
								}
							}
							
						}
						
					}
				}).live(this);
				break;
			case Release: // TODO remove parce que non utilise
				(new Action() {

					@Override
					public void live(Event e) {
	
						
					}
				}).live(this);
				break;
			case BringToPrison: // TODO remove car non utilise
				(new Action() {

					@Override
					public void live(Event e) {
						
						if(ttl == 1) {
							boolean found = false;
							Sector toGo = null;
							
							// Recherche du QG
							for (Humanoid humanoid : e.getEntities()) {
								if((!found) && (humanoid.getType() == AgentType.HERO)) {
									toGo = humanoid.getHome();
									found = true;
								}
							}
							
							// On deplace tout le monde dans le QG et on bloque les vilains
							for (Humanoid humanoid : e.getEntities()) {
								humanoid.setLocation(	toGo.getLocation().getLocationX(),
														toGo.getLocation().getLocationY());
								if(humanoid.getType() == AgentType.VILAIN) {
									((Vilain) humanoid).setCaptured(true);
								}
							}
						}
						
					}
				}).live(this);
				break;
			case Fight:
				(new Action() {
					
					@Override
					public void live(Event e) {
						if(e.getTtl() == 1) {
							AgentType winner = e.resolveFight();
							
							if(winner == AgentType.HERO) {
								Console.println(Humanoid.getCity().getDate() + " Les héros ont gagné un combat !");
								
								// Les heros gagnent, les vilains s'en vont
								List<Super> villains = new ArrayList<>();
								for(Humanoid h : e.getEntities()) {
									if(h.getType() == AgentType.VILAIN) {
										villains.add((Super) h);
//										// TODO corriger
//										double prep = ((Vilain) h).getRobberyPrep();
//										double diminuer = (double)(Event.rand.nextInt(25)); // Diminution entre 0 et 25 de la preparation
//										double newPrep = prep - diminuer;
//										((Vilain) h).setRobberyPrep((newPrep>=0)?newPrep:0);
										
										//DONE
										((Vilain) h).setRobberyPrep((double)(Event.rand.nextInt(25)));
									}
								}
								
								// On les envoie chez eux
								e.goHome(villains);
								
							} else {
								Console.println(Humanoid.getCity().getDate() + " Les vilains ont gagné un combat !");
								
								// Les vilains gagnent, les heros s'en vont
								List<Super> heroes = new ArrayList<>();
								for(Humanoid h : e.getEntities()) {
									if(h.getType() == AgentType.HERO) {
										if(Event.rand.nextDouble() < 0.1/Integer.valueOf(Properties.getInstance().getProperty("daysperyear"))) {
											// il meurt	
											(Humanoid.getCity()).becomeDead(h);
											Console.println(Humanoid.getCity().getDate() + " " + h.toString() + " est tombé(e) au combat.");
										} else {
											heroes.add((Super) h);
										}
									}
								}
								
								// On les envoie chez eux
								e.goHome(heroes);									
								
							}
						}
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
	 * Resolve the fight between Hereos and Villains
	 * @return the entity type who wins the fight
	 */
	public AgentType resolveFight() {
		AgentType winner = AgentType.HERO; // De base, les gentils gagnent toujours
		int forceHeros = 0;
		int forceVilains = 0;
		
		double heroWinProba = 0.5; // De base
		
		// On additionne les forces de chaque factions
		for(Humanoid h : this.entities) {
			if(h.getType() == AgentType.HERO) {
				forceHeros += ((Super)h).getForce();
			} else if (h.getType() == AgentType.VILAIN) {
				forceVilains += ((Super)h).getForce();
			}
		}
		
		// Determination de la probabilite de victoire
		if(forceHeros > forceVilains) {
			heroWinProba = 0.75;			
		} else if (forceHeros < forceVilains) {
			heroWinProba = 0.25;
		}
		
		// Determination de la faction gagnante
		winner = (Event.rand.nextDouble() < heroWinProba)? AgentType.HERO : AgentType.VILAIN;
		
		return winner;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(List<Humanoid> entities) {
		this.entities = entities;
	}

	/**
	 * @param sector the sector to set
	 */
	public void setSector(Sector sector) {
		this.sector = sector;
	}

	/**
	 * @param ttl the ttl to set
	 */
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(EventType type) {
		this.type = type;
	}
	

}
