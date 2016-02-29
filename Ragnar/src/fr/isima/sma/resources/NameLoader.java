package fr.isima.sma.resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.random.MersenneTwister;

public class NameLoader {
	static private NameLoader instance;
	static private Object objet = new Object();
	private List<String> names;
	private String namesFileName;
	private MersenneTwister rand;

	private NameLoader() {
		try {
			rand = new MersenneTwister(Integer.valueOf(Properties.getInstance().getProperty("rand")));
			this.setNamesFileName(Properties.getInstance().getProperty("namesFile"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static public NameLoader getInstance() {
		if(null == instance) {
			synchronized (objet) {
				if(null == instance) {
					instance = new NameLoader();
				}
			}
		}
		return instance;
	}

	private boolean loadNames() throws IOException {
		// ajout des proprietes par defaut
		names = new ArrayList<String>(20000);

		try (
				FileReader file = new FileReader(this.getNamesFileName());
				BufferedReader data = new BufferedReader(file);
			)
		{
			// lecture du fichier

			String line = data.readLine();

			while(line != null && !line.isEmpty()) {
				line = line.replaceFirst(".",(line.charAt(0)+"").toUpperCase());
				names.add(line);
				line = data.readLine();
			}

			// fin de lecture

		} catch (IOException e) {
			System.err.println("Le fichier n'a pas pu être ouvert.");
			throw e;
		}
		return true;
	}

	public String getNamesFileName() {
		return namesFileName;
	}

	public String getName() {
		String retour = "default";
		if(names.size()>0) {
			retour = names.get(rand.nextInt(names.size()));
		}
		return retour;
	}

	public void setNamesFileName(String filenname) throws IOException {
		this.namesFileName = filenname;
		this.loadNames();
	}
}
