package fr.isima.sma.simulator;

import fr.isima.sma.gui.RagnarView;
import fr.isima.sma.resources.Properties;
import fr.isima.sma.world.City;
import fr.isima.sma.world.Humanoid;
import fr.isima.sma.world.Location;

public class Ragnar {

	public Ragnar() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		Properties props = Properties.getInstance();
		City m = new City();
		m.loadCityFromFile(props.getProperty("cityFile"));
		Humanoid.setCity(m);
		m.loadAgentsFromFile(props.getProperty("agentsFile"));
		RagnarView v = new RagnarView(m);
		m.addObserver(v);
		SimulationKernel c = new SimulationKernel(m, v);

		Location.setMinLocationX(0);
		Location.setMaxLocationX(m.getSizeX());
		Location.setMinLocationY(0);
		Location.setMaxLocationY(m.getSizeY());

		while(true) {
			c.simulate();
		}
		
	}

}
