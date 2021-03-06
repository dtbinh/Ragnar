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
		try {
			this.setPropertiesFileName("settings/simulation.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setDefaultProperties() {
		properties = new HashMap<>();
		properties.put("windowsname", "Ragnar : Simulation Multi Agents");
		properties.put("name", "Ragnar");
		properties.put("ticksPerHour", "1");
		properties.put("msPerTick", "1000");
		properties.put("daysperyear", "30");
		properties.put("caseSize", "64");
		properties.put("cityFile", "settings/ragnar.txt");
		properties.put("agentsFile", "settings/agents.txt");
		properties.put("gui", "true");
		properties.put("resources", "settings/assets.rc");
		properties.put("namesFile", "settings/name.txt");
		properties.put("iterations", "1");
		properties.put("tempssimu", "1000000");
		properties.put("rand", "1103");
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

	private boolean loadProperties() throws IOException {
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
			System.err.println("Le fichier n'a pas pu �tre ouvert.");
			throw e;
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
			System.err.println("La propri�t� '" + propertyName + "' n'existe pas.");
		return retour;
	}

	public void setPropertiesFileName(String propertiesFileName) throws IOException {
		this.propertiesFileName = propertiesFileName;
		this.loadProperties();
	}
}
