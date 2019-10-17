package clueGame;

/*
 * Board Cell
 * Casey Turner, Murat Tuter
 * 
 */
public class BoardCell {

	public int row;
	public int column;
	public char room;
	public DoorDirection door;

	
	
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

}
