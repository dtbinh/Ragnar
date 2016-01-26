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
			
			Hero h = new Hero("Super", "quiche", 20, 1, 0, 0);
			
			c.addActiveEntity(h);
			
			while(true) {
				h.moveRandom();
				System.out.println(h.getLocation());
				
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
