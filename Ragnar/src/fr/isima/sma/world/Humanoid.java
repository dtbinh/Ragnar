/**
 * Humanoid class used to implement citizens and heroes/vilains
 * 
 */

package fr.isima.sma.world;

import java.util.Random;

//import fr.isima.sma.world.Humanoid.BadAgeException;
public abstract class Humanoid extends ActiveEntity {
	// Static members
	public static Random rand = new Random(123); // TODO remove the seed at the end
	public static final int ageMax = 90;
	
	// Class members
	protected String name;
	protected String surname;
	protected int age;
	
	protected int speed; // Indicate the entity speed (max blocs per ticks)
	protected Location location;
	
	/**
	 * Default constructor
	 */
	public Humanoid() {
		super();
		// By default the entities name, surname and age are randomly generated
		
	}
	
	/**
	 * Normal constructor with fewer arguments
	 * @param name
	 * @param surname
	 * @param age
	 * @throws BadAgeException
	 */
	public Humanoid(String name, String surname, int age) {
		// Default can be placed in the HQ
		this(name, surname, age, 1, 0, 0);
	}
	
	/**
	 * Specific constructor
	 * @param name name of the entity
	 * @param surname surname of the entity
	 * @param age age of the entity
	 * @throws BadAgeException if the age is not in the range ]0 - <code>maxAge</code>[
	 */
	public Humanoid(String name, String surname, int age, int speed, int locationX, int locationY) {
		this.name = name;
		this.surname = surname;
		this.speed = speed;
		
		if(age > 0 && age < Humanoid.ageMax) {
			this.age = age;
		} else {
			System.err.println("Erreur sur l'age de : " + name);
		}
		
		this.location = new Location(locationX, locationY);
	}

	/**
	 * Randomly choose where to go. It can move full speed or not
	 */
	public void moveRandom() {
		int bound = 2 * this.speed + 1; // The max of the random, +1 because it is exclusive value
		this.location.shiftLocation(Humanoid.rand.nextInt(bound) - speed, Humanoid.rand.nextInt(bound) - speed);
	}
	
	public void move(int locationX, int locationY) {
		// TODO je peux pas aller plus vite que la speed
		this.location.setLocation(locationX, locationY);
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

	
}
