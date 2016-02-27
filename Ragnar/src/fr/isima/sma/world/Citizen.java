package fr.isima.sma.world;

import java.util.List;

import fr.isima.sma.world.Sector.SectorType;

public class Citizen extends Humanoid {

	protected int dailySalary;

	public Citizen(String name, String surname, int age, int speed, int ligne, int colonne) {
		super(AgentType.CITIZEN, name, surname, age, speed, ligne, colonne);
		dailySalary = 10;
		level = 1;
	}

	/**
	 * Defines the behaviour of the entity
	 */
	@Override
	public void live() {
		
		
		if(city.getHeure() >= 18 || city.getHeure() < 8) { // On prend large pour avoir le temps de rentrer
			// Entre 20h et 8h, il faut etre a la maison (a peu pres)
			if(this.location.equals(this.getHome().location) == false) { // Pas a la maison
				if(this.path == null) { // Not created so we create it
					// This array indicates where i can walk
					boolean[][] walkable = new boolean[this.city.getSizeX()][this.city.getSizeY()];
					
					// For each sector in the city, initialize the walkable
					for(Sector[] tab : this.city.map) {
						for(Sector s : tab) {
							int x = s.location.getLocationX();
							int y = s.location.getLocationY();
							
							if(s.type == SectorType.Street) {
								walkable[x][y] = true;
							} else {
								walkable[x][y] = false;
							}
						}
					}
					
					this.path = super.findPath(
							this.location.getLocationX(),
							this.location.getLocationY(),
							this.getHome().getLocation().getLocationX(), 
							this.getHome().getLocation().getLocationY(),
							walkable);
					this.pathStep = 0; // The 0 is the current location, so go for 1
					
				} // path initialized
				
				if(pathStep >= 0 && pathStep < path.length) {
					this.setLocation(this.path[pathStep][0], this.path[pathStep][1]); // Move
					pathStep += 1; // Next step
				}
				
			} else { // At home
				if(this.path != null) { // At home and path just ended
					this.path = null;
					this.pathStep = -1;
					
					// depot de son argent a la maison
					city.getSector(this).setMoneyAvailable(this.money+home.getMoneyAvailable());
					this.setMoney(0);
				}
			}
		} else { // The rest of the day
			List<Sector> voisinnage = city.getNeighborhood(this);
			Sector cible = null;
			int valeurCible = 0;
			for(Sector s : voisinnage) {
				if(s.getType()==SectorType.Street) {
					if(valeurCible == s.getMoneyAvailable()) {
						if(Humanoid.rand.nextDouble() > 0.5) {
							cible = s;
							valeurCible = s.getMoneyAvailable();							
						}
					} else if(valeurCible < s.getMoneyAvailable()) {
						cible = s;
						valeurCible = s.getMoneyAvailable();
					}
				}
			}

			// si on a pas trouver de sector avec de l'argent on va dans une rue aléatoire
			if(cible==null) {
				cible = voisinnage.get(Humanoid.rand.nextInt(voisinnage.size()));
			}
			
			// deplacement
			if(!cible.equals(city.getSector(this))) {
				this.setLocation(cible.location.getLocationX(), cible.getLocation().getLocationY());
			} else {
				cible = city.getSector(this);
			}
			
			if(cible.getType()==SectorType.Street) {// travail
				if(dailySalary <= cible.getMoneyAvailable()) {
					double efficiency = Humanoid.rand.nextDouble()+0.4;
					this.setMoney(this.money+Integer.min((int)(efficiency*dailySalary),cible.getMoneyAvailable()));
					city.getSector(this).setMoneyAvailable(cible.getMoneyAvailable()-Integer.min((int)(efficiency*dailySalary),cible.getMoneyAvailable()));
				} else {
					this.setMoney(this.money+cible.getMoneyAvailable());
					city.getSector(this).setMoneyAvailable(0);
				}
			} else if(cible.getType()==SectorType.Bank || cible.equals(getHome())) { // depot d'argent banque ou maison
				city.getSector(this).setMoneyAvailable(this.money+cible.getMoneyAvailable());
				this.setMoney(0);
			}
		}
		
	} // live
	
}
