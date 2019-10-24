package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

/*
 * Tests for Input Board
 * Casey Turner, Murat Tuter
 * 
 */
public class InputBoardTests {

	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 25;

	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		
		board = Board.getInstance();
		board.setConfigFiles("clueGameLayout.csv", "roomLegend.txt");		
		board.initialize();
	}
	
	@Test
	public void testRooms() {
		
		// Get the map
		Map<Character, String> legend = board.getLegend();
		
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());

		// test a couple
		assertEquals("Conservatory", legend.get('C'));
		assertEquals("Ballroom", legend.get('B'));
		assertEquals("Game room", legend.get('G'));
		assertEquals("Dining room", legend.get('D'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	

	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(3, 4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		room = board.getCellAt(6, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		
		room = board.getCellAt(22, 20);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		
		room = board.getCellAt(8, 23);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		
		// Test non doors
		room = board.getCellAt(24, 24);
		assertFalse(room.isDoorway());	

		BoardCell cell = board.getCellAt(0, 5);
		assertFalse(cell.isDoorway());		

	}
	
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row=0; row< board.getNumRows(); row++)
			for (int col=0; col< board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(20, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRoomInitials() {
		
		// Test first cell in room
		assertEquals('O', board.getCellAt(0, 0).getInitial());
		assertEquals('E', board.getCellAt(0, 7).getInitial());
		assertEquals('G', board.getCellAt(10, 0).getInitial());
		
		// Test last cell in room
		assertEquals('O', board.getCellAt(6, 4).getInitial());
		assertEquals('K', board.getCellAt(24, 24).getInitial());
		
		// Test a walkway
		assertEquals('W', board.getCellAt(9, 0).getInitial());
		// Test the closet
		assertEquals('X', board.getCellAt(12,13).getInitial());
	}
	

}
