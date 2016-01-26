package fr.isima.sma.world;

public abstract class Sector {

	static public enum SectorType {
		Street, Bank, HeadQuarter
	}
	
	protected SectorType 	type;
	protected Location		location;
	
	public Sector() {
		this.type = SectorType.Street;
		location = new Location();
	}
	
	public Sector(SectorType type, Location location) {
		this.type = type;
		this.location = location;
	}
	
	@Override
	public abstract String toString();

	/**
	 * @return the type
	 */
	public SectorType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(SectorType type) {
		this.type = type;
	}

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
	
}
