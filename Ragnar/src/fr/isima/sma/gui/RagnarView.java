package fr.isima.sma.gui;

import java.util.Observable;
import java.util.Observer;

import fr.isima.sma.resources.Properties;
import fr.isima.sma.world.City;

/**
 *	Represents the entire view of the application.
 */
public class RagnarView implements Observer {
	
	protected Properties	props = Properties.getInstance();
	protected City 			modele;	/// modele
	protected ControlView	controlView;	/// fenetre des controles
	protected AgentsView	agentsView;	/// fenetre des entites
	protected CityView		cityView;	/// zone d'affichage de la carte

	/**
	 * Create the view of the MVC.
	 * @param pModele : model of the MVC
	 */
	public RagnarView(City pModele) {
		modele = pModele;
		modele.addObserver(this);
		controlView = new ControlView(modele);
		cityView = new CityView(modele, Integer.valueOf(props.getProperty("caseSize")));
		agentsView = new AgentsView(modele);
		agentsView.setLocation(cityView.getWidth()+cityView.getX(), cityView.getY());
		controlView.setLocation(agentsView.getX(), agentsView.getY()+agentsView.getHeight());

		controlView.setVisible(true);
		cityView.setVisible(true);
		agentsView.setVisible(true);
	}


	@Override
	public void update(Observable o, Object arg) {
		(new Thread(new Runnable() {

			@Override
			public void run() {
				cityView.repaint();

				try {
					agentsView.repaint();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				controlView.repaint();
			}
		})).start();
	}

	/**
	 * Getter for the ControlView.
	 * @return ControlView
	 */
	public ControlView getControlView() {
		return controlView;
	}
	
	/**
	 * Set the visibility of the GUI.
	 * @param b : boolean
	 */
	public void setVisibleGUI(boolean b) {
		cityView.setVisible(b);
		agentsView.setVisible(b);
	}

	/**
	 * Update display.
	 */
	public void dispose() {
		controlView.dispose();
		cityView.dispose();
		agentsView.dispose();
	}

}
