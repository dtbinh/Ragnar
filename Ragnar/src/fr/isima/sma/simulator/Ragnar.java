package fr.isima.sma.simulator;

import java.util.ArrayList;

import fr.isima.sma.gui.CityView;
import fr.isima.sma.world.*;

public class Ragnar {
	
	public Ragnar() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		City m = new City();
		m.loadCityFromFile("ragnar.txt");
		CityView v = new CityView(m);
		m.loadAgentsFromFile(null);
		m.addObserver(v);
		SimulationKernel c = new SimulationKernel(m, v);
		
		while(true) {
			c.simulate();			
		}
	}

}
