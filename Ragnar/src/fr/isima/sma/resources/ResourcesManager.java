package fr.isima.sma.resources;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ResourcesManager {
	static private ResourcesManager instance;
	static private Object objet = new Object();
	private HashMap<String, BufferedImage> images;
	private HashMap<String, ImageIcon> icons;
	private Properties props;

	private ResourcesManager() {
		props = Properties.getInstance();
		images = new HashMap<>();
		icons = new HashMap<>();
		images.put("default", new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB));
		icons.put("default", new ImageIcon(images.get("default")));
		loadResources();
	}

	private void loadResources() {
		String filename = props.getProperty("resources");
		if(filename != null) {
			try (
				FileReader file = new FileReader(filename);
				BufferedReader data = new BufferedReader(file);
			)
			{
				// lecture du fichier .properties

				String line = data.readLine();
				String asset[];
				int tailleIcon = Integer.valueOf(props.getProperty("caseSize")) / 4;
				while(line != null && !line.isEmpty()) {
					asset = line.split("=");
					if(asset.length == 2) {
						try {
							images.put(asset[0], ImageIO.read(new File(asset[1])));
							icons.put(asset[0], new ImageIcon(getImage(asset[0]).getScaledInstance(tailleIcon, tailleIcon, Image.SCALE_SMOOTH)));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					line = data.readLine();
				}

				// fin de lecture

			} catch (IOException e) {
				System.err.println("Le fichier n'a pas pu être ouvert.");
				e.printStackTrace();
			}

		}
	}

	public BufferedImage getImage(String name) {
		return images.get(name);
	}

	public ImageIcon getIcon(String name) {
		return icons.get(name);
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
