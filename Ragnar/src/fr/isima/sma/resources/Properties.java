package fr.isima.sma.resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Properties {
	static private Properties instance;
	static private Object objet = new Object();
	private HashMap<String, String> properties;
	private String propertiesFileName;

	private Properties() {
		this.setPropertiesFileName("simulation.properties");
	}

	private void setDefaultProperties() {
		properties = new HashMap<>();
		properties.put("name", "Ragnar");
		properties.put("ticksPerHour", "1000");
		properties.put("daysperyear", "30");
		properties.put("caseSize", "64");
		properties.put("cityFile", "ragnar.txt");
		properties.put("agentsFile", "agents.txt");
		properties.put("gui", "true");
		properties.put("resources", "assets.rc");
	}

	static public Properties getInstance() {
		if(null == instance) {
			synchronized (objet) {
				if(null == instance) {
					instance = new Properties();
				}
			}
		}
		return instance;
	}

	private boolean loadProperties() {
		// ajout des proprietes par defaut
		setDefaultProperties();

		try (
				FileReader file = new FileReader(this.getPropertiesFileName());
				BufferedReader data = new BufferedReader(file);
			)
		{
			// lecture du fichier .properties

			String line = data.readLine();
			String property[];

			while(line != null && !line.isEmpty()) {
				property = line.split("=");
				if(property.length == 2)
					properties.put(property[0], property[1]);
				line = data.readLine();
			}

			// fin de lecture

		} catch (IOException e) {
			System.err.println("Le fichier n'a pas pu être ouvert.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getPropertiesFileName() {
		return propertiesFileName;
	}

	public String getProperty(String propertyName) {
		String retour = null;
		if(properties.containsKey(propertyName))
			retour = String.valueOf(properties.get(propertyName));
		else
			System.err.println("La propriété '" + propertyName + "' n'existe pas.");
		return retour;
	}

	public void setPropertiesFileName(String propertiesFileName) {
		this.propertiesFileName = propertiesFileName;
		this.loadProperties();
	}
}
