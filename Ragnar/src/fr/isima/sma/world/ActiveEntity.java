package fr.isima.sma.world;

public abstract class ActiveEntity extends Entity {
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

}
