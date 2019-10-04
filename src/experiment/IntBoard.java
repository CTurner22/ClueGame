package experiment;

import java.util.Map;
import java.util.Set;

public class IntBoard {

	private Map<BoardCell, Set<BoardCell>> adjacencies;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	
	IntBoard() {
		calcAdjacencies();
	}
	
	void calcAdjacencies() {
		
	}
	
	Set<BoardCell> getAdjList(){
		return null;
	}
	
	void calcTargets(BoardCell startCell, int pathLength){
		
	}
	
	Set<BoardCell> getTargets(){
		return null;
	}
	
	
	
}
