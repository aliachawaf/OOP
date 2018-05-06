import java.util.ArrayList;

public class Ship {

	private String name;
	private int size;
	private Coord startCoord;
	private Coord endCoord;
	private ArrayList<Coord> coordHit;

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

	// methods
	public boolean isVertical() {
		// ship in vertical if same column from the beginning to the end
		if (this.startCoord.CompareColumn(this.endCoord)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isHit(Coord missileCoord) {

		boolean hit = false;

		for (int i = 0; i < this.shipListCoord().size(); i++) {
			if (this.shipListCoord().get(i).CompareCoord(missileCoord)) {
				hit = true;
				this.coordHit.add(missileCoord);
			}
		}
		return hit;
	}

	public boolean isDestroyed() {
		// ship has sunk when the number of coord hit is equal to his size
		if (this.coordHit.size() == this.size) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<Coord> shipListCoord() {

		// we construct a list containing the coord which compose the ship

		ArrayList<Coord> list = new ArrayList<Coord>();

		// if ship in vertical, same letter of column but different lines
		if (isVertical()) {

			for (int i = this.startCoord.getLine(); i <= this.endCoord
					.getLine(); i++) {

				Coord c = new Coord(this.startCoord.getColumn(), i,
						this.startCoord.getMapSize());
				list.add(c);
			}
		} else {
			for (char i = this.startCoord.getColumn(); i <= this.endCoord
					.getColumn(); i++) {

				Coord c = new Coord(i, this.startCoord.getLine(),
						this.startCoord.getMapSize());
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
			if (this.startCoord.CompareLine(this.endCoord)) {
				check = true;
			}
		}
		return check;
	}

	public boolean checkCoordsMatchWithSize() {
		/* checks if ship's coord correspond with its size */

		if (this.shipListCoord().size() == this.size) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkPlaceIsFree(ArrayList<Ship> list) {

		boolean check = true;

		// for each coord of this ship
		for (int i = 0; i < this.shipListCoord().size(); i++) {

			// for each ship of shipList
			for (int j = 0; j < list.size(); j++) {

				// for each coord of list's ship
				for (int k = 0; k < list.get(j).shipListCoord().size(); k++) {

					// compare coord of this ship and coord of list's ship
					if (this.shipListCoord().get(i)
							.CompareCoord(list.get(j).shipListCoord().get(k))) {
						check = false;
					}
				}
			}
		}
		return check;
	}

	// override
	public String toString() {
		return this.name + " (size " + this.size + ")";
	}
}
