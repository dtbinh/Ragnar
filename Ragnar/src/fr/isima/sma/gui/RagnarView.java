package fr.isima.sma.gui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import fr.isima.sma.world.*;

/*
 *	Vue du programme
 */
public class RagnarView implements Observer {

	protected City 			modele;	/// modele
	protected RagnarWindow	window;	/// fenetre de la map
	protected AgentsView	entities;	/// fenetre des entites
	protected Map			map;	/// zone d'affichage de la carte

	public RagnarView(City pModele) {
		window = new RagnarWindow();
		modele = pModele;
		modele.addObserver(this);
		map = new Map(modele, 64);
		entities = new AgentsView(modele);
		window.getContentPane().add(map, BorderLayout.CENTER);
		window.setSize(map.getNbBlocsL()*map.getBlocsSize(), map.getNbBlocsH()*map.getBlocsSize());
		entities.setLocation(window.getWidth()+window.getX(), window.getY());

		window.setVisible(true);
		entities.setVisible(true);
	}


	@Override
	public void update(Observable o, Object arg) {
		(new Thread(new Runnable() {

			@Override
			public void run() {
				/*
				for (Sector[] ss : modele.getMap()) {
					for (Sector s : ss) {

					}
				}

				for (ActiveEntity ae : modele.getActiveEntities().getAgents()) {

				}
				*/

				window.repaint();
				entities.repaint();
			}
		})).start();
	}


}
