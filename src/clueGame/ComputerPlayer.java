package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

public class ComputerPlayer extends Player {

	private boolean isHuman = false;
	private Board board = Board.getInstance();
	private  Vector<BoardCell> visited;
//	Guess accusation = null;
	
	
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
		
		visited = new Vector<BoardCell>(); 

		//this.lastRoom = '#'; //Make it so there is no last room to start.
		//The computer starts out not knowing any of the possible cards.
	}


	public ComputerPlayer() {
		visited = new Vector<BoardCell>(); 
	}


	public BoardCell pickLocation(Set<BoardCell> targets) {
		manageVisited();
		
		BoardCell pick = null;
		Vector<BoardCell> unVisitedPick = new Vector<BoardCell>();

		Random random = new Random();
		int randElement = random.nextInt(targets.size()-1);
		int i =0;
		
		for(BoardCell target : targets) {

			if(target.isRoom() && !visited.contains(target)) {
				visited.add(target);
				return target;
			}
			
			if(i == randElement) {
				pick = target;
			}
			
			if(!visited.contains(target)) {
				unVisitedPick.add(target);
			}
			i++;
		}
		
		if (!unVisitedPick.isEmpty()) {
			pick = unVisitedPick.get(random.nextInt(unVisitedPick.size()));
		}
			
		visited.add(pick);
		return pick;
	}


	private void manageVisited() {
		while(visited.size() > 8) {
			visited.remove(0);
		}
	}


	public void addToVisited(BoardCell cellAt) {
		// TODO Auto-generated method stub
		
	}


//	
//	public BoardCell pickLocation(Set<BoardCell> targets) {
//
//
//	}
//	
//	public Guess makeAccusation() {
//
//	}
//	
//
//	public Guess createSuggestion() {
//		
//
//		
//	}
	



}
