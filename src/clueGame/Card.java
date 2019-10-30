package clueGame;

import java.util.HashSet;

public class Card {


	private String name;
	private CardType type;
	
	public Card(String name, CardType type) {
		this.name = name;
		this.type = type;
	}

	public CardType getType() {
		return type;
	}
	public String getName() {
		return name;
	}

}
