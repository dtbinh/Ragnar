package fr.isima.sma.world;

public abstract class ActiveEntity extends Entity {
	protected int 		speed; 		// Vitesse de l'entite, en blocs par ticks de simulation
	protected Location 	location;	// Emplacement de l'entite

	public static enum AgentType {
		CITIZEN(0), HERO(1), VILAIN(2), GROUP(3);
		
		final private int value;
		private AgentType(int value) {
	        this.value = value;
	    }
		
		public int getValue() {
			return value;
		}
	}
	
	public static enum LifeState {
		DEAD(0), ALIVE(1), CHILD(2);
		
		final private int value;
		private LifeState(int value) {
	        this.value = value;
	    }
		
		public int getValue() {
			return value;
		}
	}

	protected static int cpt = 0; // Count for the id
	protected int id;

	/**
	 * Default constructor
	 */
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

	/**
	 * Reimplement this methode to make the entity act as it has to
	 */
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
		
	}

	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * Set the entity location, use instead Humanoid.setLocation(int x, int y)
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	@Deprecated 
	public void setLocation(int x, int y) {

	}

}
