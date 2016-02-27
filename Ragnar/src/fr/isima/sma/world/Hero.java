package fr.isima.sma.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import fr.isima.sma.world.Sector.SectorType;

public class Hero extends Super {

	public Hero(String name, String surname, int age, int speed, int ligne, int colonne) {
		super(AgentType.HERO, name, surname, age, speed, ligne, colonne);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#moveRandom()
	 */
	@Override
	public void moveRandom() {
		// TODO Auto-generated method stub
		super.moveRandom();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#move(int, int)
	 */
	@Override
	public void move(int ligne, int colonne) {
		// TODO Auto-generated method stub
		super.move(ligne, colonne);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		super.setName(name);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#getSurname()
	 */
	@Override
	public String getSurname() {
		// TODO Auto-generated method stub
		return super.getSurname();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#setSurname(java.lang.String)
	 */
	@Override
	public void setSurname(String surname) {
		// TODO Auto-generated method stub
		super.setSurname(surname);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#getAge()
	 */
	@Override
	public int getAge() {
		// TODO Auto-generated method stub
		return super.getAge();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#setAge(int)
	 */
	@Override
	public void setAge(int age) {
		// TODO Auto-generated method stub
		super.setAge(age);
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#getSpeed()
	 */
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed();
	}

	/* (non-Javadoc)
	 * @see fr.isima.sma.world.Super#setSpeed(int)
	 */
	@Override
	public void setSpeed(int speed) {
		// TODO Auto-generated method stub
		super.setSpeed(speed);
	}

	@Override
	public void live() {
		Sector toGo = null; // Le secteur ou il ira
		Sector here = city.getSector(this); // Secteur actuel
		double moveProb = 1.0; // Probabilite de deplacement
		List<Sector> voisinage = city.getNeighborhood(this); // Les secteurs qu'il voit
		
		// Determination de la probabilite de mouvement
		if(here.getNumberHero() > 1) { // Si un heros est deja la, on part surement
			moveProb = 0.8;
		}
		if(here.getNumberVilain() > 0) { // Si un vilain est la, on reste surement
			if(here.type==SectorType.HeroHQ) {
				moveProb = 0.1;
				// TODO mettre la vraie proba, il faut le compte total de heros
				//moveProb = 1 - (here.getNumberHero() - 1)/city.getActiveEntities().
			} else {
				moveProb = 0.3;
				
				if(Math.abs(Humanoid.rand.nextGaussian()) < 0.5) { // Est-ce qu'il veut se fight
					// TODO event figth
					moveProb = 0.0; // Si ya fight, il ne bougera pas
				}
			}
		}
		// TODO s'il est pris dans un fight, il ne bougera pas
		
		// On regarde si on va bouger
		if(Math.abs(Humanoid.rand.nextGaussian()) < moveProb) { // Je bouge
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
