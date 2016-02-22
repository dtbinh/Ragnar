package fr.isima.sma.world;

public abstract class ActiveEntity extends Entity {
	protected static City city;
	protected int speed; // Indicate the entity speed (max blocs per ticks)
	protected Location location;

	public static enum ActiveEntityType {
		Citizen, Hero, Vilain, Group
	}

	protected static int cpt = 0; // Count for the id
	protected int id;

	public ActiveEntity() {
		ActiveEntity.cpt +=1;
		this.id = cpt;
	}

	/**
	 * Check if the id of entities are the same
	 * @return true if the id are the same, false if they're not
	 */
	@Override
	public boolean equals(Object obj) {
		boolean eq = false;

		if(obj instanceof ActiveEntity) {
			if(((ActiveEntity) obj).id == this.id) {
				eq = true;
			}
		}

		return eq;
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
		Location old = this.location;
		this.location = location;
		firePropertyChange("location", old, this.location);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public static void setCity(City m) {
		city = m;
	}


}
