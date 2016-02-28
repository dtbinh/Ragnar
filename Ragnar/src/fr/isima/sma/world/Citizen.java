package fr.isima.sma.world;

import java.util.List;

import fr.isima.sma.world.Sector.SectorType;
import fr.isima.sma.world.patterns.Console;

public class Citizen extends Humanoid {

	protected int dailySalary;
	private boolean goToBank;
	private int[][] pathBank;
	private int pathBankStep;

	public Citizen(String name, String surname, int age, int speed, int ligne, int colonne) {
		super(AgentType.CITIZEN, name, surname, age, speed, ligne, colonne);
		dailySalary = 10;
		level = 1;
		superPotential = (age > 15 && age < 30);
		superChance = (Humanoid.rand.nextDouble()/10);
		goToBank = false;
		this.pathBank = null;
		this.pathBankStep = -1;
	}

	/**
	 * Defines the behaviour of the entity
	 */
	@Override
	public void live() {
		
		if(age > 15) {	// si adulte
			if(city.getHeure() >= 18 || city.getHeure() < 8) { // On prend large pour avoir le temps de rentrer
				// Entre 20h et 8h, il faut etre a la maison (a peu pres)
				if(city.getSector(this).getType().equals(SectorType.HeadQuarter) == false) { // Pas a la maison
					if(this.path == null) { // Not created so we create it
						this.pathBank = null;
						this.pathBankStep = -1;
						this.goToBank = false;
						this.path = super.findPath(
								this.location.getLocationX(),
								this.location.getLocationY(),
								this.getHome().getLocation().getLocationX(), 
								this.getHome().getLocation().getLocationY(),
								Humanoid.city.getWalkableLegit());
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
					if(age < 60 && city.getHeure() == 4 && home.getNumberCitizen() >= 2) {	// procreation
						//System.out.println("Tentative d'accouplement de " + this + " avec fertilité " + fertilite);
						if(Humanoid.rand.nextDouble() < this.fertilite) {
							city.createNewBabyCitizen(this);
							fertilite /= 2;
						}
					}
					if(city.getHeure() == 6 && city.getSector(this).getMoneyAvailable() >= 100*level*level) {
						city.getSector(this).setMoneyAvailable(home.getMoneyAvailable()-(100*level*level));
						this.setMoney(money+100*level*level);
						goToBank = true;
					}
				}
			} else { // The rest of the day
				if (goToBank) {	// si depot a faire
					
					/////////////////////////////////////////////////////////////////////////////////////
					
					if(city.getSector(this).getType().equals(SectorType.Bank) == false) { // Pas a la bank
						if(this.pathBank == null) { // Not created so we create it
							this.path = null;
							this.pathStep = -1;
							
							// choix de la banque
							Sector bank = city.getSectorByType().get(SectorType.Bank.getValue()).get(Humanoid.rand.nextInt(city.getSectorByType().get(SectorType.Bank.getValue()).size()));

							this.pathBank = super.findPath(
									this.location.getLocationX(),
									this.location.getLocationY(),
									bank.getLocation().getLocationX(), 
									bank.getLocation().getLocationY(),
									Humanoid.city.getWalkableLegit());
							this.pathBankStep = 0; // The 0 is the current location, so go for 1
							
						} // path initialized
						
						if(pathBankStep >= 0 && pathBankStep < pathBank.length) {
							this.setLocation(this.pathBank[pathBankStep][0], this.pathBank[pathBankStep][1]); // Move
							pathBankStep += 1; // Next step
						}
						
					} else { // At bank
						if(this.pathBank != null) { // At bank and path just ended
							this.pathBank = null;
							this.pathBankStep = -1;
							this.goToBank = false;
							
							// depot de son argent a la banque
							city.getSector(this).setMoneyAvailable(this.money+city.getSector(this).getMoneyAvailable());
							this.setMoney(0);
							// gain de niveau
							this.setLevel(getLevel()+1);
							this.dailySalary *= (1.5+Humanoid.rand.nextDouble());
							Console.println(city.getDate()+" "+name+" "+surname+" gagne un niveau, nouveau salaire : "+dailySalary+".");
						}
					}
					
					/////////////////////////////////////////////////////////////////////////////////////
					
				} else {	// travail
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
			}
		}
	} // live
	
}
