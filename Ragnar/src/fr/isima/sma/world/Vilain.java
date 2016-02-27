package fr.isima.sma.world;

import java.util.List;

import fr.isima.sma.world.Sector.SectorType;
import fr.isima.sma.world.patterns.Console;

public class Vilain extends Super {
	protected double robberyPrep;
	protected boolean robbing; // Est-ce qu'il est en train de voler ?

	public Vilain(String name, String surname, int age, int speed, int ligne, int colonne) {
		super(AgentType.VILAIN, name, surname, age, speed, ligne, colonne);
		
		// On l'initialise entre 20 et 80 pour pas avoir tous les braquages en meme temps apres
		robberyPrep = ((double)Humanoid.rand.nextInt(60)) + 20.0;
		robbing = false;
	}

	@Override
	public void live() {
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
				
			if(Math.abs(Humanoid.rand.nextGaussian()) < 0.5) { // Est-ce qu'il veut se fight
				// TODO event figth
				moveProb = 0.0; // Si ya fight, il ne bougera pas
			}
		}
		if(robbing) { // Truc a l'arrache pour tester
			if(Math.abs(Humanoid.rand.nextGaussian()) < 1.0) {
				moveProb = 0.0;
				System.out.println(this.name + " " + this.surname + " ne bouge plus");
			} else {
				robbing = false;
				robberyPrep = 0.0;
				moveProb = 1.0;
			}
		} // if fragile
		if(robberyPrep > 100.0 && !robbing) { // Il est prêt, et n'est pas en train de voler
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
			System.out.println(this.name + " " + this.surname + " cherche une banque");
			if(found) { // Si on l'a trouvee, on y va
				this.setLocation(toGo.getLocation().getLocationX(), toGo.getLocation().getLocationY());
				this.robbing = true;
				moveProb = 0.0; // Il ne bougera pas parce qu'il l'a trouvee
				Console.println(this.name + " " + this.surname + " tente un braquage!");
				// TODO lancement de l'event de braquage
			}
		}
		// TODO si event fight ou braquage, on bouge pas
		
		// Augmentation de la preparation
		if(!robbing && here.type!=SectorType.VilainHQ) {
			double prep = Math.abs(Humanoid.rand.nextGaussian());
			robberyPrep = robberyPrep + (prep * here.getNumberVilain()) - (prep * here.getNumberHero());
			
			if(robberyPrep < -100.0) {
				// Si le mec est au bout, on le debloque quand meme
				robberyPrep = 0.0;
			}
		}
		
		// On regarde si on va bouger
		if(Math.abs(Humanoid.rand.nextGaussian()) < moveProb) { // Je bouge
			// Un heros ne peut aller dans un qg de vilain ou chez les habitants
			int maxLook = 3; // Limite de recherche de secteur
			while( (toGo == null || toGo.getType()==SectorType.HeadQuarter) && (maxLook > 0)) {
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
