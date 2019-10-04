package experiment;

import java.util.Map;
import java.util.Set;

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
	
	void calcTargets(BoardCell startCell, int pathLength){
		
	}
	
	Set<BoardCell> getTargets(){
		return null;
	}

	public BoardCell getCell(int i, int j) {
		return null;
	}
	
	
	
}
