package fr.isima.sma.world;

import java.util.ArrayList;

public interface ICityCitizen extends ICity {

	public int getHeure(); // Heure courant de la map
	public Sector getSector(ActiveEntity citizen); // Precise sur quel secteur on se trouve
	public void depositMoney(Citizen citizen, int amount); // Pour donner l'argent a la banque
	public ArrayList<Location> pathToHome(Citizen citizen); // Retourne une liste de positions a suivre pour rentrer

}
