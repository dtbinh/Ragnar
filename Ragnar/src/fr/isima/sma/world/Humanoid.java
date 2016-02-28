/**
 * 
 * Humanoid class used to implement citizens and heroes/vilains
 * It corresponds to the "agent" class
 * 
 */

package fr.isima.sma.world;

import java.util.Random;

import fr.isima.sma.resources.Properties;
import fr.isima.sma.simulator.events.Event;

//import fr.isima.sma.world.Humanoid.BadAgeException;
public abstract class Humanoid extends ActiveEntity {
	// Static members
	protected static City city;
	protected static Random rand = new Random(123); // TODO remove the seed at the end
	protected static final int ageMax = 90;
	static protected int daysPerYear = Integer.valueOf(Properties.getInstance().getProperty("daysperyear"));
	
	// Class members
	protected String name;
	protected String surname;
	protected int age;
	protected int ageInDays;
	protected String url;
	protected ActiveEntity.AgentType type;
	protected HeadQuarter home;
	protected int[][] path; // The path to home, containing x and y locations
	protected int pathStep; // At which point am i in the path to home
	protected int level;
	protected boolean superPotential;
	protected double superChance;
	protected double chanceToDie;
	protected LifeState alive;
	protected double fertilite;
	protected boolean wantRobery = false;
	protected boolean wantRelease = false;
	
	protected Event involved;	// null si n'est pas concerné par un event
	
	// syteme monetaire
	protected int money;
	
	/**
	 * Default constructor
	 */
	public Humanoid() {
		this(AgentType.CITIZEN,"","",0);
		// By default the entities name, surname and age are randomly generated
		
	}
	
	/**
	 * Normal constructor with fewer arguments
	 * @param name
	 * @param surname
	 * @param age
	 * @throws BadAgeException
	 */
	public Humanoid(AgentType type, String name, String surname, int age) {
		// Default can be placed in the HQ
		this(type, name, surname, age, 1, 0, 0);
		setInvolved(null);
		money = 0;
	}
	
	/**
	 * Specific constructor
	 * @param name name of the entity
	 * @param surname surname of the entity
	 * @param age age of the entity
	 * @throws BadAgeException if the age is not in the range ]0 - <code>maxAge</code>[
	 */
	public Humanoid(AgentType type, String name, String surname, int age, int speed, int ligne, int colonne) {
		this.name = name;
		this.surname = surname;
		this.speed = speed;
		this.type = type;
		this.url = "";
		
		if(age >= 0 && age < Humanoid.ageMax) {
			this.age = age;
		} else {
			System.err.println("Erreur sur l'age de : " + name);
		}
		
		this.location = new Location(colonne, ligne);
		city.getSector(this).setArriveHumanoid(this.type, this);
		city.getSector(this).setNumberHumanoid(type, city.getSector(this).getNumberHumanoid(type)+1);
		firePropertyChange("location", new Location(), this.location);
		try {
			setHome((HeadQuarter)city.getSector(this));			
		} catch (ClassCastException e) {
			System.err.println(name + " " + surname + " ne peut avoir son domicile en " + colonne + "-" + ligne);
		}
		setMoney(0);
		
		path = null;
		pathStep = -1;
		
		ageInDays = age * daysPerYear;
		superPotential = false;
		level = 1;
		setChanceToDie(age<40?0.0005:(age < 65 ? 0.0005/daysPerYear*(age-39) : 0.0125+0.005/daysPerYear*(age-64)));
		superChance = 0;
		alive = age < 16 ? LifeState.CHILD : LifeState.ALIVE;
		fertilite = age > 16 ? (age <40?1:Math.pow(0.95,age-40))*(0.12+Humanoid.rand.nextDouble()/20)/daysPerYear : 0;
	}
	
	/**
	 * Randomly choose where to go. It can move full speed or not
	 */
	public void moveRandom() {
		int bound = 2 * this.speed + 1; // The max of the random, +1 because it is exclusive value
		this.location.shiftLocation(Humanoid.rand.nextInt(bound) - speed, Humanoid.rand.nextInt(bound) - speed);
	}
	
	@Override
	public void setLocation(int x, int y) {
		Location old = this.location;
		city.getSector(this).setLeaveHumanoid(type, this);
		city.getSector(this).setNumberHumanoid(type, city.getSector(this).getNumberHumanoid(type)-1);
		this.location.setLocation(x, y);
		city.getSector(this).setArriveHumanoid(type, this);
		city.getSector(this).setNumberHumanoid(type, city.getSector(this).getNumberHumanoid(type)+1);
		firePropertyChange("location", old, this.location);
	}
	
	public void move(int locationX, int locationY) {
		// TODO je peux pas aller plus vite que la speed
		this.setLocation(new Location(locationX, locationY));
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder st = new StringBuilder(name).append(name!=""?" ":"").append(surname).append("  -  ").append(this.getClass().getSimpleName());
		return st.toString();
	}
	
	/** finds the Path from (startX, startY) to (endX,endY)
	 * @param startX starting X-Coordinate
	 * @param startY starting Y-Coordinate
	 * @param endX target X-Coordinate
	 * @param endY target Y-Coordinate
	 * @param walkableTiles Map representation of walkable tiles
	 * @return path array with x & y coordinates
	 */
	public int[][] findPath(int startX, int startY, int endX, int endY, boolean[][] walkable) {
	    /* first we get the the width and length of the map */
	    int length = walkable.length;
	    int width = walkable[0].length;
	    
	    ////////////////////////////////////////////
	    boolean oldStart = walkable[startX][startY];
	    boolean oldEnd = walkable[endX][endY]; 
	    walkable[startX][startY] = true;
	    walkable[endX][endY] = true;
	    ////////////////////////////////////////////
	    
	    /* now we initialize an int array and fill it with -1 */
	    int[][] distance = new int[length][width];
	    for(int i=0; i < distance.length; i++) {
	        for(int j=0; j < distance[i].length; j++) {
	            distance[i][j] = -1;
	        }
	    }

	    /* our target point is (endX,endY) so there is distance = 0 */
	    distance[endX][endY] = 0;
	    /* we need to store the steps from the end point to the next Tiles in a variable */
	    int steps = 1;

	    /* now we'll mark every tile how much steps there are from the current Point to the end point until we hit the start point*/
	    while(distance[startX][startY] == -1) {
	        for(int i=0; i < distance.length; i++) {
	            for(int j=0; j < distance[i].length; j++) {
	                /* check if the current Tile is have the distance of the last marked Tile */
	                if(distance[i][j] == steps - 1) {
	                    /* check if the Tile right from the current Tile is valid */
	                    if(i < width - 1) {
	                        /* check if that Tile is not marked and if it is walkable*/
	                        if(distance[i+1][j] == -1 && walkable[i+1][j]) {
	                            /* not marked & walkable, so we can mark it */
	                            distance[i+1][j] = steps;
	                        }
	                    }

	                    /* check if the Tile left from the current Tile is valid */
	                    if(i > 0) {
	                        /* check if that Tile is not marked and if it is walkable*/
	                        if(distance[i-1][j] == -1 && walkable[i-1][j]) {
	                            /* not marked & walkable, so we can mark it */
	                            distance[i-1][j] = steps;
	                        }
	                    }

	                    /* check if the Tile underneath the current Tile is valid */
	                    if(j < length - 1) {
	                        /* check if that Tile is not marked and if it is walkable*/
	                        if(distance[i][j+1] == -1 && walkable[i][j+1]) {
	                            /* not marked & walkable, so we can mark it */
	                            distance[i][j+1] = steps;
	                        }
	                    }

	                    /* check if the Tile above the current Tile is valid */
	                    if(j > 0) {
	                        /* check if that Tile is not marked and if it is walkable*/
	                        if(distance[i][j-1] == -1 && walkable[i][j-1]) {
	                            /* not marked & walkable, so we can mark it */
	                            distance[i][j-1] = steps;
	                        }
	                    }
	                }
	            }
	        }
	        /* increment steps after each map check */
	        steps++;
	    }

	    /* now we have checked every Tile, so we can start making a path */
	    int[][] path = new int[steps][2];
	    /* we start with the start point */
	    path[0][0] = startX;
	    path[0][1] = startY;

	    int i = 0;
	    /* now we go on until we hit the end point */
	    while(path[i][0] != endX && path[i][1] != endY) {
	        /* get current tile coordinates */
	        int x = path[i][0];
	        int y = path[i][1];
	        /* we have everything from the current tile so increment i */
	        i++;

	        /* check if the field right from the current field is one step away */
	        if((x + 1 < length) && ((distance[x+1][y]) == (distance[x][y] - 1))) {
	            /* that tile is one step away, so take it into the path */
	            path[i][0] = x+1;
	            path[i][1] = y;
	        }

	        /* check if the field left from the current field is one step away */
	        else if( (x - 1 >= 0) && (distance[x-1][y] == (distance[x][y] - 1)) ) {
	            /* that tile is one step away, so take it into the path */
	            path[i][0] = x-1;
	            path[i][1] = y;
	        }

	        /* check if the field underneath the current field is one step away */
	        else if( (y + 1 < width) && (distance[x][y+1] == (distance[x][y] - 1)) ) {
	            /* that tile is one step away, so take it into the path */
	            path[i][0] = x;
	            path[i][1] = y+1;
	        }

	        /* check if the field above the current field is one step away */
	        else if( (y - 1 >= 0) && (distance[x][y-1] == (distance[x][y] - 1)) ) {
	            /* that tile is one step away, so take it into the path */
	            path[i][0] = x;
	            path[i][1] = y-1;
	        }
	    }
	    
	    path[i][0] = endX;
	    path[i][1] = endY;
	    
		////////////////////////////////////////////
		walkable[startX][startY] = oldStart;
		walkable[endX][endY] = oldEnd;
		////////////////////////////////////////////
	    
	    /* now we should have the finished path array */
	    // note that path[i][0] has the X-Coordinate and path[i][1] has the Y-Coordinate!
	    
	    this.pathStep = 0; // Init the path step, and we are ready to go just after
	    
	    return path;
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
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}


	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static void setCity(City m) {
		city = m;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		int old = this.money;
		this.money = money;
		firePropertyChange("money", old, this.money);
	}

	public void setLevel(int i) {
		this.level = i;
	}

	public int getLevel() {
		return this.level;
	}
	
	// veillissement
	public void vieillissement() {
		if(city.getHeure() == 1) {
			++ageInDays;
			if(ageInDays%daysPerYear==0) {
				age++;
				if(age == 16) {
					superPotential = true;
					superChance = (Humanoid.rand.nextDouble()/10);
					fertilite = (0.12+Humanoid.rand.nextDouble()/20)/daysPerYear;
					city.demenager(this);
				} else if(age == 30) {
					superPotential = false;
				} else if(age >= 90) {
					setChanceToDie(getChanceToDie() + (0.02/daysPerYear));
					fertilite *= 0.95;
				} else if(age >= 65) {
					setChanceToDie(getChanceToDie() + (0.005/daysPerYear));
					fertilite *= 0.95;
				} else if(age >= 40) {
					setChanceToDie(getChanceToDie() + (0.0005/daysPerYear));
					fertilite *= 0.95;
				}
				
				if(superPotential) {
					boolean becomeSuper = (Humanoid.rand.nextDouble()<superChance);
					if(becomeSuper) {
						city.becomeSuper(this,(Humanoid.rand.nextBoolean()?ActiveEntity.AgentType.HERO:ActiveEntity.AgentType.VILAIN));
						superPotential = false;
					}
				}
			}
			if(Humanoid.rand.nextDouble() < getChanceToDie()) {
				alive = LifeState.DEAD;
				city.becomeDead(this);
			}
		}
	}
	
	/**
	 * Mort de l'humanoid
	 */
	public void becomeDead() {
		city.becomeDead(this);
	}

	public LifeState getAlive() {
		return alive;
	}

	public void setAlive(LifeState alive) {
		this.alive = alive;
	}

	public HeadQuarter getHome() {
		return home;
	}

	public void setHome(HeadQuarter home) {
		this.home = home;
	}

	public double getChanceToDie() {
		return chanceToDie;
	}

	public void setChanceToDie(double chanceToDie) {
		this.chanceToDie = chanceToDie;
	}
	
	public AgentType getType() {
		return type;
	}

	public String toResult() {
		StringBuilder b = new StringBuilder(name).append("\t").append(surname).append("\t").append(type).append("\t").append(age).append("\t").append(level);
		return b.toString();
	}

	public Event getInvolved() {
		return involved;
	}

	public void setInvolved(Event involved) {
		this.involved = involved;
	}

	public boolean getWantRobery() {
		return wantRobery;
	}
	
	public void setWantRobery(boolean pwantRobery) {
		this.wantRobery = pwantRobery;
	}

	public void setWantRelease(boolean b) {
		wantRelease = b;
	}

	public boolean getWantRelease() {
		return wantRelease;
	}

	public static City getCity() {
		return city;
	}
	
	
}
