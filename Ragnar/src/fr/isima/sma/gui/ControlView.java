package fr.isima.sma.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ObjectProperty;

import fr.isima.sma.world.City;
import fr.isima.sma.world.patterns.Console;
import org.jdesktop.swingbinding.JListBinding;
import org.jdesktop.swingbinding.SwingBindings;

public class ControlView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private City modele;
	private Console console = Console.getInstance();
	private JList list;

	/**
	 * Create the frame.
	 */
	public ControlView(City pModele) {
		setMinimumSize(new Dimension(450, 260));
		setPreferredSize(new Dimension(450, 250));
		modele = pModele;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 260);
		contentPane = new JPanel();
		contentPane.setSize(new Dimension(450, 250));
		contentPane.setPreferredSize(new Dimension(450, 250));
		contentPane.setMinimumSize(new Dimension(450, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setMinimumSize(new Dimension(450, 50));
		buttonPanel.setSize(new Dimension(450, 50));
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new GridLayout(0, 5, 0, 0));
		
		JButton btnPlay = new JButton("Play");
		buttonPanel.add(btnPlay);
		
		JButton btnPause = new JButton("Pause");
		buttonPanel.add(btnPause);
		
		JButton btnStop = new JButton("Stop");
		buttonPanel.add(btnStop);
		
		JButton btnExport = new JButton("Export");
		buttonPanel.add(btnExport);
		
		JButton btnRestart = new JButton("Restart");
		buttonPanel.add(btnRestart);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		list = new JList();
		scrollPane.setViewportView(list);
		setAlwaysOnTop(true);
		setTitle("Panneau de contr\u00F4le");
		initDataBindings();
	}
	protected void initDataBindings() {
		BeanProperty<Console, List<String>> consoleBeanProperty = BeanProperty.create("console");
		JListBinding<String, Console, JList> jListBinding = SwingBindings.createJListBinding(UpdateStrategy.READ, console, consoleBeanProperty, list);
		jListBinding.bind();
	}
}
