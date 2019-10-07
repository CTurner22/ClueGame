package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.border.Border;

/*
 * IntBoard
 * Casey Turner, Murat Tuter
 * 
 */
public class IntBoard {

	private Map<BoardCell, Set<BoardCell>> adjacencies;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	
	public IntBoard() {
		// set up grid
		grid = new BoardCell[4][4];
		
		for (int i =0; i < grid.length; i++) {
			for (int j=0; j < grid[0].length; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
		
		// calculate adj.
		calcAdjacencies();
	}
	
	void calcAdjacencies() {
		adjacencies = new HashMap<BoardCell, Set<BoardCell>>();
		
		// loop through each spot in the grid and get adj cells 
		for (int i=0; i < grid.length; i++) {
			for (int j=0; j < grid[0].length; j++) {
				
				BoardCell loc = grid[i][j];
				
				Set<BoardCell> temp = new HashSet<BoardCell>();
				
				// make sure its a valid location
				tryInsert(i-1, j, temp);
				tryInsert(i+1, j, temp);
				tryInsert(i, j-1, temp);
				tryInsert(i, j+1, temp);

				
				// save it
				adjacencies.put(loc, temp);
			}
		}
		
	}
	
	// helper for catching out of bounds exceptions
	public void tryInsert(int i, int j, Set<BoardCell> temp) {
		try {
			temp.add(grid[i][j]);
		} catch (ArrayIndexOutOfBoundsException e) {}
	}
	
	public Set<BoardCell> getAdjList(BoardCell startCell){
		return adjacencies.get(startCell);
	}
	
	public void calcTargets(BoardCell startCell, int pathLength){
		visited = new HashSet<BoardCell>();
		visited.add(startCell);
		
		// kick off recursive algo
		findTargets(startCell, pathLength);
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
			
			if (pathLength == 1) {
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
	
	public Set<BoardCell> getTargets(){
		return targets;
	}

	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}
	
	
	
}
