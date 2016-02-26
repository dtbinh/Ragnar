package fr.isima.sma.world;

import java.util.ArrayList;
import java.util.List;

import fr.isima.sma.world.Sector.SectorType;

public class Citizen extends Humanoid {

	protected int dailySalary;
	protected int level;

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
		if(city.getHeure() > 20 || city.getHeure() < 8) {
			// Entre 20h et 8h, il faut etre a la maison
			if( !this.location.equals(this.home.location) ) {
				
				this.move(this.home.location.getLocationX(), this.home.location.getLocationY());
				
//				if(path.size() == 0) {
//					findHome(); // Create the path
//				}
//				
//				Location tmp = path.get(0); // Get the location
//				
//				this.move(tmp.getLocationX(), tmp.getLocationY()); // Move to location
//				if(this.location == this.home.location) { // If we're at home, go inside
//					this.location.setInside();
//				}
//				
//				path.remove(0); // Remove in the path
			}
			
		} else {
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
			} else if(cible.getType()==SectorType.Bank || cible.equals(home)) { // depot d'argent banque ou maison
				city.getSector(this).setMoneyAvailable(this.money+cible.getMoneyAvailable());
				this.setMoney(0);
			}
		}
		
	} // live
	
	/**
	 * Create the path to home
	 */
	public void findHome() {
	    Location tmpLoc = new Location(this.location.getLocationX(), this.location.getLocationY()); // Recuperation de la position actuelle comme copie

	    if(true) { // Si on peut traverser les batiments
	        // Tant qu'on est pas arrivé
	        while( !tmpLoc.equals(this.home.location) ) {
	            // Traitement axe X
	            int xOff = this.home.location.getLocationX() - tmpLoc.getLocationX(); // Difference avec ou je suis
	            int xDep = 0;

	            if(xOff > 0) {
	                xDep = Integer.min(xOff, speed); // Je me deplace soit de ce qu'il manque soit j'avance a fond
	            } else if (xOff < 0){
	                xDep = Integer.max(xOff, -speed);
	            }
	            tmpLoc.setLocationX(tmpLoc.getLocationX() + xDep);

	            // Traitement axe y
	            int yOff = this.home.location.getLocationY() - tmpLoc.getLocationY(); // Difference avec ou je suis
	            int yDep = 0;

	            if(yOff > 0) {
	                yDep = Integer.min(yOff, speed); // Je me deplace soit de ce qu'il manque soit j'avance a fond
	            } else if (yOff < 0){
	                yDep = Integer.max(yOff, -speed);
	            }
	            tmpLoc.setLocationX(tmpLoc.getLocationY() + yDep);

	            // Ajout dans la liste
	            Location toAdd = new Location(tmpLoc.getLocationX(), tmpLoc.getLocationY());
	            path.add(toAdd);
	            
	            System.out.println("path to " + this.home.location + " tmpLoc : " + tmpLoc + " xOff :" + xOff + " xDep : " + xDep + " yOff : " + yOff + " yDep : " + yDep + " toadd : " + toAdd);
	        }
	    } else { // Sinon path finding ouf
	    	System.out.println("Not implemented");
	    }
	} // findHome

}
