package fr.isima.sma.world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fr.isima.sma.world.HeadQuarter.OwnerType;
import fr.isima.sma.world.Sector.SectorType;

public class City extends ActiveEntity implements ICityCitizen, Notifier {
	protected int 						currentTick;
	protected int 						tickPerHour;
	protected int 						heure;
	protected int 						jour;
	protected String 					name;
	protected Observable 				notifier;
	
	protected Sector[][] 				map;
	protected ArrayList<ActiveEntity> 	activeEntities;

	public City() {
		this(2000);
	}
	
	public City(int tickPerHour) {
		this.notifier = new Observable();
		this.currentTick = 0;
		this.heure = 0;
		this.jour = 0;
		this.tickPerHour = tickPerHour;
		activeEntities = new ArrayList<>();
	}
	/**
	 * Load the map from a file
	 * @param filePath the file to load
	 */
	public void loadFromFile(String filePath) { 
		/**
		 * Structure generale du fichier
		 * NOM DE LA VILLE
		 * TAILLEX TAILLEY
		 * <it> Le reste sous forme de la map </it>
		 * B : banque
		 * H : hq de heros
		 * V : hq de villains
		 * S : rues
		 * I : hq de citizens
		 */
		
		try {
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			
			/// Setting the name of the map
			String line = br.readLine();
			this.name = line;
			
			/// Allocating the map
			line = br.readLine();
			this.map = new Sector[Integer.parseInt((line.split(" "))[1])][];
			
			int xSize = Integer.parseInt((line.split(" "))[0]);
			for (int i = 0; i < map.length; i++) {
				this.map[i] = new Sector[xSize];
			}
			
			/// Filling the map
			int lineIndex = 0;
			while((line = br.readLine()) != null) {
				for (int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					switch (c) {
					case 'B':
						map[lineIndex][i]= new Bank();
						break;
					case 'H':
						map[lineIndex][i] = new HeadQuarter(HeadQuarter.OwnerType.Heroe);
						break;
					case 'V':
						map[lineIndex][i] = new HeadQuarter(HeadQuarter.OwnerType.Vilain);
						break;
					case 'S':
						map[lineIndex][i] = new Street();
						break;
					case 'I':
						map[lineIndex][i] = new HeadQuarter(HeadQuarter.OwnerType.Citizen);
						break;
					default:
						map[lineIndex][i] = new Street();
						break;
					}
				}
				lineIndex++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addActiveEntity(ActiveEntity entity) {
		this.activeEntities.add(entity);
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
	
	/**
	 * Print the map in its string format
	 * @return the formated string representing the map
	 */
	@Override
	public String toString() {
		String[][] out = new String[getSizeX()][getSizeY()];
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				out[i][j] = map[i][j].toString();
			}
		}
		
		for (ActiveEntity entity : activeEntities) {
			out[((Humanoid)entity).getLocation().getLocationX()][((Humanoid)entity).getLocation().getLocationY()] = "#";
		}
		
		String res = "";
		res += "Map : " + this.name + "\n";
		res += "Size : " + map.length + " x " + map[0].length + "\n\n";
		
		for (int i = 0; i < out.length; i++) {
			for (int j = 0; j < out[0].length; j++) {
				res += out[i][j] + " ";
			}
			res += "\n";
		}
		
		return res;
	}

	@Override
	public int getHeure() {
		return this.heure;
	}

	/**
	 * Get the sector on which the citizen is
	 * @param citizen the guy whi need its sector
	 * @return the sector on which it is !
	 */
	@Override
	public Sector getSector(Citizen citizen) {
		int x = citizen.getLocation().getLocationX();
		int y = citizen.getLocation().getLocationY();
		
		return map[x][y];
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
	public Sector[][] getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Sector[][] map) {
		this.map = map;
	}

	/**
	 * @param heure the heure to set
	 */
	public void setHeure(int heure) {
		this.heure = heure;
	}
	
	/**
	 * @return the x size of the map
	 */
	public int getSizeX () {
		if(map[0].length == 0) {
			System.err.println("Map init might have failed !");
		}
		return map[0].length;
	}
	
	/**
	 * @return the y size of the map
	 */
	public int getSizeY() {
		if(map.length == 0) {
			System.err.println("Map init might have failed !");
		}
		return map.length;
	}

	@Override
	public void addObserver(Observer o) {
		notifier.addObserver(o);
	}

	@Override
	public int countObservers() {
		return notifier.countObservers();
	}

}
