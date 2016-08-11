package domain;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import domain.IObserver;

@SuppressWarnings("serial")
public abstract class ListViewSubject extends JFrame{
	public ListViewSubject() {
		observers = new ArrayList<IObserver>();
	}
	
	public void addObserver (IObserver o) {
		observers.add(o);
	}
	
	public void removeObserver (IObserver o) {
		observers.remove(o);
	}
	
	protected void notifyObservers() {
		for (IObserver o : observers) {
			o.update();
		}
	}
	
	private List<IObserver> observers;
}
