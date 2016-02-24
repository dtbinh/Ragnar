package fr.isima.sma.world;

public class Citizen extends Humanoid {

	public Citizen(String name, String surname, int age, int speed, int locationX, int locationY) {
		super(AgentType.CITIZEN, name, surname, age, speed, locationX, locationY);
	}

	/**
	 * Defines the behaviour of the entity
	 */
	@Override
	public void live() {
		if(city.getHeure() > 20 && city.getHeure() < 8) {
			// Entre 20h et 8h, il faut etre a la maison

		} else if (true) {
			// TODO terminer
		} else {
			city.getSector(this);
		}

	}

}
