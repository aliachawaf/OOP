import java.util.ArrayList;

public class Player {

	private int playerNumber; // 1 or 2
	private ArrayList<Ship> playerShips;
	private ArrayList<Ship> playerShipsPlaced;

	// constructor
	public Player(int playerNumber, ArrayList<Ship> playerShips) {
		this.playerNumber = playerNumber;
		this.playerShips = playerShips;
		this.playerShipsPlaced = new ArrayList<Ship>();
	}

	// getters & setters
	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public ArrayList<Ship> getPlayerShips() {
		return playerShips;
	}

	public void setPlayerShips(ArrayList<Ship> playerShips) {
		this.playerShips = playerShips;
	}

	public ArrayList<Ship> getPlayerShipsPlaced() {
		return playerShipsPlaced;
	}

	public void setPlayerShipsPlaced(ArrayList<Ship> playerShipsPlaced) {
		this.playerShipsPlaced = playerShipsPlaced;
	}

	// methods

	public ArrayList<Ship> listShipHit() {

		/*
		 * for each player's ship, we see if its list CoordHit is empty. If not,
		 * that means the ship is hit and we add it in the list returned
		 * 
		 * nb : only ships hit are returned (and not destroyed !)
		 */

		ArrayList<Ship> list = new ArrayList<Ship>();

		for (int i = 0; i < this.playerShips.size(); i++) {

			if (!(this.playerShips.get(i).getCoordHit().isEmpty())
					&& !(this.playerShips.get(i).isDestroyed())) {

				list.add(this.playerShips.get(i));
			}
		}
		return list;
	}

	public ArrayList<Ship> listShipDestroyed() {

		ArrayList<Ship> list = new ArrayList<Ship>();

		for (int i = 0; i < this.playerShips.size(); i++) {

			if (this.playerShips.get(i).isDestroyed()) {

				list.add(this.playerShips.get(i));
			}
		}
		return list;
	}

	// overrides
	public String toString() {
		return "player" + this.playerNumber;
	}

}