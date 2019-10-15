package clueGame;

import java.util.Map;

/*
 * Board class
 * Casey Turner, Murat Tuter
 * 
 */

public class Board {

	private final  int MAX_BOARD_SIZE = 50;
	private int numRows;
	private int numColumns;

	private static Board theInstance = new Board();
	
	// constructor is private to ensure only one can be created
	private Board() {}
	
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}


	public void setConfigFiles(String string, String string2) {
		// TODO Auto-generated method stub
		
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	public Map<Character, String> getLegend() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return 0;
	}

	public BoardCell getCellAt(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}


}
