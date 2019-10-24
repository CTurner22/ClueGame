package tests;


import java.util.Set;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

/*
 * Tests for Adj and targets for board
 * Casey Turner, Murat Tuter
 * 
 */
public class BoardAdjTargetTests {

	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("src/ProffessorData/CTest_ClueLayout.csv", "src/ProffessorData/CTest_ClueLegend.txt");		
		board.initialize();
	}


	// adj tests inside rooms 
	// Color : Yellow
	@Test
	public void testAdjacenciesInsideRooms(){
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(24, 24);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(6, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(20, 0);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(2, 2);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(22, 21);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(24, 24);
		assertEquals(0, testList.size());
	}

	// adj that are doorways in every direction
	// color : green
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
		assertTrue(testList.contains(board.getCellAt(17, 12)));
		
	}
	
	// Test adjacency at entrance to rooms in every direction
	// color: purple
	@Test
	public void testAdjacencyDoorways(){
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(23, 5);
		assertTrue(testList.contains(board.getCellAt(24, 5)));
		assertTrue(testList.contains(board.getCellAt(22, 5)));
		assertTrue(testList.contains(board.getCellAt(23, 4)));
		assertTrue(testList.contains(board.getCellAt(23, 6)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(18, 22);
		assertTrue(testList.contains(board.getCellAt(17, 22)));
		assertTrue(testList.contains(board.getCellAt(19, 22)));
		assertTrue(testList.contains(board.getCellAt(18, 21)));
		assertTrue(testList.contains(board.getCellAt(18, 23)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(22, 19);
		assertTrue(testList.contains(board.getCellAt(21, 19)));
		assertTrue(testList.contains(board.getCellAt(23, 19)));
		assertTrue(testList.contains(board.getCellAt(22, 18)));
		assertTrue(testList.contains(board.getCellAt(22, 20)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(19, 15);
		assertTrue(testList.contains(board.getCellAt(20, 15)));
		assertTrue(testList.contains(board.getCellAt(18, 15)));
		assertTrue(testList.contains(board.getCellAt(19, 16)));
		assertEquals(3, testList.size());
	}

	// Test a variety of walkway scenarios
	// Color : Orange
	@Test
	public void testAdjacencyWalkways(){
		// Test on top edge of board
		Set<BoardCell> testList = board.getAdjList(0, 6);
		assertTrue(testList.contains(board.getCellAt(0, 5)));
		assertTrue(testList.contains(board.getCellAt(1, 6)));

		assertEquals(2, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(9, 0);
		assertTrue(testList.contains(board.getCellAt(8, 0)));
		assertTrue(testList.contains(board.getCellAt(9, 1)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(8,7);
		assertTrue(testList.contains(board.getCellAt(8, 8)));
		assertTrue(testList.contains(board.getCellAt(8, 6)));
		assertTrue(testList.contains(board.getCellAt(7, 7)));
		assertTrue(testList.contains(board.getCellAt(9, 7)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(24, 18);
		assertTrue(testList.contains(board.getCellAt(24, 17)));
		assertTrue(testList.contains(board.getCellAt(23, 18)));
		assertTrue(testList.contains(board.getCellAt(24, 19)));
		assertEquals(3, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(7, 24);
		assertTrue(testList.contains(board.getCellAt(7, 23)));
		assertTrue(testList.contains(board.getCellAt(6, 24)));
		assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(18, 11);
		assertTrue(testList.contains(board.getCellAt(17, 11)));
		assertTrue(testList.contains(board.getCellAt(18, 10)));
		assertEquals(2, testList.size());
	}
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room, will only test two edges here
	// Color : Pink
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(15, 10, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 10)));
		assertTrue(targets.contains(board.getCellAt(15, 9)));	
		assertTrue(targets.contains(board.getCellAt(15, 11)));	


		
		board.calcTargets(6, 24, 1);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 23)));
		assertTrue(targets.contains(board.getCellAt(7, 24)));	
	}
	
	// Tests of just walkways, 2 steps
	// color : darker pink
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(0, 14, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 13)));
		assertTrue(targets.contains(board.getCellAt(1, 15)));
		assertTrue(targets.contains(board.getCellAt(2, 14)));

		
		board.calcTargets(12, 24, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 22)));
		assertTrue(targets.contains(board.getCellAt(13, 23)));
	}
	
	// Tests of just walkways, 3 steps
	// darker darker pink
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(17, 0, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 3)));
		assertTrue(targets.contains(board.getCellAt(19, 1)));
		assertTrue(targets.contains(board.getCellAt(18, 2)));
		assertTrue(targets.contains(board.getCellAt(18, 0)));
		assertTrue(targets.contains(board.getCellAt(17, 1)));

	}	
	
	// Tests of just walkways plus one door, 5 steps
	// Color : darker darker darker pink
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(0, 5, 5);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 4)));
		assertTrue(targets.contains(board.getCellAt(4, 5)));	
		assertTrue(targets.contains(board.getCellAt(4, 6)));	
		assertTrue(targets.contains(board.getCellAt(1, 5)));	
		assertTrue(targets.contains(board.getCellAt(2, 6)));	
		assertTrue(targets.contains(board.getCellAt(3, 5)));	
		assertTrue(targets.contains(board.getCellAt(3, 6)));	
	}	
	
	// Test getting into a room
	// 
	@Test 
	public void testTargetsIntoRoom(){
		// One room is exactly 2 away
		board.calcTargets(3, 14, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());

		assertTrue(targets.contains(board.getCellAt(3, 12)));
		assertTrue(targets.contains(board.getCellAt(2, 13)));
		assertTrue(targets.contains(board.getCellAt(4, 14)));
		assertTrue(targets.contains(board.getCellAt(1, 14)));
		assertTrue(targets.contains(board.getCellAt(5, 14)));
		assertTrue(targets.contains(board.getCellAt(2, 15)));
		assertTrue(targets.contains(board.getCellAt(5, 15)));
	}
	
	// Test getting into room, doesn't require all steps
	// color : dark purple
	@Test
	public void testTargetsIntoRoomShortcut()  {
		board.calcTargets(7, 2, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());

		assertTrue(targets.contains(board.getCellAt(6, 2)));
		assertTrue(targets.contains(board.getCellAt(8, 1)));
		assertTrue(targets.contains(board.getCellAt(8, 3)));
		assertTrue(targets.contains(board.getCellAt(7, 0)));
		assertTrue(targets.contains(board.getCellAt(7, 4)));
		assertTrue(targets.contains(board.getCellAt(9, 2)));

	}

	// Test getting out of a room
	// color : dark green
	@Test
	public void testRoomExit() {
		// Take one step, essentially just the adj list
		board.calcTargets(5, 19, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 19)));
		// Take two steps
		board.calcTargets(5, 19, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 18)));
		assertTrue(targets.contains(board.getCellAt(6, 20)));
		assertTrue(targets.contains(board.getCellAt(7, 19)));
	}

}
