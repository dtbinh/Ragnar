package fr.isima.sma.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

import fr.isima.sma.world.Sector;

public class Map extends JPanel {
	static protected final Color RED = new Color(255,0,0);
	static protected final Color BLU = new Color(0,0,255);
	static protected final Color GRE = new Color(0,255,0);
	static protected final Color WHI = new Color(255,255,255);
	
	protected int size;	// taille d'un bloc
	protected int nbBlocLargeur;
	protected int nbBlocHauteur;
	
	public Map(Sector [][] map, int pSize) {
		super(new GridLayout(map.length, map[0].length), true);
		setSize(map.length*pSize, map[0].length*pSize);
		for (Sector[] ss : map) {
			for (Sector s : ss) {
				add(new Case(s.getType()));
			}
		}
		size = pSize;
		nbBlocLargeur = map[0].length;
		nbBlocHauteur = map.length;
		setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(Component c : this.getComponents()) {
			c.repaint();
		}
	}
	
	public int getNbBlocsL() {
		return nbBlocLargeur;
	}
	
	public int getNbBlocsH() {
		return nbBlocHauteur;
	}
	
	public int getBlocsSize() {
		return size;
	}
}
