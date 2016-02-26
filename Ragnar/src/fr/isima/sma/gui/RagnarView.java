package fr.isima.sma.gui;

import java.util.Observable;
import java.util.Observer;

import fr.isima.sma.resources.Properties;
import fr.isima.sma.world.City;

/*
 *	Vue du programme
 */
public class RagnarView implements Observer {
	
	protected Properties	props = Properties.getInstance();
	protected City 			modele;	/// modele
	protected AgentsView	agentsView;	/// fenetre des entites
	protected CityView		cityView;	/// zone d'affichage de la carte

	public RagnarView(City pModele) {
		modele = pModele;
		modele.addObserver(this);
		cityView = new CityView(modele, Integer.valueOf(props.getProperty("caseSize")));
		agentsView = new AgentsView(modele);
		agentsView.setLocation(cityView.getWidth()+cityView.getX(), cityView.getY());

		cityView.setVisible(true);
		agentsView.setVisible(true);
	}


	@Override
	public void update(Observable o, Object arg) {
		(new Thread(new Runnable() {

			@Override
			public void run() {
				cityView.repaint();
				agentsView.repaint();
			}
		})).start();
	}


}
