package fr.isima.sma.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import fr.isima.sma.world.Sector.SectorType;

public class Case extends JPanel {
	
	protected SectorType 	type;
	protected Color[]		caseStyle = {new Color(255,255,0), new Color(20,20,20), new Color(100,100,100), new Color(50,80,255), new Color(255,50,50)};
	
	public Case(SectorType ptype) {
		super(new GridLayout(3,3), true);
		setCaseType(ptype);
		setSize(128, 128);
		setVisible(true);
	}
	
	public void setCaseType(SectorType ptype) {
		type = ptype;
		setCaseColor();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	protected void setCaseColor() {
		switch (type) {
		case Bank:
			this.setBackground(caseStyle[0]);
			break;
		case HeadQuarter:
			this.setBackground(caseStyle[1]);
			break;
		case Street:
			this.setBackground(caseStyle[2]);
			break;
		case HeroHQ:
			this.setBackground(caseStyle[3]);
			break;
		case VilainHQ:
			this.setBackground(caseStyle[4]);
			break;

		default:
			break;
		}
	}
}
