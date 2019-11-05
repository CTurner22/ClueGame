package clueGame;

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

	
	public BoardCell(int r, int c) {
		row = r;
		column = c;
		door = DoorDirection.NA;
	}
	
	public BoardCell(int r, int c, char rm) {
		row = r;
		column = c;
		room = rm;
		door = DoorDirection.NA;
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

}
