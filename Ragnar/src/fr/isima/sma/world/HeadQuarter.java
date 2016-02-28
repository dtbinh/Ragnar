package fr.isima.sma.world;

import java.util.ArrayList;
import java.util.List;

import fr.isima.sma.simulator.events.Event;
import fr.isima.sma.simulator.events.EventType;
import fr.isima.sma.world.ActiveEntity.AgentType;
import fr.isima.sma.world.patterns.MyObservable;

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
		
		observable = new MyObservable();
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
		
		out += " X="+location.getLocationX() + ";Y=" + location.getLocationY();
		
		return out;
	}
	
	@Override
	public void setNumberHumanoid(AgentType pType, int val) {
		super.setNumberHumanoid(pType, val);
		if(this.type == SectorType.HeroHQ && pType==AgentType.VILAIN && val!=0) {
			boolean wantRelease = false;
			List<Humanoid> liste = new ArrayList<>();
			for(List<Humanoid> l : this.agents)
				liste.addAll(l);
			for(Humanoid h : liste)
				wantRelease |= h.getWantRelease();
			if(wantRelease) {
				observable.setChanged();
				notifyObservers(new Event(this, liste, EventType.Release, 3));
			}
		}
	}
	
	@Override
	public void ruleEconomy() {
		if(owner == OwnerType.Citizen) {
			this.setMoneyAvailable(this.getMoneyAvailable() - this.getNumberCitizen()*80);
		}
	}

}
