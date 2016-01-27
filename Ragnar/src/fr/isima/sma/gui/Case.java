package fr.isima.sma.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import fr.isima.sma.world.Sector.SectorType;

public class Case extends JPanel {
	
	protected SectorType 	type;
	protected Color[]		caseStyle = {new Color(255,0,0), new Color(255,255,0), new Color(255,0,255)};
	
	public Case(SectorType ptype) {
		super(new GridLayout(), true);
		setCaseType(ptype);
		setVisible(true);
	}
	
	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		super.repaint();
	}
	
	public void setCaseType(SectorType ptype) {
		type = ptype;
		setCaseColor();
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

		default:
			break;
		}
	}
}
