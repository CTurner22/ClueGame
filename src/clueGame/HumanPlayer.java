package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.Vector;

public class HumanPlayer extends Player {

	public HumanPlayer(String nm, String c, int rw, int col) {
		super(nm, c, rw, col);
		isHuman = true;
	}

	public Card disproveSuggestion(Solution suggestion) {
		// todo: update to handle user input
		
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
