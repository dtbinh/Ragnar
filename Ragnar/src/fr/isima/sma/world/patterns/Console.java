package fr.isima.sma.world.patterns;

import java.util.ArrayList;
import java.util.List;

public class Console extends AbstractModelObject {
	static private Console instance;
	static private Object objet = new Object();
	protected List<String> console;
	protected String name;

	private Console() {
		this("console");
	}
	
	static public Console getInstance() {
		if(null == instance) {
			synchronized (objet) {
				if(null == instance) {
					instance = new Console();
				}
			}
		}
		return instance;
	}
	
	private Console(String pName) {
		console = new ArrayList<>();
		name = pName;
	}

	static public void add(String elt) {
		if(getInstance()!=null) {
			synchronized (objet) {
				List<String> oldValue = instance.console;
				instance.console = new ArrayList<String>(instance.console);
				instance.console.add(0, elt);
				instance.firePropertyChange("console", oldValue, instance.console);
				instance.firePropertyChange("consoleCount", oldValue.size(), instance.console.size());							
			}
		}
	}

	static public void remove(String elt) {
		if(getInstance()!=null) {
			synchronized (objet) {
				List<String> oldValue = instance.console;
				instance.console = new ArrayList<String>(instance.console);
				instance.console.remove(elt);
				instance.firePropertyChange("console", oldValue, instance.console);
				instance.firePropertyChange("consoleCount", oldValue.size(), instance.console.size());
			}
		}
	}

	public List<String> getConsole() {
		return getInstance().console;
	}
	
	public String get(int i) {
		return getInstance().console.get(i);
	}

	public int size() {
		return getInstance().console.size();
	}
	
	static public void println(String s) {
		add(s);
	}
	
}
