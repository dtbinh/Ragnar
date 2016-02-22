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


public class AgentsView extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JList<Humanoid> agentsList;
	private City modele;
	private JLabel lblBleu;

	/**
	 * Create the frame.
	 */
	public AgentsView(City pModele) {
		modele = pModele;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setAlwaysOnTop(true);
		setTitle("Liste des agents");

		agentsList = new JList<Humanoid>();
		agentsList.setVisibleRowCount(32);
		contentPane.add(agentsList, BorderLayout.CENTER);

		lblBleu = new JLabel("Bleu");
		contentPane.add(lblBleu, BorderLayout.NORTH);
		initDataBindings();
	}
	
	protected void initDataBindings() {
		
		//	HEURE
		BeanProperty<City, Integer> cityBeanProperty_1 = BeanProperty.create("heure");
		BeanProperty<JLabel, String> jLabelBeanProperty = BeanProperty.create("text");
		AutoBinding<City, Integer, JLabel, String> autoBinding = org.jdesktop.beansbinding.Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, modele, cityBeanProperty_1, lblBleu, jLabelBeanProperty);
		autoBinding.bind();
		
		//	LISTE DES AGENTS
		BeanProperty<AgentsList<Humanoid>, List<Humanoid>> agentsBeanProperty = BeanProperty.create("agents");
		@SuppressWarnings("rawtypes")
		JListBinding<Humanoid, AgentsList<Humanoid>, JList> jListBinding = SwingBindings.createJListBinding(AutoBinding.UpdateStrategy.READ, this.modele.getActiveEntities(), agentsBeanProperty, agentsList);
		ELProperty<Humanoid, Object> agentEvalutionProperty = ELProperty.create("${name} ${surname} ${age}");
		jListBinding.setDetailBinding(agentEvalutionProperty);
		jListBinding.bind();
	}
}
