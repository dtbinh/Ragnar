package fr.isima.sma.world;

public class Bank extends Sector {

	public Bank() {
		setType(SectorType.Bank);
	}
	
	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public void ruleEconomy() {
		this.setMoneyAvailable((int)(this.getMoneyAvailable() * 1.02));
	}

}
