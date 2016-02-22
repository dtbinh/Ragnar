package fr.isima.sma.world;

public class Group extends Humanoid {

	public Group() {
		super();
		city.getSector(this).setNumberGroup(city.getSector(this).getNumberGroup()+1);
	}

	@Override
	public void live() {
		// TODO Auto-generated method stub

	}

}
