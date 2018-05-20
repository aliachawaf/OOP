package chawaf.alia.Core;


public class Coord {

	private char column;
	private int line;
	private int gridSize;

	// constructors
	public Coord(char column, int line, int gridSize) {
		this.column = Character.toUpperCase(column);
		this.line = line;
		this.gridSize = gridSize;
	}

	public Coord(String coord, int gridSize) {
		this.column = Character.toUpperCase(coord.charAt(0));
		this.line = Integer.parseInt(coord.substring(1));
		this.gridSize = gridSize;
	}
	
	public Coord(int gridSize) {
		this.gridSize = gridSize;
	}

	// getters & setters
	public char getColumn() {
		return Character.toUpperCase(column);
	}

	public void setColumn(char column) {
		this.column = Character.toUpperCase(column);
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	// methods
	public boolean compareColumn(Coord c) {
		if (this.column == c.getColumn()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean compareLine(Coord c) {
		if (this.line == c.getLine()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean compareCoord(Coord c) {

		if (this.compareColumn(c) && this.compareLine(c)) {
			return true;
		} else {
			return false;
		}
	}

	
	public void interchangeCoord(Coord c) {

		Coord i = new Coord(this.getColumn(), this.getLine(), this.gridSize);

		this.setColumn(c.getColumn());
		this.setLine(c.getLine());

		c.setColumn(i.getColumn());
		c.setLine(i.getLine());

	}

	public void putCoordInOrder(Coord end) {

		/*
		 * if user has entered start and end coord not in the right way (from
		 * the right to the left, or from the bottom to the top), we interchange
		 * the 2 coords.
		 */

		if ((this.getColumn() > end.getColumn() && this.compareLine(end))
				|| (this.getLine() > end.getLine() && this.compareColumn(end))) {

			this.interchangeCoord(end);
		}
	}
	
	
	
	
	// checking
	public boolean checkColumn() {

		boolean check = false;

		for (char i = 'A'; i < Character
				.toUpperCase((char)(this.gridSize + 65)); i++) {
			if (this.column == i) {
				check = true;
			}
		}
		return check;
	}

	public boolean checkLine() {

		boolean check = false;

		for (int i = 1; i <= this.gridSize; i++) {
			if (this.line == i) {
				check = true;
			}
		}
		return check;
	}

	public boolean checkCoord() {

		return (checkColumn() && checkLine());
	}
	

	@Override
	public String toString() {
		return this.column + Integer.toString(this.line);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + line;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Coord))
			return false;
		Coord other = (Coord) obj;
		if (column != other.column)
			return false;
		if (line != other.line)
			return false;
		return true;
	}
	

}
