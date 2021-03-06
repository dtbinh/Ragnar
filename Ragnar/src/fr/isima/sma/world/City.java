package fr.isima.sma.world;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import org.apache.commons.math3.random.MersenneTwister;

import fr.isima.sma.resources.NameLoader;
import fr.isima.sma.resources.Properties;
import fr.isima.sma.world.Sector.SectorType;
import fr.isima.sma.world.patterns.AgentsList;
import fr.isima.sma.world.patterns.Console;
import fr.isima.sma.world.patterns.IMyObservable;
import fr.isima.sma.world.patterns.MyObservable;

public class City extends ActiveEntity implements IMyObservable {
	protected static MersenneTwister 	rand;
	
	static {
		rand = new MersenneTwister(Integer.valueOf(Properties.getInstance().getProperty("rand"))); // TODO remove the seed at the end
	}
	
	protected Properties				props = Properties.getInstance();
	protected int 						currentTick;
	protected int 						tickPerHour;
	protected int 						heure;
	protected int 						jour;
	protected int 						annee;
	protected String 					name;
	protected MyObservable 				notifier;

	protected Sector[][] 				map;
	protected List<List<Sector>>		sectorByType;
	protected AgentsList<Humanoid>		agents;
	protected List<Humanoid>			deadAgents;
	
	protected boolean[][]		walkableLegit = null; // True for the street, false for everithign else
	protected boolean[][]		walkableCheat = null; // True everywhere

	public City() {
		this(Integer.valueOf(Properties.getInstance().getProperty("ticksPerHour")));
	}

	public City(int tickPerHour) {
		super();
		this.notifier = new MyObservable();
		this.currentTick = 0;
		this.heure = 0;
		this.jour = 0;
		this.tickPerHour = tickPerHour;
		agents = new AgentsList<>("agents");
		sectorByType = new ArrayList<>(5);
		for(int i = 0 ; i < Sector.SectorType.values().length ; i++) {
			sectorByType.add(new ArrayList<Sector>(100));
		}
		props = Properties.getInstance();
		deadAgents = new ArrayList<>();
	}

	/**
	 * Load the agents from a file
	 * @param filePath the file to load
	 */
	public void loadAgentsFromFile(String filePath) {
		// nettoyage
		agents = new AgentsList<>("agents");

		try (
				FileReader file = new FileReader(filePath);
				BufferedReader data = new BufferedReader(file);
			)
		{
			// lecture du fichier .properties

			String line = data.readLine();
			String agent[];

			while(line != null) {
				if(!line.contains("#") && !line.isEmpty()) {
					agent = line.split(":",-1);
					if(agent.length >= 8) {
						Humanoid nouveau = null;
						switch (agent[0]) {
						
							case "Citizen":
								nouveau = new Citizen(agent[1], agent[2], Integer.valueOf(agent[3]), Integer.valueOf(agent[4]), Integer.valueOf(agent[6]), Integer.valueOf(agent[5]));							
								break;
								
							case "Hero":
								nouveau = new Hero(agent[1], agent[2], Integer.valueOf(agent[3]), Integer.valueOf(agent[4]), Integer.valueOf(agent[6]), Integer.valueOf(agent[5]));	
								break;
								
							case "Vilain":
								nouveau = new Vilain(agent[1], agent[2], Integer.valueOf(agent[3]), Integer.valueOf(agent[4]), Integer.valueOf(agent[6]), Integer.valueOf(agent[5]));							
								break;
		
							default:
								break;
						}
						nouveau.setUrl(agent[7]);
						agents.addAgent(nouveau);
					}
				}
				line = data.readLine();
			}

			// fin de lecture

		} catch (IOException e) {
			System.err.println("Le fichier n'a pas pu �tre ouvert.");
			e.printStackTrace();
		}
	}
	/**
	 * Load the map from a file
	 * @param filePath the file to load
	 */
	public void loadCityFromFile(String filePath) {
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

		try (
				FileReader fr = new FileReader(filePath);
				BufferedReader br = new BufferedReader(fr);
			)
		{



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
					map[lineIndex][i].setLocation(new Location(i,lineIndex));
					sectorByType.get(map[lineIndex][i].getType().getValue()).add(map[lineIndex][i]);
				}
				lineIndex++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addActiveEntity(Humanoid entity) {
		this.agents.addAgent(entity);
	}

	public AgentsList<Humanoid> getActiveEntities() {
		return this.agents;
	}

	public void setActiveEntities(AgentsList<Humanoid> pActiveEntities) {
		this.agents = pActiveEntities;
	}

	@Override
	public void live() {
		currentTick++;

		if(currentTick >= tickPerHour) {
			heure++;

			if(heure == 24) {
				heure = 0;
				jour++;
				if(jour == Integer.valueOf(props.getProperty("daysperyear"))) {
					jour = 0;
					setAnnee(getAnnee() + 1);
					firePropertyChange("annee", annee-1, annee);	// BINDING
				}
			}

			firePropertyChange("heure", heure-1, heure);	// BINDING
			
			currentTick = 0;
		}
		
		// update of sectors' money
		if(heure==3) {
			for (List<Sector> list : sectorByType) {
				for (Sector s : list) {
					s.ruleEconomy();
				}
			}
		}
		
		// maybe new inhabitants
		if(heure==6) {
			if(City.rand.nextDouble() < (0.9 / Integer.valueOf(props.getProperty("daysperyear")))) {
				int numberMigrants = (int) (City.rand.nextGaussian()*2+3);
				numberMigrants = numberMigrants < 1 ? 1 : numberMigrants;
				for (int i = 0; i < numberMigrants; i++) {
					imigration();
				}
				Console.println(getDate() + " Arriv�e de " + numberMigrants + " nouveaux habitants en ville.");
			}
		}
		
		agents.update();
		notifier.setChanged();
		notifier.notifyObservers();
	}

	/**
	 * Simulate arriving of people from outside the city.
	 */
	private void imigration() {
		int age = (int) (City.rand.nextGaussian()*20+40);
		age = age<16 ? 16 : age;
		Sector home = sectorByType.get(SectorType.HeadQuarter.getValue()).get(City.rand.nextInt(sectorByType.get(SectorType.HeadQuarter.getValue()).size()));
		this.agents.addAgent(new Citizen(NameLoader.getInstance().getName(), NameLoader.getInstance().getName(), age, heure, home.getLocation().getLocationY(), home.getLocation().getLocationX()));
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

		for (ActiveEntity entity : agents.getAgents()) {
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

	public int getHeure() {
		return this.heure;
	}

	/**
	 * Get the sector on which the citizen is
	 * @param citizen the guy who need its sector
	 * @return the sector on which it is !
	 */
	public Sector getSector(ActiveEntity citizen) {
		int x = citizen.getLocation().getLocationX();
		int y = citizen.getLocation().getLocationY();

		return map[y][x];
	}
	
	@Deprecated
	public void depositMoney(Citizen citizen, int amount) {

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
	public void notifyObservers() {
		notifier.notifyObservers();
	}

	@Override
	public void addObserver(Observer o) {
		notifier.addObserver(o);
	}

	@Override
	public int countObservers() {
		return notifier.countObservers();
	}

	@Override
	public void notifyObservers(Object o) {
		notifier.notifyObservers(o);
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public List<Sector> getNeighborhood(Humanoid agent) {
		List<Sector> voisinnage = new ArrayList<>();

		int x = agent.getLocation().getLocationX();
		int y = agent.getLocation().getLocationY();
		
		for(int i = Integer.max(Location.getMinLocationX(),x-agent.speed) ; i <= Integer.min(Location.getMaxLocationX()-1,x+agent.speed) ; i++) {
			for(int j = Integer.max(Location.getMinLocationY(),y-agent.speed) ; j <= Integer.min(Location.getMaxLocationY()-1,y+agent.speed) ; j++) {
				voisinnage.add(map[j][i]);
			}
		}
		
		return voisinnage;
	}

	public void becomeSuper(Humanoid h, ActiveEntity.AgentType type) {
		Console.println(getDate() + " " + h.getName() + " " + h.getSurname() + " est devenu un " + type);
		
		Humanoid newLife = null;
		
		if(type == AgentType.HERO) {
			List<Sector> lhq = sectorByType.get(SectorType.HeroHQ.getValue());
			HeadQuarter qg = (HeadQuarter) lhq.get(Humanoid.rand.nextInt(lhq.size()));
			newLife = new Hero(h.getName(), h.getSurname(), h.getAge(), h.getSpeed()*4, qg.getLocation().getLocationY(), qg.getLocation().getLocationX());
			newLife.setUrl(h.getUrl());
		} else {
			List<Sector> lhq = sectorByType.get(SectorType.VilainHQ.getValue());
			HeadQuarter qg = (HeadQuarter) lhq.get(Humanoid.rand.nextInt(lhq.size()));
			newLife = new Vilain(h.getName(), h.getSurname(), h.getAge(), h.getSpeed()*4, qg.getLocation().getLocationY(), qg.getLocation().getLocationX());
			newLife.setUrl(h.getUrl());
		}

		agents.removeAgent(h);
		h.setAlive(LifeState.DEAD);
		agents.addAgent(newLife);
		getSector(h).setLeaveHumanoid(h.type, h);
		getSector(h).setNumberHumanoid(h.type, getSector(h).getNumberHumanoid(h.type)-1);
	}

	public void becomeDead(Humanoid humanoid) {
		Console.println(getDate() + " " + humanoid.getName() + " " + humanoid.getSurname() + " est mort(e) � l'�ge de " + humanoid.getAge() + " ans.");
		deadAgents.add(humanoid);
		agents.removeAgent(humanoid);
		getSector(humanoid).setLeaveHumanoid(humanoid.type, humanoid);
		getSector(humanoid).setNumberHumanoid(humanoid.type, getSector(humanoid).getNumberHumanoid(humanoid.type)-1);
	}

	public void createNewBabyCitizen(Humanoid parent) {
		Citizen baby = new Citizen(NameLoader.getInstance().getName(), parent.getSurname(), 0, 1, parent.getHome().getLocation().getLocationY(), parent.getHome().getLocation().getLocationX());
		agents.addAgent(baby);
		Console.println(getDate() + " Naissance de " + baby.getName() + " " + baby.getSurname());
	}

	public void demenager(Humanoid humanoid) {
		List<Sector> l = sectorByType.get(SectorType.HeadQuarter.getValue());
		humanoid.setHome((HeadQuarter) l.get(Humanoid.rand.nextInt(l.size())));
		Console.println(getDate() + " " + humanoid.getName() + " " + humanoid.getSurname() + " d�m�nage en " + humanoid.home);
	}
	
	public String getDate() {
		return new StringBuilder("[").append(jour).append("/").append(annee).append("]").append("(").append(heure).append(":00)").toString();
	}
	
	public boolean[][] getWalkableLegit() {
		if(this.walkableLegit == null) {
			walkableLegit = new boolean[this.getSizeX()][this.getSizeY()];
			for(Sector[] tab : this.map) {
				for(Sector s : tab) {
					int x = s.location.getLocationX();
					int y = s.location.getLocationY();
					
					walkableLegit[x][y] = (s.type == SectorType.Street);
				}
			}
		}
		
		return walkableLegit;
	}
	
	public boolean[][] getWalkableCheat() {
		if(this.walkableCheat == null) {
			walkableCheat = new boolean[this.getSizeX()][this.getSizeY()];
			
			for(int x = 0; x < walkableCheat.length; x++) {
				for(int y = 0; y < walkableCheat[0].length; y++) {
					walkableCheat[x][y] = true;
				}
			}
		}
		
		return walkableCheat;
	}

	public List<Humanoid> getDeadAgents() {
		return this.deadAgents;
	}

	public List<List<Sector>> getSectorByType() {
		return sectorByType;
	}
	
	/**
	 * Count all the heroes in this city
	 * @return The nimber of heroes, 0 if there is none
	 */
	public int getTotalHeroes() {
		int nb = 0;
		
		for (Humanoid humanoid : agents.getAgents()) {
			if(humanoid.type == AgentType.HERO) {
				nb += 1;
			}
		}
		
		return nb;
	}
}
