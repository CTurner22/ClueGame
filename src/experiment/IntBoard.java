package experiment;

import java.util.Map;
import java.util.Set;

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
		calcAdjacencies();
	}
	
	void calcAdjacencies() {
		
	}
	
	public Set<BoardCell> getAdjList(BoardCell startCell){
		return null;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength){
		
	}
	
	public Set<BoardCell> getTargets(){
		return null;
	}

	public BoardCell getCell(int i, int j) {
		return null;
	}
	
	
	
}
