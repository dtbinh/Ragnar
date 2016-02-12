package fr.isima.sma.world;

import java.util.Observable;
import java.util.Observer;

/*
 *	Interface pour simuler l'héritage de la classe Observer
 */
public interface IMyObservable {
	public void notifyObservers();
	public void addObserver(Observer o);
	public int countObservers();
}
