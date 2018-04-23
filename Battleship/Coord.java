
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
		if ( Character.toUpperCase(this.column) == Character.toUpperCase(c.getColumn()) ){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean CompareLine (Coord c){
		if (this.line == c.getLine()){
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
	
	//checking
	public boolean checkColumn(){
		
		boolean check = false;
		
		for (char i='A'; i<='J'; i++){
			if (Character.toUpperCase(this.column) == i){
				check = true;
			}
		}
		return check;
	}
	
	public boolean checkLine(){
		
		boolean check = false;
		
		for (int i=1; i<=10; i++){
			if (this.line == i){
				check = true;
			}
		}
		return check;
	}
	
	public boolean checkCoord(){
		
		return (checkColumn() && checkLine() );
	}
	
	
	
}
