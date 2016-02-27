package fr.isima.sma.simulator;

import fr.isima.sma.gui.RagnarView;
import fr.isima.sma.resources.NameLoader;
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
		NameLoader.getInstance();		
		boolean restart = true;
		int iterations = Integer.valueOf(props.getProperty("iterations"));
		int tempssimu = Integer.valueOf(props.getProperty("tempssimu"));
		
		while(restart || iterations!=0) {
			restart = false;
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
	
			while(!c.isStop() && tempssimu >= m.getAnnee()) {
				while(!c.isPlay()) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
					}
				}
				c.simulate();
			}
			if(!restart) {
				c.exportResults("result0"+(iterations<10?0:"")+iterations);
				iterations--;
			}
			while(c.isStop()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
			}
			v.dispose();
		}
	}

}
