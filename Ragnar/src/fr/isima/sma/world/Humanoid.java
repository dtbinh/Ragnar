/**
 * Humanoid class used to implement citizens and heroes
 * 
 */

package fr.isima.sma.world;

import java.util.Random;

//import fr.isima.sma.world.Humanoid.BadAgeException;
public abstract class Humanoid extends ActiveEntity {
	// Static members
	public static Random rand = new Random();
	public static final int ageMax = 90;
	
	// Class members
	protected String name;
	protected String surname;
	protected int age;

	/**
	 * Exceptions thrown in the class
	 */
	public class BadAgeException extends Exception {
		public BadAgeException() {
			super();
		}
	}
	
	/**
	 * Default constructor
	 */
	public Humanoid() {
		// By default the entities name, surname and age are randomly generated
		
	}
	
	/**
	 * Specific constructor
	 * @param name name of the entity
	 * @param surname surname of the entity
	 * @param age age of the entity
	 * @throws BadAgeException if the age is not in the range ]0 - <code>maxAge</code>[
	 */
	public Humanoid(String name, String surname, int age) throws BadAgeException {
		this.name = name;
		this.surname = surname;
		
		if(age > 0 && age < Humanoid.ageMax) {
			this.age = age;
		} else {
			throw new BadAgeException();
		}
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

}
