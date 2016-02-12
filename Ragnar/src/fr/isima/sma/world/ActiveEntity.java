package fr.isima.sma.world;

public abstract class ActiveEntity extends Entity {
	
	protected int speed; // Indicate the entity speed (max blocs per ticks)
	protected Location location;
	
	public static enum ActiveEntityType {
		Citizen, Hero, Vilain, Group
	}
	
	public ActiveEntity() {
		// TODO Auto-generated constructor stub
	}

	public abstract void live();

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	

}
