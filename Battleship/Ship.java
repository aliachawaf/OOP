import java.util.ArrayList;

public class Ship {

	private String name;
	private int size;
	private Coord startCoord;
	private Coord endCoord;
	private ArrayList<Coord> coordHit;

	// constructors
	public Ship(String name, int size, Coord startCoord, Coord endCoord) {
		this.name = name;
		this.size = size;
		this.startCoord = startCoord;
		this.endCoord = endCoord;
		this.coordHit = new ArrayList<Coord>();
	}

	public Ship(String name, int size) {
		this.name = name;
		this.size = size;
		this.startCoord = new Coord("A1");
		this.endCoord = new Coord("A1");
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

	public void setStartCoord(String start) {
		Coord startCoord = new Coord(start);
		this.startCoord = startCoord;
	}

	public Coord getEndCoord() {
		return endCoord;
	}

	public void setEndCoord(String end) {
		Coord endCoord = new Coord(end);
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

		Coord shipCoord = new Coord(this.startCoord.getColumn(),
				this.startCoord.getLine());

		// if ship in vertical, comparison is done line by line
		if (isVertical()) {

			for (int i = this.startCoord.getLine(); i <= this.endCoord
					.getLine(); i++) {

				if (shipCoord.CompareCoord(missileCoord)) {
					hit = true;
					this.coordHit.add(missileCoord);
				}
				shipCoord.setLine(i + 1); // next line
			}
		}
		// if not in vertical, comparison is done column by column
		else {
			for (char i = this.startCoord.getColumn(); i <= this.endCoord
					.getColumn(); i++) {
				if (shipCoord.CompareCoord(missileCoord)) {
					hit = true;
					this.coordHit.add(missileCoord);
				}
				shipCoord.setColumn(i++); // next column
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

				Coord c = new Coord(this.startCoord.getColumn(), i);
				list.add(c);
			}
		} else {
			for (char i = this.startCoord.getColumn(); i <= this.endCoord
					.getColumn(); i++) {

				Coord c = new Coord(i, this.startCoord.getLine());
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

	public boolean checkSizeCoord() {
		/* checks if ship's coord correspond with its size */

		if (this.shipListCoord().size() == this.size) {
			return true;
		} else {
			return false;
		}
	}

	// override
	public String toString() {
		return this.name + " touché "
				+ this.coordHit.size() + " fois " + "detruit : "
				+ this.isDestroyed();
	}
}
