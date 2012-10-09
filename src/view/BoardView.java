package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Board;
import model.Square;

/**
 * Class to hold the visuals of the game and listen to mouse activity
 * @author Asgeir Hølleland
 */
public class BoardView extends JPanel implements MouseListener {
	private static final long serialVersionUID = 8563705674683776847L;

	private Image[] img;
	private Board board;
	private int rows, columns, sHeight, sWidth;

	/**
	 * Construtor
	 * @param columns 	how many columns the board has
	 * @param rows 		how many rows the board has
	 * @param mines 	how many mines the board has
	 * @throws Exception
	 */
	public BoardView(int rows, int columns, int mines) throws Exception {
		this.rows = rows;
		this.columns = columns;

		board = new Board(rows, columns, mines);
		
		// Loads the images that is to be used in the game
		img = new Image[13];
		for(int i = 0; i <= 12; i++) {
			img[i] = new ImageIcon("img" + File.separator + i + ".png").getImage(); 
			if(img[i].getHeight(null) == -1) {
				throw ImageNotFoundException(i);
			}
		}

		// Gets hight and width of the graphics so that graphics can be changed (for instance scaled)
		sHeight = img[0].getHeight(null);
		sWidth = img[0].getWidth(null);

		addMouseListener(this);

		setDoubleBuffered(true);
		setVisible(true);
		setSize(new Dimension(columns * sWidth, rows * sHeight));
	}

	private Exception ImageNotFoundException(int i) {
		return new Exception("Could not find image " + i + ".png");
	}

	/**
	 * Method that decides what image is to be drawn where and draws it
	 */
	public void paintComponent(Graphics g) {
		
		// As long as the game is not won or lost, non-visited squares are closed
		if(!(board.won() || board.lost())) {
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++) {
					Square s = board.getSquare(j, i);
					Image image;
					if(s.isMarked() && !s.isVisited())
						image = img[10];
					else if(!s.isVisited())
						image = img[0];
					else
						image = img[getImage(s)];
					g.drawImage(image, j * sWidth, i * sHeight, this);
				}
			}
		}
		
		// When the game is lost or won all squares are shown
		else {
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++) {
					Square s = board.getSquare(j, i);
					Image image = img[getImage(s)];
					g.drawImage(image, j * sWidth, i * sHeight, this);
				}
			}
			
			// Set text graphics options
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setFont(new Font("monospaced", Font.BOLD, 40));
			
			// If won, write "YOU WIN!!" in the middle of the screen
			if(board.won()) {
				Point p = textPosition();
				g.drawString("YOU WON!!", p.x, p.y);
			}
			// If lost, write "YOU LOST!" in the middle of the screen
			else if(board.lost()) {
				Point p = textPosition();
				g.drawString("YOU LOST!", p.x, p.y);
			}
		}
	}
	
	private Point textPosition() {
		return new Point(getSize().width / 2 - 105, getSize().height / 2);
	}

	// Method for finding the right image index
	private int getImage(Square s) {
		int num;
		if(s.isVisited() && s.isMine())
			num = 12;
		else if(s.isMine())
			num = 11;
		else if(s.adjacentMines() == 0)
			num = 9;
		else
			num = s.adjacentMines();
		return num;
	}
	
	/**
	 * Gets the hight of the graphics used in the game
	 * @return hight of graphics
	 */
	public int getImageHeight() {
		return sHeight;
	}
	
	/**
	 * Gets the width of the graphics used in the game
	 * @return width of graphics
	 */
	public int getImageWidth() {
		return sWidth;
	}
	
	@Override
	public void mouseClicked(MouseEvent evt) {

	}

	@Override
	public void mouseEntered(MouseEvent evt) {

	}

	@Override
	public void mouseExited(MouseEvent evt) {

	}

	@Override
	public void mousePressed(MouseEvent evt) {

	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		int squarePosX = evt.getX() / sWidth;
		int squarePosY = evt.getY() / sHeight;
		if(!(board.won() || board.lost()) && (squarePosY < rows && squarePosX < columns)) {
			if(evt.getButton() == MouseEvent.BUTTON1)
				board.click(squarePosX, squarePosY);
			else if(evt.getButton() == MouseEvent.BUTTON3)
				board.flag(squarePosX, squarePosY);
		}
		repaint();
	}
}