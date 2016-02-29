package fr.isima.sma.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JListBinding;
import org.jdesktop.swingbinding.SwingBindings;

import fr.isima.sma.world.City;
import fr.isima.sma.world.patterns.Console;
import fr.isima.sma.world.patterns.IMyObservable;
import fr.isima.sma.world.patterns.MyObservable;

/**
 * 	Represents graphically the control panel.
 */
public class ControlView extends JFrame implements IMyObservable {

	private static final long serialVersionUID = 1L;
	private MyObservable observable;
	private JPanel contentPane;
	private City modele;
	private Console console = Console.getInstance();
	private JList<String> list;
	private JLabel label;
	
	public enum ButtonPressed {
		PLAY, PAUSE, STOP, EXPORT, RESTART, GUI
	}

	/**
	 * Create the frame of the control panel.
	 * @param pModele : model of the MVC.
	 */
	public ControlView(City pModele) {
		observable = new MyObservable();
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
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				observable.setChanged();
				notifyObservers(ButtonPressed.PLAY);
			}
		});
		btnPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				observable.setChanged();
				notifyObservers(ButtonPressed.PAUSE);
			}
		});
		btnPause.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				observable.setChanged();
				notifyObservers(ButtonPressed.STOP);
			}
		});
		btnStop.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				observable.setChanged();
				notifyObservers(ButtonPressed.EXPORT);
			}
		});
		btnExport.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnRestart = new JButton("Restart");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				observable.setChanged();
				notifyObservers(ButtonPressed.RESTART);
			}
		});
		btnRestart.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnGui = new JButton("GUI");
		btnGui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				observable.setChanged();
				notifyObservers(ButtonPressed.GUI);
			}
		});
		btnGui.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.add(btnPlay);
		buttonPanel.add(btnPause);
		buttonPanel.add(btnStop);
		buttonPanel.add(btnExport);
		buttonPanel.add(btnRestart);
		buttonPanel.add(btnGui);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		list = new JList<String>();
		scrollPane.setViewportView(list);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		label = new JLabel("Placeholder pour la date");
		panel.add(label);
		setAlwaysOnTop(true);
		setTitle("Panneau de contr\u00F4le");
		initDataBindings();
	}

	@Override
	public void notifyObservers() {
		observable.notifyObservers();
	}
	@Override
	public void notifyObservers(Object o) {
		observable.notifyObservers(o);
	}
	@Override
	public void addObserver(Observer o) {
		observable.addObserver(o);
	}
	@Override
	public int countObservers() {
		return observable.countObservers();
	}
	
	/**
	 * Initialize the data binding.
	 */
	protected void initDataBindings() {
		BeanProperty<Console, List<String>> consoleBeanProperty = BeanProperty.create("console");
		@SuppressWarnings("rawtypes")
		JListBinding<String, Console, JList> jListBinding = SwingBindings.createJListBinding(UpdateStrategy.READ, console, consoleBeanProperty, list);
		jListBinding.bind();
		//
		ELProperty<City, Object> cityEvalutionProperty = ELProperty.create("Annee ${annee}, Jour ${jour} - ${heure}:00");
		BeanProperty<JLabel, String> jLabelBeanProperty = BeanProperty.create("text");
		AutoBinding<City, Object, JLabel, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ, modele, cityEvalutionProperty, label, jLabelBeanProperty);
		autoBinding.bind();
	}
}
