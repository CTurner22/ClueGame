package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.MenuItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import javax.lang.model.type.DeclaredType;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.ClueControlPanel;
import gui.ExitMenuItem;
import gui.NotesMenuItem;


/*
 * Board class
 * Casey Turner, Murat Tuter
 * 
 */

@SuppressWarnings("serial")
public class Board extends JPanel {

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

	
//	private Map<String, Player> players;
	private Vector<Player> players;
	private HumanPlayer humanPlayer;

	private Vector<Card> deck;
	private Map<Character, Card> roomCards;

	private Solution theCrime;
	
	private static Board theInstance = new Board();
	
	// constructor is private to ensure only one can be created
	private Board() {}
	
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(int i = 0; i<numColumns;i++) {
			for(int j=0; j<numRows; j++) {
				grid[i][j].draw(g);
			}
		}
		
		for( Player p: players) {
			p.draw(g);
		}
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
		
		// catch errors here 
		try {
			
			legend = new HashMap<Character, String>();
			roomCards = new HashMap<Character, Card>();
			grid = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
			players = new Vector<Player>();			
			deck = new Vector<Card>();

			loadRoomConfig();
			loadBoardConfig();
			loadPlayers();
			loadWeapons();
			
			
		} catch(Exception e){
			System.err.println(e.getMessage());
		}
		
		// initialize adj lists
		calcAdjacencies();
	}
	
	public void deal() {

		// shuffle with a set of "detective notes" that players used to keep track of cards they've seen and to indicate who they currently suspect (if anyone). For the computer players these detective notes are represented as a list of seen cards. To assist the human player, we will create a custom dialog that can be filled in (but it's up to the human to use it). The custom dialog is shown below. The check boxes on the left can be used to check off any cards that have been seen. The combo boxes can be used to remember the player's c deck
		Collections.shuffle(deck);
		
		Card tempPerson = deck.get(0), tempRoom = deck.get(0), tempWeapon = deck.get(0);
		
		// get three of each type of card
        for(Card card : deck) {
			switch(card.getType()){
			case PERSON:
				tempPerson = card;
				break;
			case WEAPON:
				tempWeapon = card;
				break;
			case ROOM:
				tempRoom = card;
			}
        }
        
        // create solution
        theCrime = new Solution(tempPerson, tempRoom, tempWeapon);
        
        // remove from deck to not deal to players
        deck.remove(tempPerson);
        deck.remove(tempRoom);
        deck.remove(tempWeapon);

        // deal
        int i = 0;
        while(i < deck.size()) {
        	for(Player person : players) {
        		if(i >= deck.size()) break;
        		person.addToHand(deck.get(i));
        		i++;
        	}
        }
        
        // add back so computer knows what it can guess
        deck.add(tempPerson);
        deck.add(tempRoom);
        deck.add(tempWeapon);
        
    } 

	
	// loads weapons file
	
	private void loadWeapons() throws FileNotFoundException, BadConfigFormatException {

		File file = new File(weaponsFile); 
		Scanner sc = new Scanner(file); 

		
		String line;

		// parse each line
		while (sc.hasNextLine()) {
			line = sc.nextLine().trim();
		    
			//test for blank lines
		    if (line == "") { 
		    	throw new BadConfigFormatException("Incorrect weapon file format");
		    }
		    
		    // save as a card
		    deck.add(new Card(line, CardType.WEAPON));

		}
	}

	//loads player file
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
		    Boolean human = data[2].trim().toLowerCase().contains("human") ? true : false;

		    String name = data[0].trim();
		    String color = data[1].trim();
		    int row = Integer.parseInt(data[3].trim());
		    int column = Integer.parseInt(data[4].trim());

		    // make either human or computer and save as a player
		    if(human) {
		    	humanPlayer = new HumanPlayer(name, color, row, column);
		    	players.add( humanPlayer);
		    } else {
		    	players.add( new ComputerPlayer(name, color, row, column));
		    }
		    
		    
		    // make the player card
		    deck.add(new Card(name, CardType.PERSON));

	    	
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
		    
		    // save the legend
	    	legend.put(symbol, name);

	    	
		    // check if card and add if it is
	    	// also throw error if its not card or other
		     if (data[2].contains("Card")) {
		    	 Card card = new Card(name, CardType.ROOM);
				 deck.add(card);
				 roomCards.put(symbol, card);
		     } else if (!data[2].contains("Other")) {
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
		    		case 'C':
		    			tempBoardCell.setCenterRoom(true);
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

	public Vector<Player> getPlayers() {
		return players;
	}

	public Vector<Card> getDeck() {
		return deck;
	}

	public boolean checkAccusation(Solution testSolution) {
		return testSolution.equals(theCrime);
	}

	public void setTheCrime(Solution actualSolution) {
		theCrime = actualSolution;
	}
	public Solution getTheCrime() {
		return theCrime;
	}


	public Card getRoomCard(BoardCell cell) {
		return roomCards.get(cell.getInitial());
	}


	public Object handleSuggestion(Player accuser, Solution suggestion) {
		Card responseCard = null;
		int i = players.indexOf(accuser);
		
		for(int j =0; j<players.size()-1;j++) {
			i = (i+1)%players.size();
			
			responseCard = players.get(i).disproveSuggestion(suggestion);
			if(responseCard != null) {
				return responseCard;
			}
		}
		return null;
	}

	public String getRoomName(char c) {
		return legend.get(c);
	}

	public Player nextPlayer() {
		
		return null;		
	}

	public void makeAccusation() {
		// TODO Auto-generated method stub
		
	}
	public HumanPlayer getHumanPlayer() {
		return humanPlayer;
	}

	}
