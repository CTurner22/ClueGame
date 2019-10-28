package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ComputerPlayer extends Player {

	private boolean isHuman = false;
	private Board board = Board.getInstance();
	Guess accusation = null;
	
	
	

	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
		this.lastRoom = '#'; //Make it so there is no last room to start.
		//The computer starts out not knowing any of the possible cards.
		
	}

	
	public BoardCell pickLocation(Set<BoardCell> targets) {


	}
	
	public Guess makeAccusation() {

	}
	

	public Guess createSuggestion() {
		

		
	}
	



	



}
