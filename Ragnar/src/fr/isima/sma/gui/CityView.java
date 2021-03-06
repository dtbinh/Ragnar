package fr.isima.sma.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.plaf.DimensionUIResource;

import fr.isima.sma.resources.Properties;
import fr.isima.sma.world.City;
import fr.isima.sma.world.Sector;

/**
 * Represents graphically the city's map.
 */
public class CityView extends JFrame {

	private static final long serialVersionUID = 1L;
	private Properties props = Properties.getInstance();
	static protected final Color RED = new Color(255,0,0);
	static protected final Color BLU = new Color(0,0,255);
	static protected final Color GRE = new Color(0,255,0);
	static protected final Color WHI = new Color(255,255,255);

	protected int size;	// taille d'un bloc
	protected int nbBlocLargeur;
	protected int nbBlocHauteur;

	/**
	 * Create the view of the city.
	 * @param pModele : city to represent
	 * @param pSize : size of the unit squares
	 */
	public CityView(City pModele, int pSize) {

			super();
			getContentPane().setLayout(new BorderLayout());
			setTitle(props.getProperty("windowsname"));
			setPreferredSize(new DimensionUIResource(800, 600));
			setAlwaysOnTop(true);
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocation(50, 50);
			pack();
		
		this.getContentPane().setLayout(new GridLayout(pModele.getMap().length, pModele.getMap()[0].length));
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

	/**
	 * Getter for the number of case (width).
	 * @return int : number of case (width)
	 */
	public int getNbBlocsL() {
		return nbBlocLargeur;
	}

	/**
	 * Getter for the number of case (height).
	 * @return int : number of case (height)
	 */
	public int getNbBlocsH() {
		return nbBlocHauteur;
	}

	/**
	 * Getter for the size of blocs.
	 * @return int : size
	 */
	public int getBlocsSize() {
		return size;
	}
	
	/**
	 * Override of paint.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (Component c : this.getComponents()) {
			c.repaint();
		}
	}
}
