package fr.isima.sma.gui;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import fr.isima.sma.resources.Properties;
import fr.isima.sma.resources.ResourcesManager;
import fr.isima.sma.world.City;
import fr.isima.sma.world.Hero;
import fr.isima.sma.world.Sector;
import fr.isima.sma.world.Sector.SectorType;

import javax.swing.JLabel;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SectorView extends JPanel {
	private AutoBinding<Sector, Integer, JLabel, Boolean> groupVisibleBind;
	private AutoBinding<Sector, Integer, JLabel, Boolean> citizenVisibleBind;
	private AutoBinding<Sector, Integer, JLabel, Boolean> vilainVisibleBind;

	private static final long serialVersionUID = 1L;
	private AutoBinding<Sector, Integer, JLabel, Boolean> heroVisibleBind;
	private AutoBinding<Sector, SectorType, JPanel, Color> sectorStyleBind;

	private City modele;
	private Properties proprietes;
	private ResourcesManager assets;
	private JLabel heroLabel;
	private JLabel citizenLabel;
	private JLabel vilainLabel;
	private JLabel groupLabel;

	private int indiceX;
	private int indiceY;
	private AutoBinding<Sector, Integer, JLabel, Boolean> heroNumberBind;
	private AutoBinding<Sector, Integer, JLabel, Boolean> vilainNumberBind;
	private AutoBinding<Sector, Integer, JLabel, Boolean> citizenNumberBind;
	private AutoBinding<Sector, Integer, JLabel, Boolean> groupNumberBind;


	/**
	 * Create the panel.
	 */
	public SectorView(City pModele, int i, int j) {
		modele = pModele;
		proprietes = Properties.getInstance();
		assets = ResourcesManager.getInstance();

		indiceX = i;
		indiceY = j;

		this.setPreferredSize(new Dimension(Integer.valueOf(proprietes.getProperty("caseSize")), Integer.valueOf(proprietes.getProperty("caseSize"))));
		setLayout(new GridLayout(0, 2, 0, 0));

		heroLabel = new JLabel(assets.getIcon("hero"));
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				modele.getMap()[indiceX][indiceY].setNumberHero(1);
				System.out.println(modele.getMap()[indiceX][indiceY].getNumberHero());
			}
		});
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				modele.addActiveEntity(new Hero("Bob", "Cho", 26, 10, indiceX, indiceY));
				//modele.getMap()[indiceX][indiceY].setNumberHero(1);
				System.out.println(modele.getMap()[indiceX][indiceY].getNumberHero() + " " + modele.getActiveEntities().size());
			}
		});
		add(heroLabel);

		citizenLabel = new JLabel(assets.getIcon("citizen"));
		add(citizenLabel);

		vilainLabel = new JLabel(assets.getIcon("vilain"));
		add(vilainLabel);

		groupLabel = new JLabel(assets.getIcon("group"));
		add(groupLabel);
		initDataBindings();
	}
	protected void initDataBindings() {
		BeanProperty<Sector, SectorType> sectorBeanProperty = BeanProperty.create("type");
		BeanProperty<JPanel, Color> jPanelBeanProperty = BeanProperty.create("background");
		sectorStyleBind = Bindings.createAutoBinding(UpdateStrategy.READ, modele.getMap()[indiceX][indiceY], sectorBeanProperty, this, jPanelBeanProperty, "sectorStyleBind");
		sectorStyleBind.setConverter(new SectorConverter());
		sectorStyleBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_1 = BeanProperty.create("numberHero");
		BeanProperty<JLabel, Boolean> jLabelBeanProperty_1 = BeanProperty.create("visible");
		heroVisibleBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, modele.getMap()[indiceX][indiceY], sectorBeanProperty_1, heroLabel, jLabelBeanProperty_1, "heroVisibleBind");
		heroVisibleBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_2 = BeanProperty.create("numberVilain");
		BeanProperty<JLabel, Boolean> jLabelBeanProperty_2 = BeanProperty.create("visible");
		vilainVisibleBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, modele.getMap()[indiceX][indiceY], sectorBeanProperty_2, vilainLabel, jLabelBeanProperty_2, "vilainVisibleBind");
		vilainVisibleBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_3 = BeanProperty.create("numberCitizen");
		BeanProperty<JLabel, Boolean> jLabelBeanProperty_3 = BeanProperty.create("visible");
		citizenVisibleBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, modele.getMap()[indiceX][indiceY], sectorBeanProperty_3, citizenLabel, jLabelBeanProperty_3, "citizenVisibleBind");
		citizenVisibleBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_4 = BeanProperty.create("numberGroup");
		BeanProperty<JLabel, Boolean> jLabelBeanProperty_4 = BeanProperty.create("visible");
		groupVisibleBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, modele.getMap()[indiceX][indiceY], sectorBeanProperty_4, groupLabel, jLabelBeanProperty_4, "groupVisibleBind");
		groupVisibleBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_1A = BeanProperty.create("numberHero");
		BeanProperty<JLabel, Boolean> jLabelBeanProperty_1A = BeanProperty.create("text");
		heroNumberBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, modele.getMap()[indiceX][indiceY], sectorBeanProperty_1A, heroLabel, jLabelBeanProperty_1A, "heroNumberBind");
		heroNumberBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_2A = BeanProperty.create("numberVilain");
		BeanProperty<JLabel, Boolean> jLabelBeanProperty_2A = BeanProperty.create("text");
		vilainNumberBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, modele.getMap()[indiceX][indiceY], sectorBeanProperty_2A, vilainLabel, jLabelBeanProperty_2A, "vilainNumberBind");
		vilainNumberBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_3A = BeanProperty.create("numberCitizen");
		BeanProperty<JLabel, Boolean> jLabelBeanProperty_3A = BeanProperty.create("text");
		citizenNumberBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, modele.getMap()[indiceX][indiceY], sectorBeanProperty_3A, citizenLabel, jLabelBeanProperty_3A, "citizenNumberBind");
		citizenNumberBind.bind();
		//
		BeanProperty<Sector, Integer> sectorBeanProperty_4A = BeanProperty.create("numberGroup");
		BeanProperty<JLabel, Boolean> jLabelBeanProperty_4A = BeanProperty.create("text");
		groupNumberBind = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, modele.getMap()[indiceX][indiceY], sectorBeanProperty_4A, groupLabel, jLabelBeanProperty_4A, "groupNumberBind");
		groupNumberBind.bind();
	}
}
