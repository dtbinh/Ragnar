package fr.isima.sma.world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class City extends ActiveEntity implements ICityCitizen {
	protected int currentTick;
	protected int tickPerHour; 
	protected int heure;
	protected int jour;
	protected String name;
	
	protected ArrayList< ArrayList<Sector> > map; 

	public City() {
		// TODO
	}
	
	public City(int tickPerHour) {
		this.currentTick = 0;
		this.heure = 0;
		this.jour = 0;
		this.tickPerHour = tickPerHour;
	}
	
	public void loadFromFile(String filePath) { 
		/*
		 * Structure generale du fichier
		 * NOM DE LA VILLE
		 * 
		 * Elements
		 * B : banque
		 * H : hq de heros
		 * V : hq de villains
		 * S : rues
		 * I : hq de citizens
		 */
		
		try {
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			
			// TODO finish
			
			while((line = br.readLine()) != null) {
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void live() {
		currentTick++;
		
		if(currentTick == tickPerHour) {
			heure++;
			
			if(heure == 24) {
				heure = 0;
				jour++;
			}
		}
	}

	@Override
	public int getHeure() {
		return this.heure;
	}

	@Override
	public Sector getSector(Citizen citizen) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void depositMoney(Citizen citizen, int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Location> pathToHome(Citizen citizen) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the currentTick
	 */
	public int getCurrentTick() {
		return currentTick;
	}

	/**
	 * @param currentTick the currentTick to set
	 */
	public void setCurrentTick(int currentTick) {
		this.currentTick = currentTick;
	}

	/**
	 * @return the tickPerHour
	 */
	public int getTickPerHour() {
		return tickPerHour;
	}

	/**
	 * @param tickPerHour the tickPerHour to set
	 */
	public void setTickPerHour(int tickPerHour) {
		this.tickPerHour = tickPerHour;
	}

	/**
	 * @return the jour
	 */
	public int getJour() {
		return jour;
	}

	/**
	 * @param jour the jour to set
	 */
	public void setJour(int jour) {
		this.jour = jour;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the map
	 */
	public ArrayList<ArrayList<Sector>> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(ArrayList<ArrayList<Sector>> map) {
		this.map = map;
	}

	/**
	 * @param heure the heure to set
	 */
	public void setHeure(int heure) {
		this.heure = heure;
	}

}
