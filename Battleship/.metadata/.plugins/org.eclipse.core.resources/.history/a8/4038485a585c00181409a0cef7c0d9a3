package chawaf.alia;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	private String name;
	private int size;
	private Coord startCoord;
	private Coord endCoord;
	private List<Coord> coordHit;

	// constructors
	public Ship(String name, int size) {
		this.name = name;
		this.size = size;
		this.coordHit = new ArrayList<Coord>();
	}

	// getters & setters
	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
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

	public List<Coord> getCoordHit() {
		return coordHit;
	}

	
	// methods
	public boolean isVertical() {
		// ship in vertical if same column from the beginning to the end
		if (this.startCoord.compareColumn(this.endCoord)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isHit(Coord missileCoord) {

		boolean hit = false;
		
		for (Coord c : this.shipListCoord()){
			if (c.compareCoord(missileCoord)) {
				hit = true;
				// update list of coord hit only if not already contained 
				if (!(this.coordHit.contains(missileCoord))) {
					this.coordHit.add(missileCoord);
				}
			}
		}
		
		return hit;
	}

	public boolean isDestroyed() {
		// ship has sunk when the number of coord hit is equal to its size
		if (this.coordHit.size() == this.size) {
			return true;
		} else {
			return false;
		}
	}

	public List<Coord> shipListCoord() {

		// returns the list of all the coord which compose the ship

		List<Coord> list = new ArrayList<Coord>();

		// if ship in vertical, same letter of column but different lines
		if (isVertical()) {

			for (int i = this.startCoord.getLine(); i <= this.endCoord
					.getLine(); i++) {

				Coord c = new Coord(this.startCoord.getColumn(), i,
						this.startCoord.getGridSize());
				list.add(c);
			}
		} else {
			for (char i = this.startCoord.getColumn(); i <= this.endCoord
					.getColumn(); i++) {

				Coord c = new Coord(i, this.startCoord.getLine(),
						this.startCoord.getGridSize());
				list.add(c);
			}
		}
		return list;
	}
	

	// checking
	public boolean checkNotDiagonal() {
		boolean check = false;

		if (isVertical()) {
			check = true;
		} else {
			if (this.startCoord.compareLine(this.endCoord)) {
				check = true;
			}
		}
		return check;
	}

	public boolean checkCoordsMatchWithSize() {
		/* checks if the number of ship's coord matches with its size */

		if (this.shipListCoord().size() == this.size) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkPlaceIsFree(List<Coord> listCoordTaken) {

		/*
		 * So for each coord of our ship, we check if it is contained but the
		 * list of coord already taken. If yes, place is not free
		 */

		boolean check = true;

		for (Coord c : this.shipListCoord()){
			if (listCoordTaken.contains(c)) {
				check = false;
			}
		}
		
		return check;
	}

	@Override
	public String toString() {
		return this.name + " (size " + this.size + ")";
	}

}
