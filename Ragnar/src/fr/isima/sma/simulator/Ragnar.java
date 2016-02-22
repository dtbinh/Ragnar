package fr.isima.sma.simulator;

import fr.isima.sma.gui.RagnarView;
import fr.isima.sma.world.ActiveEntity;
import fr.isima.sma.world.City;
import fr.isima.sma.world.Hero;

public class Ragnar {

	public Ragnar() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		City m = new City();
		m.loadCityFromFile("ragnar.txt");
		ActiveEntity.setCity(m);
		m.loadAgentsFromFile(null);
		RagnarView v = new RagnarView(m);
		m.addObserver(v);
		SimulationKernel c = new SimulationKernel(m, v);
		m.addActiveEntity(new Hero("Benoît", "", 22));

		while(true) {
			c.simulate();
		}
	}

}
