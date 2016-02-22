package fr.isima.sma.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

import fr.isima.sma.world.City;
import fr.isima.sma.world.Sector;

public class Map extends JPanel {

	private static final long serialVersionUID = 1L;
	static protected final Color RED = new Color(255,0,0);
	static protected final Color BLU = new Color(0,0,255);
	static protected final Color GRE = new Color(0,255,0);
	static protected final Color WHI = new Color(255,255,255);

	protected int size;	// taille d'un bloc
	protected int nbBlocLargeur;
	protected int nbBlocHauteur;

	public Map(City pModele, int pSize) {
		super(new GridLayout(pModele.getMap().length, pModele.getMap()[0].length), true);
		setSize(pModele.getMap().length*pSize, pModele.getMap()[0].length*pSize);
		int i = 0;
		for (Sector[] ss : pModele.getMap()) {
			int j = 0;
			for (int k = 0; k < ss.length; k++) {
				add(new SectorView(pModele, i, j));
				j++;
			}
			i++;
		}
		size = pSize;
		nbBlocLargeur = pModele.getMap()[0].length;
		nbBlocHauteur = pModele.getMap().length;
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
