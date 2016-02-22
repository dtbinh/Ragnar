package fr.isima.sma.world;

public class Citizen extends Humanoid {
	static private ICityCitizen cityInterface;

	public Citizen(ICityCitizen city) {
		super();
		city.getSector(this).setNumberHero(city.getSector(this).getNumberHero()+1);
	}

	public Citizen(String name, String surname, int age, int speed, int locationX, int locationY) {
		super(name, surname, age, speed, locationX, locationY);
		city.getSector(this).setNumberCitizen(city.getSector(this).getNumberCitizen()+1);
	}

	/**
	 * Defines the behaviour of the entity
	 */
	@Override
	public void live() {
		if(cityInterface.getHeure() > 20 && cityInterface.getHeure() < 8) {
			// Entre 20h et 8h, il faut etre a la maison

		} else if (true) {
			// TODO terminer
		} else {
			cityInterface.getSector(this);
		}

	}

	public static void setCityInterface(ICityCitizen i) {
		cityInterface = i;
	}

}
