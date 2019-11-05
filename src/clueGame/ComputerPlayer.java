package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Handler;

public class ComputerPlayer extends Player {

	private boolean isHuman = false;
	private Board board = Board.getInstance();
	private  Vector<BoardCell> visited;
	private  Set<Card> roomsSeen;
	private  Set<Card> peopleSeen;
	private  Set<Card> weaponsSeen;


//	Guess accusation = null;
	
	
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
		
		visited = new Vector<BoardCell>(); 
		roomsSeen = new HashSet<Card>();
		peopleSeen = new HashSet<Card>();
		weaponsSeen = new HashSet<Card>();


		//this.lastRoom = '#'; //Make it so there is no last room to start.
		//The computer starts out not knowing any of the possible cards.
	}


	public ComputerPlayer() {
		visited = new Vector<BoardCell>();
		roomsSeen = new HashSet<Card>();
		peopleSeen = new HashSet<Card>();
		weaponsSeen = new HashSet<Card>();
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
	
	public Solution createSuggestion() {
		Vector<Card> people = new Vector<Card>();
		Vector<Card> weapons = new Vector<Card>();
		Random random = new Random();

		for(Card card: board.getDeck()) {
			switch(card.getType()){
			case PERSON:
				if(!peopleSeen.contains(card)) {
					people.add(card);
				}
				break;
			case WEAPON:
				if(!weaponsSeen.contains(card)) {
					weapons.add(card);
				}
			}
		}
		
		Card room = board.getRoomCard(board.getCellAt(row,column));
		Card wpn = weapons.size() > 0 ? weapons.get(random.nextInt(weapons.size())) : weapons.get(0);
		Card ppl = people.size() > 0 ? people.get(random.nextInt(people.size())) : people.get(0);
		
		return new Solution(ppl, room, wpn);
	}


	public void addToVisited(BoardCell cellAt) {
		visited.add(cellAt);
	}


	public void addToSeen(Card card) {
		if(card == null) {
			return;
		}
		
		switch(card.getType()){
		case PERSON:
			peopleSeen.add(card);
			break;
		case WEAPON:
			weaponsSeen.add(card);
			break;
		case ROOM:
			roomsSeen.add(card);
		}
	}


	public Card disproveSuggestion(Solution suggestion) {
		Random random = new Random();
		Vector<Card> matching = new Vector<Card>();
		Vector<Card> allCards = new Vector<Card>();

		
		allCards.add(suggestion.getCrimeScene());
		allCards.add(suggestion.getMurderer());
		allCards.add(suggestion.getMurderWeapon());

		for(Card card: allCards) {
			if(hand.contains(card)) {
				matching.add(card);
			}
		}
		
		return matching.size() > 0 ? matching.get(random.nextInt(matching.size())) : null;
	}

}
