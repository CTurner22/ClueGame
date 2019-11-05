package tests;


import java.util.Map;
import java.util.Set;
import java.util.Vector;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.CardType;

import clueGame.Player;


/*
 * Tests actions for board
 * Casey Turner, Murat Tuter
 * 
 */
public class GameActionTests {

	private static Board board;

	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("clueGameLayout.csv", "roomLegend.txt", "weapons.txt", "players.txt");		
		board.initialize();
	}
	

	/*
	 * Test Selecting a target location - ComputerPlayer
	 */
	
	// test if targets are random
	@Test
	public void testTargetRandomSelection() {
		 ComputerPlayer player = new ComputerPlayer();
		 
		 // Pick a location with no rooms in target, just three targets
		 board.calcTargets(18, 0, 2);
		 boolean loc_1 = false;
		 boolean loc_2 = false;
		 boolean loc_3 = false;
		 
		 // Run the test a large number of times
		 for (int i=0; i<100; i++) {
			 BoardCell selected = player.pickLocation(board.getTargets());
			 if (selected == board.getCellAt(17, 1))
				 loc_1 = true;
			 else if (selected == board.getCellAt(19, 1))
				 loc_2 = true;
			 else if (selected == board.getCellAt(18, 2))
				 loc_3 = true;
			 else {
				fail("Invalid target selected");

			 }
		 }
		 // Ensure each target was selected at least once
		 assertTrue(loc_1);
		 assertTrue(loc_2);
		 assertTrue(loc_3);
	}
	
	// test if an unvisited room is a target it is selected
	@Test
	public void testUnvisitedRoomSelection() {
		 ComputerPlayer player = new ComputerPlayer();
		 
		 // Pick a location with no rooms in target, just three targets
		 board.calcTargets(17, 3, 2);
			 
		 BoardCell selected = player.pickLocation(board.getTargets());
		 assertTrue(selected.isRoom());

	}
	
	// test if a room is recently visited, targets are random
	@Test
	public void testVisitedRoomSelection() {
		 ComputerPlayer player = new ComputerPlayer();
		 
		 player.addToVisited(board.getCellAt(11, 22));
		 
		 board.calcTargets(12, 22, 1);
		 boolean loc_1 = false;
		 boolean loc_2 = false;
		 boolean loc_3 = false;
		 boolean loc_4 = false;

		 // Run the test a large number of times
		 for (int i=0; i<100; i++) {
			 BoardCell selected = player.pickLocation(board.getTargets());
			 if (selected == board.getCellAt(11, 22))
				 loc_1 = true;
			 else if (selected == board.getCellAt(12, 23))
				 loc_2 = true;
			 else if (selected == board.getCellAt(12, 21))
				 loc_3 = true;
			 else if (selected == board.getCellAt(13, 22))
				 loc_4 = true;
			 else
				 fail("Invalid target selected");
		 }
		 // Ensure each target was selected at least once
		 assertTrue(loc_1);
		 assertTrue(loc_2);
		 assertTrue(loc_3);
		 assertTrue(loc_4);

	}
	
	
}
