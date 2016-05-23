package gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private DepixelateAlgorithm algorithm;
	
	public ImagePanel() {
		super();
		
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				System.out.println(e.getX() + " " + e.getY());
				algorithm.clickOnPixel(e.getX(), e.getY());
				repaint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void setImage(File imageFile) {
		algorithm = DepixelateAlgorithm.loadForImageFile(imageFile);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(algorithm.getImage() != null)
			g.drawImage(algorithm.getImage(), 0, 0, null);
	}
	
}
