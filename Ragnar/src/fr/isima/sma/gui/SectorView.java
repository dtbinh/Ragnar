package fr.isima.sma.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import fr.isima.sma.resources.Properties;
import fr.isima.sma.resources.ResourcesManager;
import fr.isima.sma.world.City;
import fr.isima.sma.world.Sector;

/**
 * 	Represents graphically a sector.
 */
public class SectorView extends JPanel {
	private AutoBinding<Sector, Integer, JLabel, Boolean> groupVisibleBind;
	private AutoBinding<Sector, Integer, JLabel, Boolean> citizenVisibleBind;
	private AutoBinding<Sector, Integer, JLabel, Boolean> vilainVisibleBind;

	private static final long serialVersionUID = 1L;
	private AutoBinding<Sector, Integer, JLabel, Boolean> heroVisibleBind;

	private City modele;
	private Sector secModele;
	private Properties proprietes;
	private ResourcesManager assets;
	private JLabel heroLabel;
	private JLabel citizenLabel;
	private JLabel vilainLabel;
	private JLabel groupLabel;

	private int indiceX;
	private int indiceY;
	private AutoBinding<Sector, Integer, JLabel, String> heroNumberBind;
	private AutoBinding<Sector, Integer, JLabel, String> vilainNumberBind;
	private AutoBinding<Sector, Integer, JLabel, String> citizenNumberBind;
	private AutoBinding<Sector, Integer, JLabel, String> groupNumberBind;
	private Image image;


	/**
	 * Create the Sector representation.
	 * @param pModele : model of the MVC
	 * @param i : int - X position
	 * @param j : int - Y position
	 */
	public SectorView(City pModele, int i, int j) {
		modele = pModele;
		proprietes = Properties.getInstance();
		assets = ResourcesManager.getInstance();

		indiceX = i;
		indiceY = j;
		
		secModele = modele.getMap()[indiceX][indiceY];

		this.setPreferredSize(new Dimension(Integer.valueOf(proprietes.getProperty("caseSize")), Integer.valueOf(proprietes.getProperty("caseSize"))));
		
		image = assets.getImage(secModele.getType().toString().toLowerCase());
		image = image.getScaledInstance(Integer.valueOf(proprietes.getProperty("caseSize")), Integer.valueOf(proprietes.getProperty("caseSize")), Image.SCALE_SMOOTH);
		setLayout(new GridLayout(2, 1, 0, 0));
										
												heroLabel = new JLabel(assets.getIcon("hero"));
												add(heroLabel);
												
														citizenLabel = new JLabel(assets.getIcon("citizen"));
														add(citizenLabel);
														
																vilainLabel = new JLabel(assets.getIcon("vilain"));
																add(vilainLabel);
																
																		groupLabel = new JLabel(assets.getIcon("group"));
																		add(groupLabel);
		initDataBindings();
	}
	
	/**
	 * Initialize data binding.
	 */
	protected void initDataBindings() {
		BeanProperty<Sector, Integer> sectorBeanProperty_1 = BeanProperty.create("numberHero");
		BeanProperty<JLabel, Boolean> jLabelBeanProperty_1 = BeanProperty.create("visible");
		heroVisibleBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, secModele, sectorBeanProperty_1, heroLabel, jLabelBeanProperty_1, "heroVisibleBind");
		heroVisibleBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_2 = BeanProperty.create("numberVilain");
		BeanProperty<JLabel, Boolean> jLabelBeanProperty_2 = BeanProperty.create("visible");
		vilainVisibleBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, secModele, sectorBeanProperty_2, vilainLabel, jLabelBeanProperty_2, "vilainVisibleBind");
		vilainVisibleBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_3 = BeanProperty.create("numberCitizen");
		BeanProperty<JLabel, Boolean> jLabelBeanProperty_3 = BeanProperty.create("visible");
		citizenVisibleBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, secModele, sectorBeanProperty_3, citizenLabel, jLabelBeanProperty_3, "citizenVisibleBind");
		citizenVisibleBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_4 = BeanProperty.create("numberGroup");
		BeanProperty<JLabel, Boolean> jLabelBeanProperty_4 = BeanProperty.create("visible");
		groupVisibleBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, secModele, sectorBeanProperty_4, groupLabel, jLabelBeanProperty_4, "groupVisibleBind");
		groupVisibleBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_1A = BeanProperty.create("numberHero");
		BeanProperty<JLabel, String> jLabelBeanProperty_1A = BeanProperty.create("text");
		heroNumberBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, secModele, sectorBeanProperty_1A, heroLabel, jLabelBeanProperty_1A, "heroNumberBind");
		heroNumberBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_2A = BeanProperty.create("numberVilain");
		BeanProperty<JLabel, String> jLabelBeanProperty_2A = BeanProperty.create("text");
		vilainNumberBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, secModele, sectorBeanProperty_2A, vilainLabel, jLabelBeanProperty_2A, "vilainNumberBind");
		vilainNumberBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_3A = BeanProperty.create("numberCitizen");
		BeanProperty<JLabel, String> jLabelBeanProperty_3A = BeanProperty.create("text");
		citizenNumberBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, secModele, sectorBeanProperty_3A, citizenLabel, jLabelBeanProperty_3A, "citizenNumberBind");
		citizenNumberBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_4A = BeanProperty.create("numberGroup");
		BeanProperty<JLabel, String> jLabelBeanProperty_4A = BeanProperty.create("text");
		groupNumberBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, secModele, sectorBeanProperty_4A, groupLabel, jLabelBeanProperty_4A, "groupNumberBind");
		groupNumberBind.bind();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
}
