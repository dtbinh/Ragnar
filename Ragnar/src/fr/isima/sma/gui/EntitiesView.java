package fr.isima.sma.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.isima.sma.world.ActiveEntity;
import fr.isima.sma.world.City;
import fr.isima.sma.world.Hero;
import fr.isima.sma.world.Humanoid;

import javax.swing.JSplitPane;
import javax.swing.ListModel;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import java.awt.Dimension;
import java.awt.Window.Type;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;
import org.jdesktop.beansbinding.BeanProperty;
import java.util.ArrayList;
import org.jdesktop.swingbinding.JListBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.ELProperty;
import javax.swing.JLabel;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ObjectProperty;
import java.util.List;

public class EntitiesView extends JFrame implements Observer {

	private JPanel contentPane;
	protected City modele;	/// modele
	protected List<Humanoid> entities;
	JList entitiesList;
	DefaultListModel<ActiveEntity> listModel;
	/**
	 * Create the frame.
	 */
	public EntitiesView(City pModele) {
		setAlwaysOnTop(true);
		modele = pModele;
		entities = modele.getActiveEntities();
		setType(Type.UTILITY);
		setResizable(false);
		setTitle("Ragnar : Suivi des agents");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setEnabled(false);
		splitPane.setLeftComponent(splitPane_1);
		
		JPanel imagePanel = new JPanel();
		imagePanel.setSize(128, 128);
		splitPane_1.setLeftComponent(imagePanel);
		
		Component rigidArea = Box.createRigidArea(new Dimension(128, 128));
		imagePanel.add(rigidArea);
		
		JPanel infoPanel = new JPanel();
		splitPane_1.setRightComponent(infoPanel);
		entitiesList = new JList<>();
		
		splitPane.setRightComponent(entitiesList);
		/*
		(new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(ActiveEntity ae : modele.getActiveEntities()) {
					if(!listModel.contains(ae))
						listModel.addElement(ae);
				}
			}
		})).start();*/
		initDataBindings();
	}

	@Override
	public void update(Observable o, Object arg) {
	/*	(new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(ActiveEntity ae : modele.getActiveEntities()) {
					if(!listModel.contains(ae))
						listModel.addElement(ae);
				}
			}
		})).start();*/
	}
	protected void initDataBindings() {
		/*JListBinding<Humanoid, ArrayList<Humanoid>, JList> jListBinding = SwingBindings.createJListBinding(UpdateStrategy.READ_WRITE, entities, entitiesList);
		//
		BeanProperty<Humanoid, String> humanoidBeanProperty = BeanProperty.create("name");
		jListBinding.setDetailBinding(humanoidBeanProperty);
		//
		jListBinding.bind();*/
	}
}
