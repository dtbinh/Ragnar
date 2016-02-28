package fr.isima.sma.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import fr.isima.sma.simulator.events.Event;
import fr.isima.sma.simulator.events.EventType;
import fr.isima.sma.world.ActiveEntity.AgentType;
import fr.isima.sma.world.patterns.AbstractModelObject;
import fr.isima.sma.world.patterns.IMyObservable;
import fr.isima.sma.world.patterns.MyObservable;

public abstract class Sector extends AbstractModelObject implements IMyObservable {

	static public enum SectorType {
		Street(0), Bank(1), HeadQuarter(2), HeroHQ(3), VilainHQ(4);
		private final int value;
		private SectorType(int a) {
			value = a;
		}
		public int getValue() {
			return value;
		}
	}

	protected SectorType 	type;
	protected Location		location;
	protected int			numberHumanoid[];
	protected List<List<Humanoid>> agents; 
	protected int 			moneyAvailable;		// somme disponible sur le secteur pour etre recolte par les cytoyens
	protected MyObservable observable;

	public Sector() {
		this(SectorType.Street, new Location());
	}

	public Sector(SectorType type, Location location) {
		super();
		observable = new MyObservable();
		numberHumanoid = new int[4];
		this.type = type;
		this.location = location;
		agents = new ArrayList<List<Humanoid>>(4);
		for(int i = 0 ; i < AgentType.values().length ; i++) {
			agents.add(new ArrayList<Humanoid>(16));
			numberHumanoid[i] = 0;
		}
		this.setMoneyAvailable(500);
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

	public int getNumberHero() {
		return numberHumanoid[AgentType.HERO.getValue()];
	}

	public void setNumberHero(int numberHero) {
		int old = this.numberHumanoid[AgentType.HERO.getValue()];
		this.numberHumanoid[AgentType.HERO.getValue()] = numberHero;
		firePropertyChange("numberHero", old, numberHero);	// BINDING
	}

	public int getNumberVilain() {
		return numberHumanoid[AgentType.VILAIN.getValue()];
	}

	public void setNumberVilain(int numberVilain) {
		int old = this.numberHumanoid[AgentType.VILAIN.getValue()];
		this.numberHumanoid[AgentType.VILAIN.getValue()] = numberVilain;
		firePropertyChange("numberVilain", old, numberVilain);	// BINDING
	}

	public int getNumberCitizen() {
		return numberHumanoid[AgentType.CITIZEN.getValue()];
	}

	public void setNumberCitizen(int numberCitizen) {
		int old = this.numberHumanoid[AgentType.CITIZEN.getValue()];
		this.numberHumanoid[AgentType.CITIZEN.getValue()] = numberCitizen;
		firePropertyChange("numberCitizen", old, numberCitizen);	// BINDING
	}

	public int getNumberGroup() {
		return numberHumanoid[AgentType.GROUP.getValue()];
	}

	public void setNumberGroup(int numberGroup) {
		int old = this.numberHumanoid[AgentType.GROUP.getValue()];
		this.numberHumanoid[AgentType.GROUP.getValue()] = numberGroup;
		firePropertyChange("numberGroup", old, numberGroup);	// BINDING
	}

	public int getNumberHumanoid(AgentType pType) {
		return numberHumanoid[pType.getValue()];
	}

	public void setNumberHumanoid(AgentType pType, int val) {
		int old = this.numberHumanoid[pType.getValue()];
		this.numberHumanoid[pType.getValue()] = val;
		String propertyName = "number"+pType.toString().charAt(0)+pType.toString().toLowerCase().substring(1);
		firePropertyChange(propertyName, old, val);	// BINDING
	}

	public void ruleEconomy() {
		try {
			throw new Exception("Non implemented method ruleEconomy in "+getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMoneyAvailable() {
		return moneyAvailable;
	}

	public void setMoneyAvailable(int moneyAvailable) {
		this.moneyAvailable = moneyAvailable;
	}

	public String toResult() {
		// TODO Auto-generated method stub
		return type+"\t"+this.moneyAvailable;
	}

	public void setArriveHumanoid(AgentType ptype, Humanoid humanoid) {
		agents.get(ptype.getValue()).add(humanoid);
	}

	public void setLeaveHumanoid(AgentType ptype, Humanoid humanoid) {
		agents.get(ptype.getValue()).remove(humanoid);
	}

	@Override
	public void notifyObservers() {
		observable.notifyObservers();
	}

	@Override
	public void notifyObservers(Object o) {
		observable.notifyObservers(o);
	}

	@Override
	public void addObserver(Observer o) {
		observable.addObserver(o);
	}

	@Override
	public int countObservers() {
		return observable.countObservers();
	}
	
	public void newFight() {
		List<Humanoid> liste = new ArrayList<>();
		for(List<Humanoid> l : this.agents)
			liste.addAll(l);
		
		observable.setChanged();
		notifyObservers(new Event(this, liste, EventType.Fight, 1));
	}

}
