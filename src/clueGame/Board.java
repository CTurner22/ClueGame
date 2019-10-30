package clueGame;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
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
	
	public final String path = "src/Data/";
	
	private String layoutFile;
	private String roomFile;
	private String weaponsFile;
	private String playersFile;

	
	private Map<BoardCell, Set<BoardCell>> adjacencies;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;

	private Map<Character, String> legend;
	
	private Map<String, Player> players;
	private Set<Card> cards;


	private static Board theInstance = new Board();
	
	// constructor is private to ensure only one can be created
	private Board() {}
	
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}


	public void setConfigFiles(String boardFile, String roomFile) {
		
		layoutFile = path + boardFile;
		this.roomFile = path + roomFile;

	}
	
	public void setConfigFiles(String boardFile, String roomFile, String weaponsFile, String playersFile) {
		
		layoutFile = path + boardFile;
		this.roomFile = path + roomFile;
		this.weaponsFile = path + weaponsFile;
		this.playersFile = path + playersFile;

	}

	public void initialize(){
		
		//catch errors here 
		try {
			
			legend = new HashMap<Character, String>();
			grid = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
			players = new HashMap<String, Player>();			
			cards = new HashSet<Card>();

			loadRoomConfig();
			loadBoardConfig();
			loadPlayers();
			loadWeapons();
			
			
		} catch(Exception e){
			System.err.println(e.getMessage());
		}
		
		//initialize adj lists
		calcAdjacencies();
		
	}

	private void loadWeapons() {
	}

	private void loadPlayers() throws FileNotFoundException, BadConfigFormatException{

		File file = new File(playersFile); 
		Scanner sc = new Scanner(file); 

		
		String line;

		// parse each line
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			
		    String[] data = line.split(",");
		    
		    if (data.length != 5) {
		    	throw new BadConfigFormatException("Incorrect player file format");
		    }
		    
		    if (data[0].trim() == "") { 
		    	throw new BadConfigFormatException("Incorrect player file format");
		    }
		    
		    // parse data
		    Boolean human = data[2].trim().toLowerCase() == "human" ? true : false;

		    String name = data[0].trim();
		    String color = data[1].trim();
		    int row = Integer.parseInt(data[3].trim());
		    int column = Integer.parseInt(data[4].trim());

		    // make either human or computer
		    if(human) {
		    	players.put(name, new HumanPlayer(name, color, row, column));
		    } else {
		    	players.put(name, new ComputerPlayer(name, color, row, column));
		    }
	    	

		}

	}

	public void calcTargets(int i, int j, int pathLength) {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		visited.add(grid[i][j]);
		
		// kick off recursive algo
		findTargets(grid[i][j], pathLength);		
	}
	
	public void findTargets(BoardCell startCell, int pathLength){
			
			// loop through adj cells
			for(BoardCell nextTo : adjacencies.get(startCell)) {
				
				// skip if visited
				if (visited.contains(nextTo)) {
					continue;
				}
				
				// visit
				visited.add(nextTo);
				
				if (pathLength == 1 || nextTo.isDoorway()) {
					//found target
					targets.add(nextTo);
				} else {
					//recurse
					findTargets(nextTo, pathLength-1);
				}
				
				visited.remove(nextTo);
			}
			
			visited.add(startCell);
			
		}

	void calcAdjacencies() {
		adjacencies = new HashMap<BoardCell, Set<BoardCell>>();
		
		// loop through each spot in the grid and get adj cells 
		for (int i=0; i < numRows; i++) {
			for (int j=0; j < numColumns; j++) {
				
				BoardCell loc = grid[i][j];
				
				Set<BoardCell> temp = new HashSet<BoardCell>();
				
				// make sure its a valid location
				if( loc.isDoorway() || loc.isWalkway()) {
					tryInsert(i-1, j, temp, loc);
					tryInsert(i+1, j, temp, loc);
					tryInsert(i, j-1, temp, loc);
					tryInsert(i, j+1, temp, loc);
				}

				// save it
				adjacencies.put(loc, temp);
			}
		}
		
	}
	
	// helper for catching out of bounds of grid and non valid locations
	public void tryInsert(int i, int j, Set<BoardCell> temp, BoardCell originalLoc) {
		
		// catch out of bounds
		if(i >= numRows || j >= numColumns) {
			return;
		}
		
		if(i < 0 || j < 0) {
			return;
		}
		
		// handle exiting door
		if(originalLoc.isDoorway()) {
			if(originalLoc.allowsEntryFrom(grid[i][j]) && grid[i][j].isWalkway() ) {
				temp.add(grid[i][j]);
			}
			return;
		}
		
		// only catch walkways and doors
		if (grid[i][j].isWalkway()|| grid[i][j].allowsEntryFrom(originalLoc)) {
			temp.add(grid[i][j]);
		}
	}
	

	// getters
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

	// loads in the LEGEND config file
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		
		File file = new File(roomFile); 
		Scanner sc = new Scanner(file); 
		
		String row;

		// parse each line
		while (sc.hasNextLine()) {
			row = sc.nextLine();
		    String[] data = row.split(",");
		    
		    if (data.length != 3) {
		    	throw new BadConfigFormatException("Incorrect legend file format");
		    }
		    
		    if (data[0].trim() == "") { 
		    	throw new BadConfigFormatException("Incorrect legend file format");
		    }
		    
		    char symbol = data[0].trim().charAt(0);
		    String name = data[1].trim();
	    	legend.put(symbol, name);

	    	
		    // Do something with the card or other classification here
		     if (!(data[2].contains("Other") || data[2].contains("Card"))) {
		    	 throw new BadConfigFormatException("bad room category");
		     }
		}

	}

	// Loads in the BOARD Config
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		File file = new File(layoutFile); 
		Scanner sc = new Scanner(file); 
		
		String row;
		int rowNum = 0;
	    int columnNum = 0;
	    int outerColumnNumber = -1;					

	    // parse each line
		while (sc.hasNextLine()) {
			row = sc.nextLine();
		    String[] data = row.split(",");
		    
		    columnNum = 0;
		    for( String x:data) {
		    	
		    	// test for legend layout room miss match
		    	if(!legend.containsKey(x.charAt(0))) {
		    		throw new BadConfigFormatException("Legend and Layout room inconsistancy");
		    	}
		    	
		    	BoardCell tempBoardCell = new BoardCell(rowNum, columnNum, x.charAt(0));
		    	
		    	if (x.length() > 1) {
		    		char direction = x.charAt(1);
		    		DoorDirection door;
		    		
		    		//switch to go between input char and enum
		    		switch(Character.toUpperCase(direction)) {
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
		    
		    // test for weird column sizes 
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

	public Set<BoardCell> getAdjList(int i, int j) {
		return adjacencies.get(grid[i][j]);
	}


	public Set<BoardCell> getTargets() {
		return targets;
	}

	public Map<String, Player> getPlayers() {
		return players;
	}

	public Set<Card> getCards() {
		return cards;
	}

}
