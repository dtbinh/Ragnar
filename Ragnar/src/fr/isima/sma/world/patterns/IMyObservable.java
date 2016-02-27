package fr.isima.sma.world.patterns;

import java.util.Observer;

/*
 *	Interface pour simuler l'héritage de la classe Observer
 */
public interface IMyObservable {
	public void notifyObservers();
	public void notifyObservers(Object o);
	public void addObserver(Observer o);
	public int countObservers();
}
