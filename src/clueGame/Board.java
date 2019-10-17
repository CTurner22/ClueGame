package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


/*
 * Board class
 * Casey Turner, Murat Tuter
 * 
 */

public class Board {

	private final  int MAX_BOARD_SIZE = 50;
	private int numRows;
	private int numColumns;
	
	private String layoutFile;
	private String legendFile;
	
	private Map<BoardCell, Set<BoardCell>> adjacencies;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;

	private Map<Character, String> legend;


	private static Board theInstance = new Board();
	
	// constructor is private to ensure only one can be created
	private Board() {}
	
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}


	public void setConfigFiles(String lout, String lgn) {
		
		layoutFile = lout;
		legendFile = lgn;

	}

	public void initialize(){
		try {
			
			
			legend = new HashMap<Character, String>();
			loadRoomConfig();
			
			grid = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
			loadBoardConfig();

			
		} catch(Exception e){
			System.err.println(e.getMessage());
		}


	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCellAt(int i, int j) {
		return grid[i][j];
	}

	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		
		File file = new File(legendFile); 
		Scanner sc = new Scanner(file); 
		
		String row;

		while (sc.hasNextLine()) {
			row = sc.nextLine();
		    String[] data = row.split(",");
		    
		    try {
		    	legend.put(data[0].trim().charAt(0), data[1].trim());
		    } catch(Exception e) {
		    	throw new BadConfigFormatException("Incorrect legend file format");
		    }
		    
		    // Do something with the card or other classification here
		     if (!(data[2].contains("Other") || data[2].contains("Card"))) {
		    	 throw new BadConfigFormatException("bad room category");
		     }
		    
		    
		}

			    
	}


	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		File file = new File(layoutFile); 
		Scanner sc = new Scanner(file); 
		
		String row;
		int rowNum = 0;
	    int columnNum = 0;
	    int outerColumnNumber = -1;

		while (sc.hasNextLine()) {
			row = sc.nextLine();
		    String[] data = row.split(",");
		    
		    columnNum = 0;
		    for( String x:data) {
		    	
		    	if(!legend.containsKey(x.charAt(0))) {
		    		throw new BadConfigFormatException("Legend and Layout room inconsistancy");
		    	}
		    	
		    	BoardCell tempBoardCell = new BoardCell(rowNum, columnNum, x.charAt(0));
		    	
		    	if (x.length() > 1) {
		    		char direction = x.charAt(1);
		    		DoorDirection door;
		    		
		    		switch(direction) {
		    		case 'L':
		    			door = DoorDirection.LEFT;
		    			break;
		    		case 'R':
		    			door = DoorDirection.RIGHT;
		    			break;
		    		case 'U':
		    			door = DoorDirection.UP;
		    			break;
		    		case 'D':
		    			door = DoorDirection.DOWN;
		    			break;
		    		default:
		    			door = DoorDirection.NA;
		    		}
		    		
		    		tempBoardCell.setDoor(door);
		    	}
		    	
		    	grid[rowNum][columnNum] = tempBoardCell;
		    	columnNum++;
		    }
		    
		    if(outerColumnNumber >= 0 && columnNum != outerColumnNumber) {
		    	throw new BadConfigFormatException("Column inconsistancy");
		    } else {
		    	outerColumnNumber = columnNum;
		    }
		    
		    rowNum++;
		
		}
		
		numRows = rowNum;
		numColumns = columnNum;
			    
	}


}
