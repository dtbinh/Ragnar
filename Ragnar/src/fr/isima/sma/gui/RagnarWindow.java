package fr.isima.sma.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.plaf.DimensionUIResource;

public class RagnarWindow extends JFrame {
	
	public RagnarWindow() {
		super();
		getContentPane().setLayout(new BorderLayout());
		setTitle("Ragnar : Simulation Multi Agents");
		setPreferredSize(new DimensionUIResource(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(200, 200);
		pack();
	}
}
