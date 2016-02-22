package fr.isima.sma.world;

public class Hero extends Super {

	public Hero()  {
		super();
		city.getSector(this).setNumberHero(city.getSector(this).getNumberHero()+1);
	}

	public Hero(String name, String surname, int age, int speed, int locationX, int locationY) {
		super(name, surname, age, speed, locationX, locationY);
		city.getSector(this).setNumberHero(city.getSector(this).getNumberHero()+1);
	}

	public Hero(String name, String surname, int age) {
		super(name, surname, age);
		city.getSector(this).setNumberHero(city.getSector(this).getNumberHero()+1);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#moveRandom()
	 */
	@Override
	public void moveRandom() {
		// TODO Auto-generated method stub
		super.moveRandom();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#move(int, int)
	 */
	@Override
	public void move(int locationX, int locationY) {
		// TODO Auto-generated method stub
		super.move(locationX, locationY);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		super.setName(name);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#getSurname()
	 */
	@Override
	public String getSurname() {
		// TODO Auto-generated method stub
		return super.getSurname();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#setSurname(java.lang.String)
	 */
	@Override
	public void setSurname(String surname) {
		// TODO Auto-generated method stub
		super.setSurname(surname);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#getAge()
	 */
	@Override
	public int getAge() {
		// TODO Auto-generated method stub
		return super.getAge();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#setAge(int)
	 */
	@Override
	public void setAge(int age) {
		// TODO Auto-generated method stub
		super.setAge(age);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#getSpeed()
	 */
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#setSpeed(int)
	 */
	@Override
	public void setSpeed(int speed) {
		// TODO Auto-generated method stub
		super.setSpeed(speed);
	}

	@Override
	public void live() {
		// TODO Auto-generated method stub

	}

}
