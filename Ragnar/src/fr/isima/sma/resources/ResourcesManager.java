package fr.isima.sma.resources;

import java.util.HashMap;

public class ResourcesManager {
	static private ResourcesManager instance;
	static private Object objet;
	
	private ResourcesManager() {
		
	}
	
	static public ResourcesManager getInstance() {
		if(null == instance) {
			synchronized (objet) {
				if(null == instance) {
					instance = new ResourcesManager();
				}
			}
		}
		return instance;
	}
}
