package fr.isima.sma.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class Case extends JPanel {
	public Case(Color c) {
		super(new GridLayout(), true);
		this.setBackground(c);
		setVisible(true);
	}
	
	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		super.repaint();
	}
}
