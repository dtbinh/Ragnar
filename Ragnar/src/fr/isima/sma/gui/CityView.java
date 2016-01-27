package fr.isima.sma.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import fr.isima.sma.world.*;

/*
 *	Vue du programme
 */
public class CityView implements Observer {
	
	protected City 			modele;	/// modele
	protected RagnarWindow	window;	/// fenetre
	protected Map			map;	/// zone d'affichage de la carte
	
	public CityView(City pModele) {
		window = new RagnarWindow();
		modele = pModele;
		//modele.addObserver(this);
		map = new Map(pModele.getSizeY(), pModele.getSizeX(), 50);
		window.getContentPane().add(map, BorderLayout.CENTER);
		window.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		map.repaint();
	}
	
	public static void main(String[] args) {
		City ragnar = new City(2000);
		ragnar.loadFromFile("ragnar.txt");
		CityView cv = new CityView(ragnar);
		
		cv.map.getComponent(3).setBackground(new Color(255,255,255));
	}
	
}
