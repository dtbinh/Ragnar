package fr.isima.sma.simulator;

import fr.isima.sma.world.*;
import fr.isima.sma.world.Humanoid.BadAgeException;

public class Ragnar {
	
	public Ragnar() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		VirtualClock c = new VirtualClock(2000);
		
		Hero benji = null;
		try {
			benji = new Hero("Super", "Man", 21, 4, 0, 0);
		} catch (BadAgeException e) {
			System.err.println("N'a pas pu etre cree");
		}
		
		if(benji != null) {
			benji.location.setMaxLocation(10, 10);
		
			while(true) {
				if(c.ticTac()) {
					benji.moveRandom();
					System.out.println(benji.location);
				}
			}
		}
	}

}
