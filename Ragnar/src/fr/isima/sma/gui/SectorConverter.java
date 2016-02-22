package fr.isima.sma.gui;

import java.awt.Color;

import org.jdesktop.beansbinding.Converter;

import fr.isima.sma.world.Sector;
import fr.isima.sma.world.Sector.SectorType;

public class SectorConverter extends Converter<Sector.SectorType, Color> {
	static protected Color[] sectorStyle = {new Color(255,255,0), new Color(20,20,20), new Color(100,100,100), new Color(50,80,255), new Color(255,50,50)};

	@Override
	public Color convertForward(SectorType arg) {
		Color couleur = null;
		switch (arg) {
		case Bank:
			couleur = sectorStyle[0];
			break;
		case HeadQuarter:
			couleur = sectorStyle[1];
			break;
		case Street:
			couleur = sectorStyle[2];
			break;
		case HeroHQ:
			couleur = sectorStyle[3];
			break;
		case VilainHQ:
			couleur = sectorStyle[4];
			break;

		default:
			break;
		}

		return couleur;
	}

	@Override
	public SectorType convertReverse(Color arg) {
		// TODO Auto-generated method stub
		return null;
	}

}