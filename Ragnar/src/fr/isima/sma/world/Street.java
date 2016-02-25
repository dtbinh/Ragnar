package fr.isima.sma.world;

public class Street extends Sector {
	
	protected int			dailyMoney;			// gain quotidien de la somme a recolter

	public Street() {
		setType(SectorType.Street);
	}
	
	public SectorType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "S";
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
	
	@Override
	public void ruleEconomy() {
		this.moneyAvailable += ((0.75+City.rand.nextDouble()/2)*dailyMoney);
	}
}
