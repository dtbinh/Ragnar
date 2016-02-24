package fr.isima.sma.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JListBinding;
import org.jdesktop.swingbinding.SwingBindings;

import fr.isima.sma.resources.Properties;
import fr.isima.sma.resources.ResourcesManager;
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
import javax.swing.ImageIcon;

import java.awt.Panel;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Graphics;

import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ObjectProperty;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.ComponentOrientation;
import java.awt.Cursor;


public class AgentsView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ResourcesManager res = ResourcesManager.getInstance();
	private JPanel contentPane;
	public JList<Humanoid> agentsList;
	private City modele;
	private JLabel lblDate;
	private JPanel panelAgent;
	private Panel detailsPanel;
	private JLabel imageLabel;
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
	private JPanel panelScrollableAgentList;
	private JScrollPane scrollPane;
	private JPanel layoutBorderDetailPanel;
	private JPanel panelDateTime;

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
		
		panelDateTime = new JPanel();
		contentPane.add(panelDateTime, BorderLayout.SOUTH);

		lblDate = new JLabel("Placeholder pour la date");
		panelDateTime.add(lblDate);
		
		panelAgent = new JPanel();
		panelAgent.setPreferredSize(new Dimension(500, 128));
		panelAgent.setMinimumSize(new Dimension(500, 128));
		contentPane.add(panelAgent, BorderLayout.NORTH);
		panelAgent.setLayout(new BoxLayout(panelAgent, BoxLayout.X_AXIS));
		
		imageLabel = new JLabel();
		imageLabel.setPreferredSize(new Dimension(128, 128));
		imageLabel.setMaximumSize(new Dimension(128, 128));
		imageLabel.setMinimumSize(new Dimension(128, 128));
		panelAgent.add(imageLabel);
		
		layoutBorderDetailPanel = new JPanel();
		layoutBorderDetailPanel.setBackground(Color.DARK_GRAY);
		layoutBorderDetailPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
		panelAgent.add(layoutBorderDetailPanel);
		layoutBorderDetailPanel.setLayout(new BorderLayout(0, 0));
		
		detailsPanel = new Panel();
		layoutBorderDetailPanel.add(detailsPanel);
		detailsPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		detailsPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		detailsPanel.setBounds(new Rectangle(0, 0, 5, 5));
		detailsPanel.setFont(new Font("Dialog", Font.PLAIN, 12));
		detailsPanel.setPreferredSize(new Dimension(450, 10));
		detailsPanel.setForeground(Color.WHITE);
		detailsPanel.setBackground(Color.DARK_GRAY);
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
		
		panelScrollableAgentList = new JPanel();
		contentPane.add(panelScrollableAgentList, BorderLayout.CENTER);
		panelScrollableAgentList.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panelScrollableAgentList.add(scrollPane, BorderLayout.CENTER);
		
				agentsList = new JList<Humanoid>();
				scrollPane.setViewportView(agentsList);
				agentsList.setVisibleRowCount(32);
				agentsList.setAutoscrolls(true);
				agentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
						agentsList.addListSelectionListener(new ListSelectionListener() {
				
				            @Override
				            public void valueChanged(ListSelectionEvent arg0) {
				                if (!arg0.getValueIsAdjusting()) {
				                	Humanoid selected = modele.getActiveEntities().getAgents().get(agentsList.getSelectedIndex());
				                    lblnom.setText(selected.getName());
				                    lblprenom.setText(selected.getSurname());
				                    lblage.setText(String.valueOf(selected.getAge()));
				                    lblvitesse.setText(String.valueOf(selected.getSpeed()));
				                    lbltype.setText(selected.getClass().getSimpleName());
				                    lblposition.setText(String.valueOf(selected.getLocation()));
				                    String imageName = "";
				                    if(selected.getUrl().isEmpty() || selected.getUrl() == null)
				                    	imageName = selected.getClass().getSimpleName().toLowerCase();
				                    else
				                    	imageName = selected.getUrl();
				                    imageLabel.setIcon(new ImageIcon(res.getImage(imageName)));
				                }
				            }
				        });
		initDataBindings();
		//agentsList.setSelectedIndex(0);
	}

	protected void paintComponent(Graphics g) {
		for(Component c : this.getComponents()) {
			c.repaint();
		}
	}
	protected void initDataBindings() {
		BeanProperty<City, List<Humanoid>> cityBeanProperty = BeanProperty.create("activeEntities.agents");
		JListBinding<Humanoid, City, JList> jListBinding = SwingBindings.createJListBinding(UpdateStrategy.READ, modele, cityBeanProperty, agentsList, "agentsListBinding");
		jListBinding.bind();
		//
		ELProperty<City, Object> cityEvalutionProperty = ELProperty.create("Jour ${jour} - ${heure}:00");
		BeanProperty<JLabel, String> jLabelBeanProperty = BeanProperty.create("text");
		AutoBinding<City, Object, JLabel, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ, modele, cityEvalutionProperty, lblDate, jLabelBeanProperty, "DateTimeBinding");
		autoBinding_1.bind();
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