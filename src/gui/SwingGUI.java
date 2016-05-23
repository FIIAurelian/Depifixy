package gui;

import java.io.File;

import javax.swing.JFrame;

public class SwingGUI {

	private JFrame mainFrame;
	private ImagePanel imageFrame;
	
	public SwingGUI() { 
		mainFrame = new JFrame("Depixify");
		mainFrame.setSize(600, 600);
		imageFrame = new ImagePanel();
		imageFrame.setImage(new File("cat.png"));
		mainFrame.add(imageFrame);
	}
	
	public void showGUI() {
		mainFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingGUI gui = new SwingGUI();
		gui.showGUI();
	}
}
