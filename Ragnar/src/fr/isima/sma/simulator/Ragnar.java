package fr.isima.sma.simulator;

import fr.isima.sma.world.City;

public class Ragnar {
	
	public Ragnar() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		VirtualClock c = new VirtualClock(2000);
		
		while(true) {
			if(c.ticTac()) {
				System.out.println("Bonjour ! "+System.currentTimeMillis());
			}
		}
	}

}
