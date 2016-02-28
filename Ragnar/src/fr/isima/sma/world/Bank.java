package fr.isima.sma.world;

import java.util.ArrayList;
import java.util.List;

import fr.isima.sma.simulator.events.Event;
import fr.isima.sma.simulator.events.EventType;
import fr.isima.sma.world.ActiveEntity.AgentType;
import fr.isima.sma.world.patterns.IMyObservable;

public class Bank extends Sector implements IMyObservable {
	
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
	
	@Override
	public void setNumberHumanoid(AgentType pType, int val) {
		super.setNumberHumanoid(pType, val);
		if(pType==AgentType.VILAIN && val!=0) {
			boolean wantRobery = false;
			List<Humanoid> liste = new ArrayList<>();
			for(List<Humanoid> l : this.agents)
				liste.addAll(l);
			for(Humanoid h : liste)
				wantRobery |= h.getWantRobery();
			if(wantRobery) {
				observable.setChanged();
				notifyObservers(new Event(this, liste, EventType.Robery, 3));
			}
		}
	}
}
