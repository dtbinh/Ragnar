package fr.isima.sma.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.RepaintManager;

import fr.isima.sma.world.*;
import fr.isima.sma.world.Sector.SectorType;

/*
 *	Vue du programme
 */
public class CityView implements Observer {
	
	protected City 			modele;	/// modele
	protected RagnarWindow	window;	/// fenetre de la map
	protected EntitiesView	entities;	/// fenetre des entites
	protected Map			map;	/// zone d'affichage de la carte
	protected Badge			hero;
	protected Badge			vilain;
	protected Badge			citizen;
	
	public CityView(City pModele) {
		window = new RagnarWindow();
		modele = pModele;
		modele.addObserver(this);
		map = new Map(modele.getMap(), 64);
		entities = new EntitiesView(modele);
		modele.addObserver(entities);
		window.getContentPane().add(map, BorderLayout.CENTER);
		window.setSize(map.getNbBlocsL()*map.getBlocsSize(), map.getNbBlocsH()*map.getBlocsSize());
		entities.setLocation(window.getWidth()+window.getX(), window.getY());
		//hero = new Case(SectorType.HeadQuarter);
		vilain = new Badge("assets\\hydra_badge.png");
		hero = new Badge("assets\\avengers_badge.png");
		citizen = new Badge("assets\\man_badge.png");
		
		(new Thread(new Runnable() {
			
			@Override
			public void run() {
				int n = 0;
				for (Sector[] ss : modele.getMap()) {
					for (Sector s : ss) {
						((Case) map.getComponent(n)).removeAll();
						((Case) map.getComponent(n++)).setCaseType(s.getType());
					}
				}
				window.validate();
				
				Badge cur = null;
				for (ActiveEntity ae : modele.getActiveEntities()) {
					if(ae.getClass() == Hero.class)
						cur = hero;
					else if(ae.getClass() == Vilain.class)
						cur = vilain;
					else
						cur = citizen;
					((Case) map.getComponent(ae.getLocation().getLocationX()+map.getNbBlocsL()*ae.getLocation().getLocationY())).add(new Badge( cur ));
				}
				//window.repaint();
				window.validate();				
			}
			
		})).start();
		window.setVisible(true);
		entities.setVisible(true);
	}
	

	@Override
	public void update(Observable o, Object arg) {
		(new Thread(new Runnable() {
			
			@Override
			public void run() {
				int n = 0;
				for (Sector[] ss : modele.getMap()) {
					for (Sector s : ss) {
						((Case) map.getComponent(n)).removeAll();
						((Case) map.getComponent(n++)).setCaseType(s.getType());
					}
				}
				window.validate();
				
				Badge cur = null;
				for (ActiveEntity ae : modele.getActiveEntities()) {
					if(ae.getClass() == Hero.class)
						cur = hero;
					else if(ae.getClass() == Vilain.class)
						cur = vilain;
					else
						cur = citizen;
					((Case) map.getComponent(ae.getLocation().getLocationX()+map.getNbBlocsL()*ae.getLocation().getLocationY())).add(new Badge( cur ));
				}
				//window.repaint();
				window.validate();				
			}
			
		})).start();
	}
	
	
}
