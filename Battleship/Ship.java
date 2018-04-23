import java.util.ArrayList;

public class Ship {
	
	private String name;
	private int size;
	private Coord startCoord;
	private Coord endCoord;
	private ArrayList<Coord> coordHit;
	
	//constructor
	public Ship(String name, int size, Coord startCoord, Coord endCoord, ArrayList<Coord> coordHit) {
		super();
		this.name = name;
		this.size = size;
		this.startCoord = startCoord;
		this.endCoord = endCoord;
		this.coordHit = coordHit;
	}


	//getters & setters
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Coord getStartCoord() {
		return startCoord;
	}

	public void setStartCoord(Coord startCoord) {
		this.startCoord = startCoord;
	}

	public Coord getEndCoord() {
		return endCoord;
	}

	public void setEndCoord(Coord endCoord) {
		this.endCoord = endCoord;
	}

	public ArrayList<Coord> getCoordHit() {
		return coordHit;
	}

	public void setCoordHit(ArrayList<Coord> coordHit) {
		this.coordHit = coordHit;
	}


	//methods
	public boolean isVertical(){
		//ship in vertical if same column from the beginning to the end
		if (this.startCoord.CompareColumn(this.endCoord)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isHit(Coord missileCoord){
		
		boolean hit = false;
		Coord shipCoord = this.startCoord;
		
		//if ship in vertical, comparison is done line by line
		if (isVertical()){
			
			for (int i=this.startCoord.getLine(); i<= this.endCoord.getLine(); i++){
					
				if (shipCoord.CompareCoord(missileCoord)){
						hit = true;
						this.coordHit.add(missileCoord);
					}
				shipCoord.setLine(i+1);	//next line
			}	
		} 
		//if not in vertical, comparison is done column by column
		else {
			for (char i=this.startCoord.getColumn(); i<= this.endCoord.getColumn(); i++){
					if (shipCoord.CompareCoord(missileCoord)){
						hit = true;
						this.coordHit.add(missileCoord);
					}
					shipCoord.setColumn(i++); //next column
			}	
		}
		return hit;
	}
	
	public boolean isDestroyed(){
		//ship has sunk when the number of coord hit is equal to his size
		if (this.coordHit.size() == this.size){
			return true;
		}
		else {
			return false;
		}
	}

	//override
	public String toString(){
		return "bateau " + this.name + " de taille " + this.size + ", touché " + this.coordHit.size() + " fois, "
				+ "detruit : " + this.isDestroyed();
	}
	

}
