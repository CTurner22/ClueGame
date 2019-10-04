package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	
	private IntBoard board;

	@Before
    public void beforeAll() {
       board = new IntBoard();      
    }
	
	
	@Test
	public void testAdjacencyTopLeft() {
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyBottomRight() {
		BoardCell cell = board.getCell(3,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyRightEdge() {
		BoardCell cell = board.getCell(2,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertTrue(testList.contains(board.getCell(3, 3)));
		
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacencyLeftEdge() {
		BoardCell cell = board.getCell(2,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(3, 0)));
		
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacencyLeftMiddle() {
		BoardCell cell = board.getCell(1,1);
		Set<BoardCell> testList = board.getAdjList(cell);
		
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(1, 2)));

		assertEquals(4, testList.size());
	}
	
	@Test
	public void testAdjacencyRightMiddle() {
		BoardCell cell = board.getCell(2,2);
		Set<BoardCell> testList = board.getAdjList(cell);
		
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(3, 2)));

		assertEquals(4, testList.size());
	}
	
	@Test
	public void testTargets1(){
		
		BoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 1);
		
		Set targets = board.getTargets();
		
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(2, 1)));
	}
	
	@Test
	public void testTargets2(){
		
		BoardCell cell = board.getCell(3, 2);
		board.calcTargets(cell, 3);
		
		Set targets = board.getTargets();
		
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));

	}
	
	@Test
	public void testTargets3(){
		
		BoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 2);
		
		Set targets = board.getTargets();
		
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));

	}
}
