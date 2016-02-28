package fr.isima.sma.simulator.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.random.MersenneTwister;

import fr.isima.sma.world.ActiveEntity.AgentType;
import fr.isima.sma.resources.Properties;
import fr.isima.sma.world.Hero;
import fr.isima.sma.world.Humanoid;
import fr.isima.sma.world.Sector;
import fr.isima.sma.world.Super;
import fr.isima.sma.world.Vilain;
import fr.isima.sma.world.patterns.Console;

public class Event {
	protected static MersenneTwister rand;
	
	static {
		rand = new MersenneTwister(Integer.valueOf(Properties.getInstance().getProperty("rand"))); // TODO remove the seed at the end
	}
	
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
									Console.println("Les heros ont empeche le braquage");
									// TODO evenement emprisonnement
									
									//new Event(here, heroesAndVilains, EventType.BringToPrison, 20) // Il faudra changer le ttl a 1 quand ce sera bon
									
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
									Console.println("Le braquage est reussi");
									
									int butin = (int)(here.getMoneyAvailable() / here.getNumberVilain());
									for(Humanoid h : e.getEntities()) {
										if(h.getType() == AgentType.VILAIN) {
											h.setMoney(h.getMoney() + butin); // Additionne avec ce qu'il avait
										}
									}
									here.setMoneyAvailable(0); // La banque est vide
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
						/* Version pas mal mais compliquee a gerer */
						
//						boolean found = false;
//						Sector toGo = null;
//						
//						// Creation du groupe et ajout des individus
//						Group heroesAndVilains = new Group(Group.GroupType.WITHPRISONERS, e.getSector().getLocation());
//						for (Humanoid humanoid : e.getEntities()) {
//							heroesAndVilains.add(humanoid);
//							
//							// Je cherche ou est le qg
//							if((heroesAndVilains.isPathNull() == true) && (!found) && (humanoid.getType() == AgentType.HERO)) {
//								toGo = humanoid.getHome();
//								found = true;
//							}
//						}
//						
//						// Creation du chemin
//						if(heroesAndVilains.isPathNull()) {
//							heroesAndVilains.findPath(	e.getSector().getLocation().getLocationX(),
//														e.getSector().getLocation().getLocationY(),
//														toGo.getLocation().getLocationX(),
//														toGo.getLocation().getLocationY(),
//														Humanoid.getCity().getWalkableLegit());
//						}
//						
//						// Parcours du chemin
//						if(heroesAndVilains.getPathStep() >= 0 && heroesAndVilains.getPathStep() < heroesAndVilains.getPath().length) {
//							// On fait avancer le groupe
//							heroesAndVilains.setLocation(	heroesAndVilains.getPath()[heroesAndVilains.getPathStep()][0],
//															heroesAndVilains.getPath()[heroesAndVilains.getPathStep()][1]);
//							heroesAndVilains.setPathStep(heroesAndVilains.getPathStep() + 1);
//						}
//						
//						// A destination
//						if( !(heroesAndVilains.getPathStep() < heroesAndVilains.getPath().length)) {
//							// TODO set captured les vilains
//							
//							heroesAndVilains.setPath(null);
//							heroesAndVilains.setPathStep(-1);
//						}
						
						/* version classique et bourrin mais qui marche */
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
								Console.println("Les heros ont gagne un combat !");
								// TODO evenement emprisonnement
								// new Event(here, heroesAndVilains, EventType.BringToPrison, 20)
							} else {
								Console.println("Les vilains ont gagne un combat");
								
								// Les vilains gagnent, les heros s'en vont
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
				vilains.get(0).becomeDead();
				vilains.remove(0);
			}
			
		} else if (forceHeros < forceVilains) {
			heroWinProba = 0.25;
			
			if (Math.abs(Event.rand.nextGaussian()) < 0.2) { // Un seul meurt
				heroes.get(0).becomeDead();
				heroes.remove(0);
			}
			
		}
		
		// Determination de la faction gagnante
		winner = (Math.abs(Event.rand.nextGaussian()) < heroWinProba)? AgentType.HERO : AgentType.VILAIN;
		
		return winner;
	}
	
	/**
	 * Bring the entities back to home by creating the path to it
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
	 * Reset the robbery preparation for each vilains
	 * @param vilains the vilain list to reset robbery prep
	 */
	protected void resetPrep(List<Vilain> vilains) {
		for(Vilain v : vilains) {
			v.resetRobberyPrep();
		}
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
