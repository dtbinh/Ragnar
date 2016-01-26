package fr.isima.sma.simulator;

import java.util.ArrayList;

import fr.isima.sma.world.*;

public class Ragnar {
	
	public Ragnar() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		City c = new City();
		
		if(args.length == 1) {
			System.out.println("Chargement de la city par fichier");
			c.loadFromFile(args[0]);
			Location.setMaxLocation(c.getSizeX(), c.getSizeY());
			
			Hero h = new Hero("Super", "quiche", 20, 2, 5, 5);
			c.addActiveEntity(h);
			
			System.out.println(Location.getMaxLocationX() + " " + Location.getMaxLocationY());
			System.out.println(Location.getMinLocationX() + " " + Location.getMinLocationY());
			System.out.println(h.getLocation());
			h.move(2, 2);
			System.out.println(h.getLocation());
			
			while(true) {
				h.moveRandom();
				
				System.out.println(c);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
			}
		} else {
			System.err.println("Pas de fichier de configuration de la city");
		}
	}

}
