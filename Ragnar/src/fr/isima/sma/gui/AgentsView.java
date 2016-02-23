package fr.isima.sma.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JListBinding;
import org.jdesktop.swingbinding.SwingBindings;

import fr.isima.sma.world.*;

import javax.swing.JList;
import org.jdesktop.beansbinding.BeanProperty;
import java.util.List;
import javax.swing.JLabel;
import org.jdesktop.beansbinding.AutoBinding;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JScrollBar;
import javax.swing.BoxLayout;
import java.awt.Panel;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.GridLayout;
import java.awt.Font;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ObjectProperty;
import javax.swing.JScrollPane;


public class AgentsView extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JList<Humanoid> agentsList;
	private City modele;
	private JLabel lblDate;
	private JPanel panel;
	private Panel detailsPanel;
	private Panel imagePanel;
	private JLabel lblNom;
	private JLabel lblPrenom;
	private JLabel lblprenom;
	private JLabel lblnom;
	private JLabel lblType;
	private JLabel lbltype;
	private JLabel lblAge;
	private JLabel lblage;
	private JLabel lblPostion;
	private JLabel lblposition;
	private JLabel lblVitesse;
	private JLabel lblvitesse;

	/**
	 * Create the frame.
	 */
	public AgentsView(City pModele) {
		setMinimumSize(new Dimension(450, 500));
		setSize(new Dimension(450, 569));
		modele = pModele;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setAlwaysOnTop(true);
		setTitle("Liste des agents");

		agentsList = new JList<Humanoid>();
		agentsList.setVisibleRowCount(32);
		contentPane.add(agentsList, BorderLayout.CENTER);
		agentsList.setAutoscrolls(true);
		agentsList.setSelectedIndex(0);

		lblDate = new JLabel("Placeholder pour la date");
		contentPane.add(lblDate, BorderLayout.SOUTH);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(500, 128));
		panel.setMinimumSize(new Dimension(500, 128));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		imagePanel = new Panel();
		imagePanel.setPreferredSize(new Dimension(128, 128));
		imagePanel.setMaximumSize(new Dimension(128, 128));
		imagePanel.setMinimumSize(new Dimension(128, 128));
		panel.add(imagePanel);
		
		detailsPanel = new Panel();
		detailsPanel.setFont(new Font("Dialog", Font.PLAIN, 12));
		detailsPanel.setPreferredSize(new Dimension(450, 10));
		detailsPanel.setForeground(Color.WHITE);
		detailsPanel.setBackground(Color.DARK_GRAY);
		panel.add(detailsPanel);
		detailsPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNom = new JLabel("Nom :");
		lblNom.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNom.setForeground(Color.WHITE);
		lblNom.setDoubleBuffered(true);
		detailsPanel.add(lblNom);
		
		lblnom = new JLabel("#nom");
		lblnom.setForeground(Color.YELLOW);
		lblnom.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		detailsPanel.add(lblnom);
		
		lblPrenom = new JLabel("Pr\u00E9nom :");
		lblPrenom.setForeground(Color.WHITE);
		lblPrenom.setFont(new Font("Tahoma", Font.BOLD, 12));
		detailsPanel.add(lblPrenom);
		lblPrenom.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPrenom.setLabelFor(lblprenom);
		
		lblprenom = new JLabel("#prenom");
		lblprenom.setForeground(Color.YELLOW);
		lblprenom.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		detailsPanel.add(lblprenom);
		
		lblType = new JLabel("Type :");
		lblType.setForeground(Color.WHITE);
		lblType.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblType.setAlignmentX(0.5f);
		detailsPanel.add(lblType);
		
		lbltype = new JLabel("#type");
		lbltype.setForeground(Color.YELLOW);
		lbltype.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		detailsPanel.add(lbltype);
		
		lblAge = new JLabel("Age :");
		lblAge.setForeground(Color.WHITE);
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAge.setAlignmentX(0.5f);
		detailsPanel.add(lblAge);
		
		lblage = new JLabel("#age");
		lblage.setForeground(Color.YELLOW);
		lblage.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		detailsPanel.add(lblage);
		
		lblVitesse = new JLabel("Vitesse :");
		lblVitesse.setForeground(Color.WHITE);
		lblVitesse.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblVitesse.setAlignmentX(0.5f);
		detailsPanel.add(lblVitesse);
		
		lblvitesse = new JLabel("#vitesse");
		lblvitesse.setForeground(Color.YELLOW);
		lblvitesse.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		detailsPanel.add(lblvitesse);
		
		lblPostion = new JLabel("Postion :");
		lblPostion.setForeground(Color.WHITE);
		lblPostion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPostion.setAlignmentX(0.5f);
		detailsPanel.add(lblPostion);
		
		lblposition = new JLabel("#position");
		lblposition.setForeground(Color.YELLOW);
		lblposition.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		detailsPanel.add(lblposition);
		initDataBindings();
		agentsList.setSelectedIndex(0);
	}
	protected void initDataBindings() {
		BeanProperty<City, Integer> cityBeanProperty_1 = BeanProperty.create("heure");
		BeanProperty<JLabel, String> jLabelBeanProperty = BeanProperty.create("text");
		AutoBinding<City, Integer, JLabel, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, modele, cityBeanProperty_1, lblDate, jLabelBeanProperty);
		autoBinding.bind();
		//
		BeanProperty<City, List<Humanoid>> cityBeanProperty = BeanProperty.create("activeEntities.agents");
		JListBinding<Humanoid, City, JList> jListBinding = SwingBindings.createJListBinding(UpdateStrategy.READ, modele, cityBeanProperty, agentsList, "agentsListBinding");
		//
		ELProperty<Humanoid, Object> humanoidEvalutionProperty = ELProperty.create("${name} ${surname} ${class.name}");
		jListBinding.setDetailBinding(humanoidEvalutionProperty, "agentsListDetailsBinding");
		//
		jListBinding.bind();
		//
		BeanProperty<JList, String> jListBeanProperty = BeanProperty.create("selectedElement.surname");
		ObjectProperty<JLabel> jLabelObjectProperty = ObjectProperty.create();
		AutoBinding<JList, String, JLabel, JLabel> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ, agentsList, jListBeanProperty, lblnom, jLabelObjectProperty);
		autoBinding_1.bind();
		//
		BeanProperty<JList<Humanoid>, Object> jListBeanProperty_1 = BeanProperty.create("selectedElement");
		AutoBinding<JList<Humanoid>, Object, JLabel, JLabel> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ, agentsList, jListBeanProperty_1, lblnom, jLabelObjectProperty);
		autoBinding_2.bind();
	}
}
/*
//	LISTE DES AGENTS
BeanProperty<AgentsList<Humanoid>, List<Humanoid>> agentsBeanProperty = BeanProperty.create("agents");
@SuppressWarnings("rawtypes")
JListBinding<Humanoid, AgentsList<Humanoid>, JList> jListBinding = SwingBindings.createJListBinding(AutoBinding.UpdateStrategy.READ, this.modele.getActiveEntities(), agentsBeanProperty, agentsList);
ELProperty<Humanoid, Object> agentEvalutionProperty = ELProperty.create("${name} ${surname} ${age}");
jListBinding.setDetailBinding(agentEvalutionProperty);
jListBinding.bind();
*/
