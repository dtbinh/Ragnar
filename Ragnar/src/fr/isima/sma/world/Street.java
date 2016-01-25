package fr.isima.sma.world;

public class Street extends Sector {

	static public enum SectorType {
		Street, Bank, HeadQuarter
	}
	
	protected SectorType 	type;
	protected Location		location;
	
	public Street(SectorType t, Location l) {
		super();
		type = t;
		location = l;
	}
	
	public SectorType getType() {
		return type;
	}
	
	public void setType(SectorType t) {
		type = t;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location l) {
		location = l;
	}

}
