package fr.isima.sma.world;

public class Vilain extends Super {

	public Vilain() {
		city.getSector(this).setNumberHero(city.getSector(this).getNumberHero()+1);
	}

	public Vilain(String name, String surname, int age, int speed, int locationX, int locationY) {
		super(name, surname, age, speed, locationX, locationY);
		city.getSector(this).setNumberVilain(city.getSector(this).getNumberVilain()+1);
	}

	@Override
	public void live() {
		// TODO Auto-generated method stub

	}

}
