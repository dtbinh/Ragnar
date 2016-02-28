package fr.isima.sma.world;

import java.util.ArrayList;
import java.util.List;

import fr.isima.sma.simulator.events.Event;

public abstract class Super extends Humanoid {
	/*
	 * evenements connus de tous
	 */
	protected static List<Event> events = new ArrayList<>();

	public Super() {
		// TODO Auto-generated constructor stub
	}

	public Super(AgentType type, String name, String surname, int age, int speed, int locationX, int locationY) {
		super(type, name, surname, age, speed, locationX, locationY);
		// TODO Auto-generated constructor stub
	}

	public Super(AgentType type, String name, String surname, int age) {
		super(type, name, surname, age);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Humanoid#moveRandom()
	 */
	@Override
	public void moveRandom() {
		// TODO Auto-generated method stub
		super.moveRandom();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Humanoid#move(int, int)
	 */
	@Override
	public void move(int locationX, int locationY) {
		// TODO Auto-generated method stub
		super.move(locationX, locationY);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Humanoid#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Humanoid#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		super.setName(name);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Humanoid#getSurname()
	 */
	@Override
	public String getSurname() {
		// TODO Auto-generated method stub
		return super.getSurname();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Humanoid#setSurname(java.lang.String)
	 */
	@Override
	public void setSurname(String surname) {
		// TODO Auto-generated method stub
		super.setSurname(surname);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Humanoid#getAge()
	 */
	@Override
	public int getAge() {
		// TODO Auto-generated method stub
		return super.getAge();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Humanoid#setAge(int)
	 */
	@Override
	public void setAge(int age) {
		// TODO Auto-generated method stub
		super.setAge(age);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Humanoid#getSpeed()
	 */
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Humanoid#setSpeed(int)
	 */
	@Override
	public void setSpeed(int speed) {
		// TODO Auto-generated method stub
		super.setSpeed(speed);
	}
	
	static public void addEvent(Event e) {
		events.add(e);
	}
	
	static public void removeEvent(Event e) {
		events.remove(e);
	}
	
	static public Event getEvent(int i) {
		return events.get(i);
	}
	
	static public List<Event> getEvents() {
		return events;
	}
	
	static public void setEvents(List<Event> liste) {
		events = liste;
	}

}
