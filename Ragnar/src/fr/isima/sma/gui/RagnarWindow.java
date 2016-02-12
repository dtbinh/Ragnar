package fr.isima.sma.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.plaf.DimensionUIResource;

public class RagnarWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RagnarWindow() {
		super();
		getContentPane().setLayout(new BorderLayout());
		setTitle("Ragnar : Simulation Multi Agents");
		setPreferredSize(new DimensionUIResource(800, 600));
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(200, 200);
		pack();
	}
}
