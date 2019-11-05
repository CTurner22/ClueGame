package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {

	private String name;

	protected int row;
	protected int column;
	private Set<Card> hand;
	

	public Set<Card> getHand() {
		return hand;
	}

	// handle convert to actual color later
	private String color;
	
	public Player() {};

	public Player(String name, String color, int row, int col) {
		this.row = row;
		this.name = name;
		this.color = color;
		this.column = col;
		
		hand = new HashSet<Card>();
	}
	
	// getters
	public void addToHand(Card card) {
		hand.add(card);
	}

	public String getName() {
		return name;
	}
	public String getColor() {
		return color;
	}
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
//	public Card disproveSuggestion(suggestion) {
//
//	}

}

