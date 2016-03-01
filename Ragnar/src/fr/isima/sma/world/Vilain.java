package fr.isima.sma.world;

import java.util.List;

import fr.isima.sma.world.Sector.SectorType;
import fr.isima.sma.world.patterns.Console;

public class Vilain extends Super {
	protected double robberyPrep;
	protected boolean captured; // S'il est capturé, il ne peux pas bouger

	/**
	 * Vilain constructor
	 * @param name The vilain's name
	 * @param surname The vilain's surname
	 * @param age The vilain's age
	 * @param speed The vilain's speed (in block per simulation ticks)
	 * @param ligne The vilain y axis coordinate on the map
	 * @param colonne The vilain x axis coordinate on the map
	 */
	public Vilain(String name, String surname, int age, int speed, int ligne, int colonne) {
		super(AgentType.VILAIN, name, surname, age, speed, ligne, colonne);
		
		// On l'initialise entre 20 et 80 pour pas avoir tous les braquages en meme temps apres
		robberyPrep = ((double)Humanoid.rand.nextInt(60)) + 20.0;
	}

	/**
	 * Make the villain living
	 */
	@Override
	public void live() {
		if( !captured ) {
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
				double moveProb = 1.0; // Probabilite de deplacement
				List<Sector> voisinage = city.getNeighborhood(this); // Les secteurs qu'il voit
				
				// Determination de la probabilite de mouvement
				if(here.getNumberVilain() > 1) { // Si un vilain est deja la, il va rester avec
					moveProb = 0.3;
				}
				if(here.getNumberHero() > 0) { // Si un heros est la, il veut s'en fuir
					moveProb = 0.8;
				}
				if(robberyPrep > 100.0) { // Il est prêt, et n'est pas en train de voler
					moveProb = 1.0; // Il cherche une banque, donc bouge tout le temps, sauf si on l'a trouvee
					
					// Recherche une banque
					boolean found = false;
					for(int i = 0; i < voisinage.size() && !found; i++) {
						Sector s = voisinage.get(i);
						if(s.type == SectorType.Bank) {
							found = true;
							toGo = s;
						}
					}
					
					if(found == true && toGo != null) { // Si on l'a trouvee, on y va
						this.setLocation(toGo.getLocation().getLocationX(), toGo.getLocation().getLocationY());
						moveProb = 0.0; // Il ne bougera pas parce qu'il l'a trouvee
						Console.println(Humanoid.city.getDate() + " " + this.toString() + " tente un braquage !");
						this.setWantRobery(true); // Lancement du braquage
					}
				} else { // S'il n'est pas pret il va au moins tenter de sauver un collegue
					// Recherche des heros enprisonnes dans le voisinage
					boolean found = false;
					for(int i = 0; i < voisinage.size() && !found; i++) {
						Sector s = voisinage.get(i);
						if(s.type == SectorType.HeroHQ && s.getNumberCaptured() > 0) {
							found = true;
							toGo = s;
						}
					}
					
					if(found == true && toGo != null) { // Si on l'a trouve
						// On teste s'il y va, proba de : 1 / (2 * nbHerosQg)/nbJours
						if( Humanoid.rand.nextDouble() < (double)((double)1 / (double)((double)16*( ((double)toGo.getNumberHero()>0.0)?(double)toGo.getNumberHero()+1:1.0)) )/daysPerYear ) {
							Console.println(Humanoid.city.getDate() + " " + this.toString() + " a libéré des vilains.");
							this.setLocation(toGo.getLocation().getLocationX(), toGo.getLocation().getLocationY());
							moveProb = 0.0; // Il ne bougera pas parce qu'il l'a trouvee
							toGo.freeAll(); // Liberation des vilains
						}
					}
				}
				
				// Augmentation de la preparation, si pas dans un HQ
				if(here.type!=SectorType.VilainHQ) {
					double prep = Humanoid.rand.nextDouble()*2;
					robberyPrep = robberyPrep + (((prep * here.getNumberVilain())*2 - (prep * here.getNumberHero()))/daysPerYear); // TODO ameliorer la preparation
					
					if(robberyPrep < -100.0) {
						// Si le mec est au bout, on le debloque quand meme
						robberyPrep = 0.0;
					}
				}
				
				// On regarde si on va bouger
				if(Humanoid.rand.nextDouble() < moveProb) { // Je bouge
					// Un vilain ne peut aller chez les Citoyens ou chez les Heros
					int maxLook = 3; // Limite de recherche de secteur autorise
					while( (toGo == null || toGo.getType()==SectorType.HeadQuarter || toGo.getType()==SectorType.HeroHQ) && (maxLook > 0)) {
						// Pas le droit d'aller chez les citoyens ou les heros de cette maniere
						toGo = voisinage.get(Humanoid.rand.nextInt(voisinage.size()));
						maxLook -= 1;
					}
					
					// On bouge, sauf si on voulait aller la ou on a pas le droit
					if(toGo != null && maxLook > 0) { // Si maxLook est pas positif on peut estimer qu'il a mal choisi
						this.setLocation(toGo.getLocation().getLocationX(), toGo.getLocation().getLocationY());
					}
				}
			}
		} else { // Il a quand meme une toute petite chance de se liberer tout seul
			if(Humanoid.rand.nextDouble() < ((0.00005)*Humanoid.city.getSector(this).getNumberVilain())/daysPerYear ) { // 0.005% de chances de se liberer
				Console.println(Humanoid.getCity().getDate() + " " + this.toString() + " s'est libéré tout seul.");
				this.setCaptured(false);
			}
		}
	}
	
	/**
	 * Reset the vilain robberyPrep to 0.0 
	 */
	public void resetRobberyPrep() {
		this.robberyPrep = 0.0;
	}

	/**
	 * @return True if the vilain is captured and can't move, False if he can move
	 */
	public boolean isCaptured() {
		return captured;
	}

	/**
	 * @param captured True to trap the vilain, False to release him
	 */
	public void setCaptured(boolean captured) {
		this.captured = captured;
	}

	/**
	 * @return the robberyPrep
	 */
	public double getRobberyPrep() {
		return robberyPrep;
	}

	/**
	 * @param robberyPrep the robberyPrep to set
	 */
	public void setRobberyPrep(double robberyPrep) {
		this.robberyPrep = robberyPrep;
	}

}
