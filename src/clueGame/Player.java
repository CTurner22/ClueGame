package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.util.ArrayList;
import java.util.Random;

public class Player {

	private String name;

	private int row;
	private int column;

	// handle convert to actual color later
	private String color;
	


	public Player(String name, String color, int row, int col) {
		this.row = row;
		this.name = name;
		this.color = color;
		this.column = col;
	
	}
	
//	public Card disproveSuggestion(suggestion) {
//
//	}


	// getters
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



}

