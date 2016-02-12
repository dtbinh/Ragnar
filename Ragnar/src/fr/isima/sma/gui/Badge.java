package fr.isima.sma.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicTreeUI.SelectionModelPropertyChangeHandler;

import fr.isima.sma.world.Sector.SectorType;

public class Badge extends JLabel {
	
	static protected Color transparent = new Color(0,0,0,0);
	
	protected String imgName;
	protected BufferedImage image;
	protected ImageIcon icon;
	protected Dimension oldDim;
	
	public Badge(String pImgName) {
		super();
		setImage(pImgName);
		icon = new ImageIcon(image);
		setIcon(icon);
		oldDim = new Dimension();
		setVisible(true);
	}
	
	public Badge(Badge pBadge) {
		super();
		this.imgName = pBadge.imgName;
		this.image = pBadge.image;
		this.icon = pBadge.icon;
		this.oldDim = pBadge.oldDim;
		setIcon(icon);
		setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		//if(!oldDim.equals(this.getSize())) {
			oldDim = this.getSize();
			Image dimg = image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
			icon.setImage(dimg);
			//setIcon(icon);
		//}
	}
	
	public void setImage(String urlImg) {
		imgName = urlImg;
		try {
			image = ImageIO.read(new File(imgName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
