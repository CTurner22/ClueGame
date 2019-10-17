package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.BoardCell.DoorDirection;

public class testBoard {
	
	private static int NUM_ROWS = 25;
	private static int NUM_COLS = 25;
	
	private static Board board; 
	
	@BeforeClass
	public static void setUp() throws Exception{
		board = board.getInstance();
		board.setConfigFiles("ClueGameLayout.csv", "roomLegend.txt");
		board.loadRoomConfig();
		board.loadBoardConfig();
		
	}

	@Test
	public void testLegend() {
		//test that legend is set as expected from input file
		Map<Character, String> legend = board.getLegend();
		assertEquals("Conservatory", legend.get('C')); //first in file
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Ballroom", legend.get('B'));
		assertEquals("Game room", legend.get('G'));
		assertEquals("Library", legend.get('L'));
		assertEquals("Office", legend.get('O'));
		assertEquals("Dining room", legend.get('D'));
		assertEquals("Lounge", legend.get('U'));
		assertEquals("Entrway", legend.get('E'));
		assertEquals("Closet", legend.get('X'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	@Test
	public void testDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLS, board.getNumColumns());
	}
	
	@Test
	public void testDoorDirections() {
		BoardCell door = board.getCell(1,4);
		assertTrue(door.isDoorway());
		assertEquals(DoorDirection.RIGHT, door.getDoorDirection());
		
		door = board.getCell(0,18);
		assertTrue(door.isDoorway());
		assertEquals(DoorDirection.LEFT, door.getDoorDirection());
		
		door = board.getCell(11,17);
		assertTrue(door.isDoorway());
		assertEquals(DoorDirection.UP, door.getDoorDirection());
		
		door = board.getCell(14,1);
		assertTrue(door.isDoorway());
		assertEquals(DoorDirection.DOWN, door.getDoorDirection());
		
		door = board.getCell(0,0);
		assertFalse(door.isDoorway());
	} 
	
	@Test
	public void testNumDoors() {
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(44, numDoors);
	}
	
	@Test
	public void testInitials() {
		assertEquals('C', board.getCell(0, 0).getInitial());
		assertEquals('K', board.getCell(0, 8).getInitial());
		assertEquals('B', board.getCell(0, 21).getInitial());
		assertEquals('G', board.getCell(9, 0).getInitial());
		assertEquals('L', board.getCell(9, 21).getInitial());
		assertEquals('O', board.getCell(24, 0).getInitial());
		assertEquals('D', board.getCell(24, 7).getInitial());
		assertEquals('U', board.getCell(24, 12).getInitial());
		assertEquals('E', board.getCell(24, 21).getInitial());
		assertEquals('X', board.getCell(24, 21).getInitial());
		assertEquals('W', board.getCell(24, 21).getInitial());
	}
}
