package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {

	private String name;

	protected int row;
	protected int column;
	protected Set<Card> hand;
	protected boolean isHuman;
	
	// handle convert to actual color later
	private String color;

	public Set<Card> getHand() {
		return hand;
	}


	
	public Player() {
		isHuman = false;
		hand = new HashSet<Card>();

	};

	public Player(String name, String color, int row, int col) {
		this.row = row;
		this.name = name;
		this.color = color;
		this.column = col;
		isHuman = false;
		
		hand = new HashSet<Card>();
	}
	
	// getters and setters
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

	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}

	public void draw(Graphics g) {
		int scale = BoardCell.SCALE_FACTOR;
		Color c = Color.BLACK;


	    try {
			Field field = Class.forName("java.awt.Color").getField(color.toLowerCase());
			c = (Color)field.get(null);
		} catch (Exception e) {
			System.err.println("Wrong Color Format: " + color);;
		}
	    
	    //color the actual circle and border
	    g.setColor(c);
		g.fillOval(column*scale, row*scale, scale, scale);
		g.setColor(Color.BLACK);
		g.drawOval(column*scale, row*scale, scale, scale);
	}



	public boolean isHuman() {
		return isHuman;
	}



	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}



	public void move(BoardCell newLoc) {
		row = newLoc.getRow();
		column = newLoc.getColumn();
	}

	
//	public Card disproveSuggestion(suggestion) {
//
//	}

}

