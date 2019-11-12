package clueGame;

import java.awt.Color;
import java.awt.Graphics;

/*
 * Board Cell
 * Casey Turner, Murat Tuter
 * 
 */
public class BoardCell {

	private int row;
	private int column;
	private char room;
	private DoorDirection door;
	private boolean centerRoom;
	
	public boolean isCenterRoom() {
		return centerRoom;
	}

	public void setCenterRoom(boolean centerRoom) {
		this.centerRoom = centerRoom;
	}

	public static final int SCALE_FACTOR = 35;
	
	public BoardCell(int r, int c) {
		row = r;
		column = c;
		door = DoorDirection.NA;
		centerRoom = false;
	}
	
	public BoardCell(int r, int c, char rm) {
		row = r;
		column = c;
		room = rm;
		door = DoorDirection.NA;
		centerRoom = false;
	}
	
	public void setDoor(DoorDirection d) {
		door = d;
	}

	public boolean isDoorway() {
		return door != DoorDirection.NA;
	}

	public DoorDirection getDoorDirection() {
		return door;
	}

	public char getInitial() {
		return room;
	}

	public boolean allowsEntryFrom(BoardCell loc) {
		
		switch(door) {
			case DOWN: 
				return (row == loc.row - 1);
			case UP:
				return (row == loc.row + 1);
			case RIGHT:
				return (column == loc.column - 1);
			case LEFT:
				return (column == loc.column + 1);
			default:
				return false;
		}
		
	}

	public boolean isWalkway() {
		// This should be based on CARD or OTHER for universal standard not hardcoded 'W', but currently no better way to tell difference in 
		// walkways and closets	
		return room == 'W';
	}

	public boolean isRoom() {
		return isDoorway();
	}
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void draw(Graphics g) {

		// draw grid on room type switch
		switch(room) {
		case 'W':
			g.setColor(Color.WHITE);
			g.fillRect(column*SCALE_FACTOR, row*SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);
			g.setColor(Color.BLACK);
			g.drawRect(column*SCALE_FACTOR, row*SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);
			return;
			
		case 'X':
			g.setColor(Color.PINK);
			g.fillRect(column*SCALE_FACTOR, row*SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);
			return;
			
		default:
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(column*SCALE_FACTOR, row*SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);
			drawDoor(g);
			if(centerRoom) {
				drawRoomName(g);
			}
		}
	}

	private void drawRoomName(Graphics g) {
		Board board = Board.getInstance();
		String name = board.getRoomName(room);
		
		g.setColor(Color.RED);
		g.drawString(name, column*SCALE_FACTOR, row*SCALE_FACTOR);
	}

	private void drawDoor(Graphics g) {
		
		// set starting location to entire square
		g.setColor(Color.darkGray);
		int x = column*SCALE_FACTOR,y = row*SCALE_FACTOR, width = SCALE_FACTOR,height = SCALE_FACTOR;
		
		//use switch to narrow based on direction of door
		switch (door) {
		case DOWN:
			height/=6;
			y+= (SCALE_FACTOR - height);
			break;
		case UP:
			height/=6;
			break;
		case LEFT:
			width/=6;
			break;
		case RIGHT:
			width/=6;
			x+=(SCALE_FACTOR - width);
			break;
		default:
			return;
		}
		
		//draw it
		g.fillRect(x, y, width, height);
	}

}
