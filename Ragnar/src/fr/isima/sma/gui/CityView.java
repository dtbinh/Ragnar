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
		modele.addObserver(this);
		map = new Map(modele.getMap(), 50);
		window.getContentPane().add(map, BorderLayout.CENTER);
		window.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		int n = 0;
		for (Sector[] ss : modele.getMap()) {
			for (Sector s : ss) {
				((Case) map.getComponent(n)).setCaseType(s.getType());
			}
		}
		map.repaint();
	}
	
}
