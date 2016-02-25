/**
 * 
 * Humanoid class used to implement citizens and heroes/vilains
 * It corresponds to the "agent" class
 * 
 */

package fr.isima.sma.world;

import java.util.Random;

//import fr.isima.sma.world.Humanoid.BadAgeException;
public abstract class Humanoid extends ActiveEntity {
	// Static members
	protected static City city;
	protected static Random rand = new Random(123); // TODO remove the seed at the end
	protected static final int ageMax = 90;
	protected HeadQuarter home;
	
	// Class members
	protected String name;
	protected String surname;
	protected int age;
	protected String url;
	protected ActiveEntity.AgentType type;
	
	// syteme monetaire
	protected int money;
	
	
	/**
	 * Default constructor
	 */
	public Humanoid() {
		this(AgentType.CITIZEN,"","",0);
		// By default the entities name, surname and age are randomly generated
		
	}
	
	/**
	 * Normal constructor with fewer arguments
	 * @param name
	 * @param surname
	 * @param age
	 * @throws BadAgeException
	 */
	public Humanoid(AgentType type, String name, String surname, int age) {
		// Default can be placed in the HQ
		this(type, name, surname, age, 1, 0, 0);
	}
	
	/**
	 * Specific constructor
	 * @param name name of the entity
	 * @param surname surname of the entity
	 * @param age age of the entity
	 * @throws BadAgeException if the age is not in the range ]0 - <code>maxAge</code>[
	 */
	public Humanoid(AgentType type, String name, String surname, int age, int speed, int ligne, int colonne) {
		this.name = name;
		this.surname = surname;
		this.speed = speed;
		this.type = type;
		
		if(age > 0 && age < Humanoid.ageMax) {
			this.age = age;
		} else {
			System.err.println("Erreur sur l'age de : " + name);
		}
		
		this.location = new Location(ligne, colonne);
		city.getSector(this).setNumberHumanoid(type, city.getSector(this).getNumberHumanoid(type)+1);
		firePropertyChange("location", new Location(), this.location);
		home = (HeadQuarter)city.getSector(this);
		setMoney(0);
	}

	/**
	 * Randomly choose where to go. It can move full speed or not
	 */
	public void moveRandom() {
		int bound = 2 * this.speed + 1; // The max of the random, +1 because it is exclusive value
		this.location.shiftLocation(Humanoid.rand.nextInt(bound) - speed, Humanoid.rand.nextInt(bound) - speed);
	}

	@Override
	public void setLocation(Location location) {
		Location old = this.location;
		city.getSector(this).setNumberHumanoid(type, city.getSector(this).getNumberHumanoid(type)-1);
		this.location.setLocation(location.getLocationX(), location.getLocationY());
		city.getSector(this).setNumberHumanoid(type, city.getSector(this).getNumberHumanoid(type)+1);
		firePropertyChange("location", old, this.location);
	}
	
	@Override
	public void setLocation(int x, int y) {
		Location old = this.location;
		city.getSector(this).setNumberHumanoid(type, city.getSector(this).getNumberHumanoid(type)-1);
		this.location.setLocation(x, y);
		city.getSector(this).setNumberHumanoid(type, city.getSector(this).getNumberHumanoid(type)+1);
		firePropertyChange("location", old, this.location);
	}
	
	public void move(int locationX, int locationY) {
		// TODO je peux pas aller plus vite que la speed
		this.setLocation(new Location(locationX, locationY));
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder st = new StringBuilder(name).append(name!=""?" ":"").append(surname).append("  -  ").append(this.getClass().getSimpleName());
		return st.toString();
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}


	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static void setCity(City m) {
		city = m;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
}
