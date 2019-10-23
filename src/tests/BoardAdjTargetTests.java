package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("CTest_ClueLayout.csv", "CTest_ClueLegend.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}


	@Test
	public void testAdjacenciesInsideRooms(){
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(24, 24);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(6, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(0, 20);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(2, 2);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(3, 5);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(6, 4);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	@Test
	public void testAdjacencyRoomExit(){
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(3, 4);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(3, 5)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(15, 21);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 20)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(16, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(17, 13)));
		//TEST DOORWAY UP
		testList = board.getAdjList(10, 1);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(9, 1)));
		//TEST DOORWAY UP, WHERE THERE'S A WALKWAY LEFT
		testList = board.getAdjList(18, 12);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(18, 11)));
		
	}
	
	// Test adjacency at entrance to rooms
	@Test
	public void testAdjacencyDoorways(){
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(9, 9);
		assertTrue(testList.contains(board.getCellAt(9, 8)));
		assertTrue(testList.contains(board.getCellAt(8, 9)));
		assertTrue(testList.contains(board.getCellAt(8, 8)));
		assertEquals(3, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(3, 12);
		assertTrue(testList.contains(board.getCellAt(2, 12)));
		assertTrue(testList.contains(board.getCellAt(3, 11)));
		assertTrue(testList.contains(board.getCellAt(3, 13)));
		assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(15, 17);
		assertTrue(testList.contains(board.getCellAt(21, 22)));
		assertTrue(testList.contains(board.getCellAt(21, 24)));
		assertTrue(testList.contains(board.getCellAt(20, 23)));
		assertTrue(testList.contains(board.getCellAt(22, 23)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(15, 13);
		assertTrue(testList.contains(board.getCellAt(15, 12)));
		assertTrue(testList.contains(board.getCellAt(15, 14)));
		assertTrue(testList.contains(board.getCellAt(14, 13)));
		assertTrue(testList.contains(board.getCellAt(16, 13)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	@Test
	public void testAdjacencyWalkways(){
		// Test on top edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(0, 6);
		assertTrue(testList.contains(board.getCellAt(0, 7)));
		assertEquals(1, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(9, 0);
		assertTrue(testList.contains(board.getCellAt(8, 0)));
		assertTrue(testList.contains(board.getCellAt(9, 1)));
		assertTrue(testList.contains(board.getCellAt(10, 0)));
		assertEquals(3, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(3, 15);
		assertTrue(testList.contains(board.getCellAt(3, 14)));
		assertTrue(testList.contains(board.getCellAt(3, 16)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(8,7);
		assertTrue(testList.contains(board.getCellAt(8, 8)));
		assertTrue(testList.contains(board.getCellAt(8, 6)));
		assertTrue(testList.contains(board.getCellAt(7, 7)));
		assertTrue(testList.contains(board.getCellAt(9, 7)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(14, 15);
		assertTrue(testList.contains(board.getCellAt(14, 16)));
		assertTrue(testList.contains(board.getCellAt(13, 15)));
		assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(7, 11);
		assertTrue(testList.contains(board.getCellAt(7, 10)));
		assertTrue(testList.contains(board.getCellAt(6, 11)));
		assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(5, 3);
		assertTrue(testList.contains(board.getCellAt(5, 2)));
		assertTrue(testList.contains(board.getCellAt(5, 4)));
		assertTrue(testList.contains(board.getCellAt(6, 3)));
		assertEquals(3, testList.size());
	}
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(21, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 7)));
		assertTrue(targets.contains(board.getCellAt(21, 6)));	
		
		board.calcTargets(14, 0, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 1)));
		assertTrue(targets.contains(board.getCellAt(13, 0)));	
		assertTrue(targets.contains(board.getCellAt(15, 0)));			
	}
	
	// Tests of just walkways, 2 steps
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(16, 5, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 5)));
		assertTrue(targets.contains(board.getCellAt(15, 4)));
		
		board.calcTargets(12, 0, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(10, 0)));
		assertTrue(targets.contains(board.getCellAt(12, 2)));
		assertTrue(targets.contains(board.getCellAt(13, 1)));
	}
	
	// Tests of just walkways, 4 steps
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(21, 7, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 7)));
		assertTrue(targets.contains(board.getCellAt(19, 7)));
		assertTrue(targets.contains(board.getCellAt(18, 6)));
		assertTrue(targets.contains(board.getCellAt(20, 6)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(14, 0, 4);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 4)));
		assertTrue(targets.contains(board.getCellAt(15, 3)));	
		assertTrue(targets.contains(board.getCellAt(14, 2)));	
		assertTrue(targets.contains(board.getCellAt(15, 1)));	
	}	
	
	// Tests of just walkways plus one door, 6 steps
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(14, 0, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 6)));
		assertTrue(targets.contains(board.getCellAt(15, 5)));	
		assertTrue(targets.contains(board.getCellAt(15, 3)));	
		assertTrue(targets.contains(board.getCellAt(14, 4)));	
		assertTrue(targets.contains(board.getCellAt(15, 1)));	
		assertTrue(targets.contains(board.getCellAt(14, 2)));	
		assertTrue(targets.contains(board.getCellAt(13, 4)));	
	}	
	
	// Test getting into a room
	@Test 
	public void testTargetsIntoRoom(){
		// One room is exactly 2 away
		board.calcTargets(17, 16, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		// directly left (can't go right 2 steps)
		assertTrue(targets.contains(board.getCellAt(17, 14)));
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(15, 16)));
		assertTrue(targets.contains(board.getCellAt(19, 16)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(18, 17)));
		assertTrue(targets.contains(board.getCellAt(18, 15)));
		assertTrue(targets.contains(board.getCellAt(16, 17)));
		assertTrue(targets.contains(board.getCellAt(16, 15)));
	}
	
	// Test getting into room, doesn't require all steps
	@Test
	public void testTargetsIntoRoomShortcut()  {
		board.calcTargets(12, 7, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(12, targets.size());
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(15, 7)));
		assertTrue(targets.contains(board.getCellAt(9, 7)));
		// directly right (can't go left)
		assertTrue(targets.contains(board.getCellAt(12, 10)));
		// right then down
		assertTrue(targets.contains(board.getCellAt(13, 9)));
		assertTrue(targets.contains(board.getCellAt(13, 7)));
		// down then left/right
		assertTrue(targets.contains(board.getCellAt(14, 6)));
		assertTrue(targets.contains(board.getCellAt(14, 8)));
		// right then up
		assertTrue(targets.contains(board.getCellAt(10, 8)));
		// into the rooms
		assertTrue(targets.contains(board.getCellAt(11, 6)));
		assertTrue(targets.contains(board.getCellAt(10, 6)));		
		// 
		assertTrue(targets.contains(board.getCellAt(11, 7)));		
		assertTrue(targets.contains(board.getCellAt(12, 8)));	
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit() {
		// Take one step, essentially just the adj list
		board.calcTargets(4, 20, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 19)));
		// Take two steps
		board.calcTargets(4, 20, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 19)));
		assertTrue(targets.contains(board.getCellAt(5, 19)));
		assertTrue(targets.contains(board.getCellAt(4, 18)));
	}

}
