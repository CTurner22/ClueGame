package clueGame;

import java.util.HashSet;

public class Card {


	private String cardName;
	private CardType type;
	
	public Card(String name, CardType type) {
		this.cardName = name;
		this.type = type;
	}
	public String getCardName() {
		return this.cardName;
	}

	public boolean equals(Object obj) {

	}
}
