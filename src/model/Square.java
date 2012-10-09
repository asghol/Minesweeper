package model;

/**
 * Class that holds data about a Square in the game such as how many mines are adjacent
 * @author Asgeir Hølleland
 */
public class Square {
	private boolean marked, visited, mine;
	private int numMines;
	
	/**
	 * Constructor
	 * @param mine indicates whether the Square is a mine or not
	 */
	public Square(boolean mine) {
		marked = false;
		visited = false;
		this.mine = mine;
		numMines = 0;
	}
	
	/**
	 * 
	 * @return true if marked, false otherwise
	 */
	public boolean isMarked() {
		return marked;
	}
	
	/**
	 * Method to mark a Square (for the user to mark a spot he suspects being a mine)
	 * @return true if marked, false otherwise
	 */
	public void mark() {
		if(!marked)
			marked = true;
		else
			marked = false;
	}
	
	/**
	 * Method for visiting a Square
	 * @return true if visited, false otherwise
	 */
	public boolean visit() {
		if(!visited) {
			visited = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Method to check if a Square is visited or not
	 * @return true if visited, false otherwise
	 */
	public boolean isVisited() {
		return visited;
	}
	
	/**
	 * Method to check if a Square is a mine or not
	 * @return true if mine, otherwise false
	 */
	public boolean isMine() {
		return mine;
	}
	
	/**
	 * Method for getting the number of adjacent mines
	 * @return number of mines adjacent to this Square
	 */
	public int adjacentMines() {
		return numMines;
	}
	
	/**
	 * Method for updating the number of mines adjacent to this Square
	 */
	public void newMineFound() {
		numMines++;
	}
	
	/**
	 * toString method for testing purposes
	 */
	public String toString() {
		if(!visited)
			return "[?]";
		if(mine)
			return "[X]";
		if(numMines != 0)
			return "[" + numMines + "]";
		return "[ ]";
	}
}