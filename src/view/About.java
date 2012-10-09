package view;

import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Simple dialog box, will probably update with graphics, info and maybe sound
 * @author Asgeir Hølleland
 */
public class About extends JDialog {
	private static final long serialVersionUID = -7754918383926484094L;
	private JLabel jlCreator;
	
	/**
	 * Constructor
	 * @param frame parent frame
	 * @param p point of location to place the about frame
	 */
	public About(JFrame frame, Point p) {
		super(frame, "About the game", false);
		
		setup();
		
		setLocation(p);
		pack();
		setResizable(false);
		setVisible(true);
		setSize(175, 60);
	}
	
	// Method for setting up the frame
	private void setup() {
		jlCreator = new JLabel("  Created by Asgeir Hølleland");
		add(jlCreator);
	}
}