package fr.isima.sma.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class Map extends JPanel {
	static protected final Color RED = new Color(255,0,0);
	static protected final Color BLU = new Color(0,0,255);
	static protected final Color GRE = new Color(0,255,0);
	static protected final Color WHI = new Color(255,255,255);
	
	public Map(int pRows, int pCols, int pSize) {
		super(new GridLayout(pRows, pCols), true);
		setSize(pRows*pSize, pCols*pSize);
		for(int i = 0 ; i < pCols*pRows ; ++i) {
			this.add(new Case((i%2==0? BLU : RED)));
		}
		setVisible(true);
	}
	
	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		super.repaint();
	}
}
