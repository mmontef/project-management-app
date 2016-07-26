package domain;

import java.util.ArrayList;
import java.util.List;

import domain.IObserver;

public abstract class Subject {
	public Subject() {
		//observers = new ArrayList<IObserver>();
	}
	
	public static void addObserver (IObserver o) {
		observers.add(o);
	}
	
	public static void removeObserver (IObserver o) {
		observers.remove(o);
	}
	
	protected static void notifyObservers() {
		for (IObserver o : observers) {
			o.update();
		}
	}
	
	private static List<IObserver> observers = new ArrayList<IObserver>();
}
