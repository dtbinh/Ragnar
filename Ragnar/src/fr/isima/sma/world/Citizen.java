package fr.isima.sma.world;

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
		
		
		
	}

}
