package fr.isima.sma.simulator;

import fr.isima.sma.gui.RagnarView;
import fr.isima.sma.resources.Properties;
import fr.isima.sma.world.City;
import fr.isima.sma.world.Humanoid;

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

		while(true) {
			c.simulate();
		}
		
	}

}
