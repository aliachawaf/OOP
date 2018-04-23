
public class Coord {

	public char column;
	public int line;
	
	//constructors
	public Coord(char column, int line) {
		this.column = column;
		this.line = line;
	}

	public Coord(String coord){
		this.column = coord.charAt(0);
		this.line = Integer.parseInt(coord.substring(1));
	}

	//getters & setters
	public char getColumn() {
		return column;
	}

	public void setColumn(char column) {
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}
	
	//methods
	public boolean CompareColumn (Coord c){
		if ( Character.toUpperCase(this.getColumn()) == Character.toUpperCase(c.getColumn()) ){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean CompareLine (Coord c){
		if (this.getLine() == c.getLine()){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean CompareCoord (Coord c){
		
		if (this.CompareColumn(c) && this.CompareLine(c)){
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	
}
