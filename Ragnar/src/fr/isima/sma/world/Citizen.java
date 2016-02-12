package fr.isima.sma.world;

public class Citizen extends Humanoid {
	static private ICityCitizen cityInterface;

	public Citizen(ICityCitizen city) {
		// TODO Auto-generated constructor stub
	}
	
	public Citizen(String name, String surname, int age, int speed, int locationX, int locationY) {
		super(name, surname, age, speed, locationX, locationY);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Defines the behaviour of the entity
	 */
	@Override
	public void live() {
		if(cityInterface.getHeure() > 20 && cityInterface.getHeure() < 8) {
			// Entre 20h et 8h, il faut etre a la maison
			
			
		} else {
			cityInterface.getSector(this);
		}
		
	}
	
	public static void setCityInterface(ICityCitizen i) {
		cityInterface = i;
	}

}
