package fr.isima.sma.world;

public class HeadQuarter extends Sector {
	
	public static enum OwnerType {
		Citizen, Heroe, Vilain
	}

	protected OwnerType owner;
	
	public HeadQuarter() {
		this(OwnerType.Citizen);
	}
	
	public HeadQuarter(OwnerType owner) {
		super();
		this.owner = owner;
		setType(SectorType.HeadQuarter);
		if(owner == OwnerType.Heroe)
			setType(SectorType.HeroHQ);
		else if(owner == OwnerType.Vilain)
			setType(SectorType.VilainHQ);
	}
	
	@Override
	public String toString() {
		String out = "";
		
		switch (owner) {
		case Citizen:
			out += "I";
			break;
		case Heroe:
			out += "H";
			break;
		case Vilain:
			out += "V";
			break;
		default:
			out += "?";
			break;
		}
		
		return out;
	}

}
