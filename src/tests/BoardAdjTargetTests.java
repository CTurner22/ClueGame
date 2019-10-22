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
/*
 * Test for board adjancencies 
 * Casey Turner, Murat Tuter
 * 
 */

public class BoardAdjTargetTests {

	
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("src/OurData/clueGameLayout.csv", "src/OurData/roomLegend.txt");		
		board.initialize();
	}

	// Ensure that player does not move around within room
	@Test
	public void testAdjacenciesInsideRooms() {
	
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. 
	@Test
	public void testAdjacencyRoomExit(){

	}
	
	// Test adjacency at entrance to rooms
	@Test
	public void testAdjacencyDoorways(){
		
	}

	// Test a variety of walkway scenarios
	@Test
	public void testAdjacencyWalkways(){
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	@Test
	public void testTargetsOneStep() {
				
	}
	
	// Tests of just walkways, 2 steps
	@Test
	public void testTargetsTwoSteps() {
	
	}
	
	// Tests of just walkways, 4 steps
	@Test
	public void testTargetsFourSteps() {
	}	
	
	// Tests of just walkways plus one door, 6 steps
	@Test
	public void testTargetsSixSteps() {

	}	
	
	// Test getting into a room
	@Test 
	public void testTargetsIntoRoom(){
		
	}
	
	// Test getting into room, doesn't require all steps
	@Test
	public void testTargetsIntoRoomShortcut() {
		
	}

	// Test getting out of a room
	@Test
	public void testRoomExit(){
	}

}
