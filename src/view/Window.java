package view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * Class to hold all components of the game
 * @author Asgeir Hølleland
 */
public class Window extends JFrame implements ActionListener {
	private static final long serialVersionUID = 3271761107727511963L;

	private JMenuBar jmb;
	private JMenu jmFile, jmHelp;
	private JMenuItem jmiNewGame, jmiEasy, jmiNormal, jmiHard, jmiCustomGame, jmiExit, jmiAbout;

	private BoardView bw;
	private Point p;
	private int rows = 9;
	private int columns = 9;
	private int mines = 10;
	
	private final int MARGIN_WIDTH = 6;
	private final int MARGIN_HEIGHT = 51;

	/**
	 * Constructor
	 */
	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Minesweeper");
		setIconImage(new ImageIcon("img" + File.separator + "Logo.png").getImage());

		setJMenuBar(createMenu());
		try {
			bw = new BoardView(rows, columns, mines);
		} catch (Exception e) {
			new ErrorDialog("ImageNotFoundException", e.getMessage());
			System.exit(0);
		}
		add(bw);

		int width = columns * bw.getImageWidth() + MARGIN_WIDTH;
		int height = rows * bw.getImageHeight() + MARGIN_HEIGHT;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		p = new Point(dim.width / 2 - width / 2, dim.height / 2 - height / 2);
		setLocation(p);
		
		pack();
		setResizable(false);
		setVisible(true);
		setSize(width, height);
	}

	// Method for creating a JMenuBar
	private JMenuBar createMenu() {
		jmb = new JMenuBar();

		jmFile = new JMenu("File");

		jmiNewGame = new JMenuItem("New Game");
		jmiNewGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		jmiNewGame.addActionListener(this);
		
		jmiCustomGame = new JMenuItem("Custom Game");
		jmiCustomGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		jmiCustomGame.addActionListener(this);

		jmiEasy = new JMenuItem("Easy");
		jmiEasy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0));
		jmiEasy.addActionListener(this);
		
		jmiNormal = new JMenuItem("Normal");
		jmiNormal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0));
		jmiNormal.addActionListener(this);
		
		jmiHard = new JMenuItem("Hard");
		jmiHard.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0));
		jmiHard.addActionListener(this);
		
		jmiExit = new JMenuItem("Exit");
		jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		jmiExit.addActionListener(this);

		jmFile.add(jmiNewGame);
		jmFile.add(jmiCustomGame);
		jmFile.addSeparator();
		jmFile.add(jmiEasy);
		jmFile.add(jmiNormal);
		jmFile.add(jmiHard);
		jmFile.addSeparator();
		jmFile.add(jmiExit);

		jmHelp = new JMenu("Help");

		jmiAbout = new JMenuItem("About");
		jmiAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		jmiAbout.addActionListener(this);

		jmHelp.add(jmiAbout);

		jmb.add(jmFile);
		jmb.add(jmHelp);

		jmb.setVisible(true);

		return jmb;
	}

	// Sets up a new game with the same settings
	private void newGame() {
		newGame(rows, columns, mines);
	}

	// Sets up a new game with custom settings
	private void newGame(int rows, int columns, int mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;

		getContentPane().removeAll();
		setJMenuBar(createMenu());
		try {
			add(new BoardView(rows, columns, mines));
		} catch (Exception e) {
			new ErrorDialog("ImageNotFoundException", e.getMessage());
			System.exit(0);
		}
		setVisible(true);
		setSize(columns * bw.getImageWidth() + MARGIN_WIDTH, rows * bw.getImageHeight() + MARGIN_HEIGHT);
		repaint();
	}

	// Creates a new custom sized game
	private void customGame() {
		CustomGame cg = new CustomGame(this, p, rows, columns, mines);
		if(!cg.failed()) {
			newGame(cg.getRows(), cg.getColumns(), cg.getMines());
		}
	}

	// Exits the game in a nice and clean manner
	private void exit() {
		System.exit(0);
	}

	// Opens the About dialog
	private void about() {
		new About(this, p);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == jmiNewGame) {
			newGame();
		}
		else if(evt.getSource() == jmiEasy) {
			newGame(9, 9, 10);
		}
		else if(evt.getSource() == jmiNormal) {
			newGame(16, 16, 40);
		}
		else if(evt.getSource() == jmiHard) {
			newGame(16, 30, 99);
		}
		else if(evt.getSource() == jmiCustomGame) {
			customGame();
		}
		else if(evt.getSource() == jmiExit) {
			exit();
		}
		else if(evt.getSource() == jmiAbout) {
			about();
		}
	}
}