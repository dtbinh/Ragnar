package fr.isima.sma.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.plaf.DimensionUIResource;

import fr.isima.sma.resources.Properties;

public class RagnarWindow extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Properties props = Properties.getInstance();

	public RagnarWindow() {
		super();
		getContentPane().setLayout(new BorderLayout());
		setTitle(props.getProperty("windowsname"));
		setPreferredSize(new DimensionUIResource(800, 600));
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(50, 50);
		pack();
	}
}
