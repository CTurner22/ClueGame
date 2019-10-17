package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.BoardCell;
import experiment.IntBoard;

/*
 * Tests for IntBoard
 * Casey Turner, Murat Tuter
 * 
 */
public class IntBoardTests {

	private IntBoard myBoard;



	@Before
	public void beforeAll() {
		myBoard = new IntBoard(4,4);
	}

	@Test
	public void testAdjacency0() //test (0,0)
	{
		BoardCell cell = myBoard.getBoardCell(0,0);
		Set<BoardCell> testList = myBoard.getAdjList(cell);
		assertTrue(testList.contains(myBoard.getBoardCell(1, 0)));
		assertTrue(testList.contains(myBoard.getBoardCell(0, 1)));
		assertEquals(2, testList.size());
	}

	@Test
	public void testAdjacency1() //test (3,3)
	{
		BoardCell cell = myBoard.getBoardCell(3,3);
		Set<BoardCell> testList = myBoard.getAdjList(cell);
		assertTrue(testList.contains(myBoard.getBoardCell(3, 2)));
		assertTrue(testList.contains(myBoard.getBoardCell(2, 3)));
		assertEquals(2, testList.size());
	}

	@Test
	public void testAdjacency2() //test (0,3)
	{
		BoardCell cell = myBoard.getBoardCell(0,3);
		Set<BoardCell> testList = myBoard.getAdjList(cell);
		assertTrue(testList.contains(myBoard.getBoardCell(0, 2)));
		assertTrue(testList.contains(myBoard.getBoardCell(1, 3)));
		assertEquals(2, testList.size());
	}

	@Test
	public void testAdjacency3() //test (3,0)
	{
		BoardCell cell = myBoard.getBoardCell(3,0);
		Set<BoardCell> testList = myBoard.getAdjList(cell);
		assertTrue(testList.contains(myBoard.getBoardCell(2, 0)));
		assertTrue(testList.contains(myBoard.getBoardCell(3, 1)));
		assertEquals(2, testList.size());
	}

	@Test
	public void testAdjacency4() //test (1,1)
	{
		BoardCell cell = myBoard.getBoardCell(1,1);
		Set<BoardCell> testList = myBoard.getAdjList(cell);
		assertTrue(testList.contains(myBoard.getBoardCell(0, 1)));
		assertTrue(testList.contains(myBoard.getBoardCell(1, 2)));
		assertTrue(testList.contains(myBoard.getBoardCell(2, 1)));
		assertTrue(testList.contains(myBoard.getBoardCell(1, 0)));
		assertEquals(4, testList.size());
	}

	@Test
	public void testAdjacency5() //test (2,2)
	{
		BoardCell cell = myBoard.getBoardCell(2,2);
		Set<BoardCell> testList = myBoard.getAdjList(cell);
		assertTrue(testList.contains(myBoard.getBoardCell(1, 2)));
		assertTrue(testList.contains(myBoard.getBoardCell(2, 3)));
		assertTrue(testList.contains(myBoard.getBoardCell(3, 2)));
		assertTrue(testList.contains(myBoard.getBoardCell(2, 1)));
		assertEquals(4, testList.size());
	}


	@Test
	public void testTargets0_3() //check (0,0) with 3 moves
	{
		BoardCell cell = myBoard.getBoardCell(0, 0);
		myBoard.calcTargets(cell, 3);
		Set targets = myBoard.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(myBoard.getBoardCell(3, 0)));
		assertTrue(targets.contains(myBoard.getBoardCell(2, 1)));
		assertTrue(targets.contains(myBoard.getBoardCell(0, 1)));
		assertTrue(targets.contains(myBoard.getBoardCell(1, 2)));
		assertTrue(targets.contains(myBoard.getBoardCell(0, 3)));
		assertTrue(targets.contains(myBoard.getBoardCell(1, 0)));
	}

	@Test
	public void testTargets0_2() //check (2,2) with 4 moves
	{
		BoardCell cell = myBoard.getBoardCell(2, 2);
		myBoard.calcTargets(cell, 4);
		Set targets = myBoard.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(myBoard.getBoardCell(0, 0)));
		assertTrue(targets.contains(myBoard.getBoardCell(1, 1)));
		assertTrue(targets.contains(myBoard.getBoardCell(3, 1)));
		assertTrue(targets.contains(myBoard.getBoardCell(1, 3)));
		assertTrue(targets.contains(myBoard.getBoardCell(3, 3)));
		assertTrue(targets.contains(myBoard.getBoardCell(0, 2)));
		assertTrue(targets.contains(myBoard.getBoardCell(2, 0)));
	}

}
