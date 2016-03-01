package fr.isima.sma.world;

public class Street extends Sector {
	
	protected int			dailyMoney;			// gain quotidien de la somme a recolter

	public Street() {
		setType(SectorType.Street);
		dailyMoney = 50 + City.rand.nextInt(100);
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
		if(moneyAvailable < 1)
			moneyAvailable *= (1+City.rand.nextDouble()/10);
		moneyAvailable += (int)((0.75+City.rand.nextDouble()/2)*dailyMoney);
	}
}
