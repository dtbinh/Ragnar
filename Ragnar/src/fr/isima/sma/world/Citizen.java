package fr.isima.sma.world;

public class Citizen extends Humanoid {
	static private ICityCitizen cityInterface;

	public Citizen(ICityCitizen city) {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Defines the behaviour of the entity
	 */
	@Override
	public void live() {
		if(cityInterface.getHeure() > 22) {
			
		} else {
			cityInterface.getSector(this);
		}
		
	}
	
	public static void setCityInterface(ICityCitizen i) {
		cityInterface = i;
	}

}
