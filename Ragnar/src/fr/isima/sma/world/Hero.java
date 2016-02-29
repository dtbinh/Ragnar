package fr.isima.sma.world;

import java.util.List;

import fr.isima.sma.simulator.events.Event;
import fr.isima.sma.simulator.events.EventType;
import fr.isima.sma.world.Sector.SectorType;
import fr.isima.sma.world.patterns.Console;

public class Hero extends Super {

	public Hero(String name, String surname, int age, int speed, int ligne, int colonne) {
		super(AgentType.HERO, name, surname, age, speed, ligne, colonne);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#moveRandom()
	 */
	@Override
	public void moveRandom() {
		super.moveRandom();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#move(int, int)
	 */
	@Override
	public void move(int ligne, int colonne) {
		super.move(ligne, colonne);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#getName()
	 */
	@Override
	public String getName() {
		return super.getName();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		super.setName(name);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#getSurname()
	 */
	@Override
	public String getSurname() {
		return super.getSurname();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#setSurname(java.lang.String)
	 */
	@Override
	public void setSurname(String surname) {
		super.setSurname(surname);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#getAge()
	 */
	@Override
	public int getAge() {
		return super.getAge();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#setAge(int)
	 */
	@Override
	public void setAge(int age) {
		super.setAge(age);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#getSpeed()
	 */
	@Override
	public int getSpeed() {
		return super.getSpeed();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#setSpeed(int)
	 */
	@Override
	public void setSpeed(int speed) {
		super.setSpeed(speed);
	}

	@Override
	public void live() {
		if(this.path != null) { // Il doit aller a quelque part
			if(this.pathStep >=0 && pathStep < path.length) {
				this.setLocation(this.path[pathStep][0], this.path[pathStep][1]); // Il bouge
				this.pathStep += 1; // Prochain pas
			}
			
			if( !(this.pathStep < path.length)) { // A destination
				this.path = null; // Clean le path et il reprendra sa vie
				this.pathStep = -1;
			}
			
		} else { // Il se promene
		
			Sector toGo = null; // Le secteur ou il ira
			Sector here = city.getSector(this); // Secteur actuel
			double moveProb = 1.0; // Probabilite de deplacement de base
			List<Sector> voisinage = city.getNeighborhood(this); // Les secteurs qu'il voit
			
			// Determination de la probabilite de mouvement
			if(here.getNumberHero() > 1) { // Si un heros est deja la, on part
				moveProb = 0.8;
			}
			if(here.getNumberVilain() > 0) { // Si un vilain est la, on reste surement
				if(here.type==SectorType.HeroHQ) {
					// S'il y a un au moins un vilain au qg, la proba de bouger est : (nbHerosQG - 1) / nbTotalHerosMap
					moveProb = (double)(((double)here.getNumberHero() - (double)1) / (double)Humanoid.city.getTotalHeroes());
				} else {
					moveProb = 0.3;
					
					if(Humanoid.rand.nextDouble() < 0.2) { // Est-ce qu'il veut se fight
						here.newFight(); // Lancement du combat
						moveProb = 0.0; // Si ya fight, il ne bougera pas a ce tour
					}
				}
			} else { // Il regarde si un braquage est en cours et qu'il peut y aller
				
				// Pour chaque evenement, on regarde si braquage et dans la voisinage
				boolean found = false;
				Event e = null;
				for(int i = 0; i < super.getEvents().size() && !found; i++) {
					e = super.getEvent(i);
					if(voisinage.contains(e) && e.getType() == EventType.Robery) { // Alors il doit y aller
						found = true;
						toGo = e.getSector();
					}
				}
				
				if(found == true && toGo != null && e != null) { // Si on l'a trouve, i y va forcement
					Console.println(Humanoid.city.getDate() + " " + this.toString() + " part dejouer le braquage.");
					this.setLocation(toGo.getLocation().getLocationX(), toGo.getLocation().getLocationY());
					e.addEntity(this); // Il participe maintenant a l'evenement
					moveProb = 0.0; // Il ne bougera pas parce qu'il est alle a la banque
				}
			}
			
			// On regarde si on va bouger
			if(Humanoid.rand.nextDouble() < moveProb) { // Je bouge
				// Un heros ne peut aller dans un qg de vilain ou chez les habitants
				int maxLook = 3; // Limite de recherche de secteur
				while( (toGo == null || toGo.getType()==SectorType.VilainHQ || toGo.getType()==SectorType.HeadQuarter) && (maxLook > 0)) {
					toGo = voisinage.get(Humanoid.rand.nextInt(voisinage.size()));
					maxLook -= 1;
				}
				
				// On bouge, sauf si on voulait aller la ou on a pas le droit
				if(toGo != null && maxLook > 0) { // Si maxLook est pas positif on peut estimer qu'il a mal choisi
					this.setLocation(toGo.getLocation().getLocationX(), toGo.getLocation().getLocationY());
				}
			}
		}
	}

}
