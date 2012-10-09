package model;

import java.util.Random;

/**
 * Class that holds data about a Board
 * @author Asgeir Hølleland
 */
public class Board {
	private Square[][] board;
	private int width, height, numMines, numSafe;
	private boolean validBoard, won, lost;

	/**
	 * Constructor
	 * @param width 	width of the board
	 * @param hight 	hight of the board
	 * @param numMines 	number of mines (cannot be more then width * hight - 1) 
	 */
	public Board(int height, int width, int numMines) {
		won = false;
		lost = false;
		this.height = height;
		this.width = width;
		this.numMines = numMines;
		numSafe = (height * width) - numMines;
		board = new Square[height][width];
		validBoard = generateBoard();
	}

	/**
	 * Generates a board
	 * @return true if board is valid, otherwise false
	 */
	private boolean generateBoard() {
		Square[] miner = new Square[numMines];
		Random r = new Random();

		// Creates mines at the board
		if(numMines < height * width && width > 0 && height > 0) {
			for(int i = 0; i < numMines; i++) {
				boolean isCreated = false;
				int mineCounter = 0;
				while(!isCreated) {
					int posX = r.nextInt(height);
					int posY = r.nextInt(width);
					if(board[posX][posY] == null) {
						Square Square = new Square(true);
						board[posX][posY] = Square;
						miner[mineCounter] = Square;
						mineCounter++;
						isCreated = true;
					}
				}
			}

			// Creates safe squares
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					if(board[i][j] == null)
						board[i][j] = new Square(false);
					else if(board[i][j].isMine()) {
						if(j > 0) {
							if(board[i][j - 1] == null)
								board[i][j - 1] = new Square(false);
							board[i][j - 1].newMineFound(); // W
						}
						if(i > 0 && j > 0) {
							if(board[i - 1][j - 1] == null)
								board[i - 1][j - 1] = new Square(false);
							board[i - 1][j - 1].newMineFound(); // NW
						}
						if(i > 0) {
							if(board[i - 1][j] == null)
								board[i - 1][j] = new Square(false);
							board[i - 1][j].newMineFound(); // N
						}
						if(i > 0 && width - 1 > j) {
							if(board[i - 1][j + 1] == null)
								board[i - 1][j + 1] = new Square(false);
							board[i - 1][j + 1].newMineFound(); // NE
						}
						if(width - 1 > j) {
							if(board[i][j + 1] == null)
								board[i][j + 1] = new Square(false);
							board[i][j + 1].newMineFound(); // E
						}
						if(width - 1 > j && height - 1 > i) {
							if(board[i + 1][j + 1] == null)
								board[i + 1][j + 1] = new Square(false);
							board[i + 1][j + 1].newMineFound(); // SE
						}
						if(height - 1 > i) {
							if(board[i + 1][j] == null)
								board[i + 1][j] = new Square(false);
							board[i + 1][j].newMineFound(); // S
						}
						if(j > 0 && height - 1 > i) {
							if(board[i + 1][j - 1] == null)
								board[i + 1][j - 1] = new Square(false);
							board[i + 1][j - 1].newMineFound();  // SW
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Indicates whether the board is valid or not
	 * @return true if board is valid, otherwise false 
	 */
	public boolean validBoard() {
		return validBoard;
	}
	
	/**
	 * Gives the number of safe squares
	 * @return number of safe squares
	 */
	public int countSafe() {
		return numSafe;
	}
	
	/**
	 * Checks whether the game is won or not
	 * @return true if won, otherwise false
	 */
	public boolean won() {
		return won;
	}
	
	/**
	 * Checks whether the game is lost or not
	 * @return true if lost, otherwise false
	 */
	public boolean lost() {
		return lost;
	}
	
	/**
	 * Flags a square at a given position
	 * @param x
	 * @param y
	 */
	public void flag(int x, int y) {
		getSquare(x, y).mark();
	}

	/**
	 * Method to "click" on a given position of the board
	 * @param x
	 * @param y
	 * @return true if operation is valid, otherwise false
	 */
	public void click(int x, int y) {
		if(board[y][x].isMine()) {
			board[y][x].visit();
			lost = true;
		}
		else if(board[y][x].isVisited()) {
			// DO NOTHING
		}
		else if(board[y][x].adjacentMines() == 0) {
			board[y][x].visit();
			numSafe--;
			open(x, y);
		}
		else {
			board[y][x].visit();
			numSafe--;
			if(numSafe == 0)
				won = true;
		}
	}

	// Method for opening squares that have no mines next to them
	private void open(int x, int y) {
		if(x > 0) {
			if(!board[y][x - 1].isVisited()) {
				board[y][x - 1].visit();
				numSafe--;
				if(board[y][x - 1].adjacentMines() == 0)
					open(x - 1, y);
			}
		}
		if(x > 0 && y > 0) {
			if(!board[y - 1][x - 1].isVisited()) {
				board[y - 1][x - 1].visit();
				numSafe--;
				if(board[y - 1][x - 1].adjacentMines() == 0)
					open(x - 1, y - 1);
			}
		}
		if(width - 1 > x) {
			if(!board[y][x + 1].isVisited()) {
				board[y][x + 1].visit();
				numSafe--;
				if(board[y][x + 1].adjacentMines() == 0)
					open(x + 1, y);
			}
		}
		if(width - 1 > x && y > 0) {
			if(!board[y - 1][x + 1].isVisited()) {
				board[y - 1][x + 1].visit();
				numSafe--;
				if(board[y - 1][x + 1].adjacentMines() == 0)
					open(x + 1, y - 1);
			}
		}
		if(y > 0) {
			if(!board[y - 1][x].isVisited()) {
				board[y - 1][x].visit();
				numSafe--;
				if(board[y - 1][x].adjacentMines() == 0)
					open(x, y - 1);
			}
		}
		if(width - 1 > x && height - 1 > y) {
			if(!board[y + 1][x + 1].isVisited()) {
				board[y + 1][x + 1].visit();
				numSafe--;
				if(board[y + 1][x + 1].adjacentMines() == 0)
					open(x + 1, y + 1);
			}
		}
		if(height - 1 > y) {
			if(!board[y + 1][x].isVisited()) {
				board[y + 1][x].visit();
				numSafe--;
				if(board[y + 1][x].adjacentMines() == 0)
					open(x, y + 1);
			}
		}
		if(x > 0 && height - 1 > y) {
			if(!board[y + 1][x - 1].isVisited()) {
				board[y + 1][x - 1].visit();
				numSafe--;
				if(board[y + 1][x - 1].adjacentMines() == 0)
					open(x - 1, y + 1);
			}
		}
		if(numSafe == 0)
			won = true;
	}
	
	/**
	 * Method for getting a specific Square
	 * @param x
	 * @param y
	 * @return the Square from the given position on the Board
	 */
	public Square getSquare(int x, int y) {
		return board[y][x];
	}
	
	/**
	 * toString for testing purposes
	 */
	public String toString() {
		String str = new String("");
		if(validBoard) {
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					str += board[i][j].toString();
				}
				str += "\n";
			}
		}
		return str;
	}
}