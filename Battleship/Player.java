import java.util.ArrayList;

public class Player {

	private int playerNumber; // 1 or 2
	private ArrayList<Ship> playerShips;
	private Display boardGame;
	private Display boardAttack;

	// constructor
	public Player(int playerNumber, ArrayList<Ship> playerShips, int mapSize) {
		this.playerNumber = playerNumber;
		this.playerShips = playerShips;
		this.boardGame = new Display(mapSize);
		this.boardAttack = new Display(mapSize);
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

	public Display getBoardGame() {
		return boardGame;
	}

	public void setBoardGame(Display boardGame) {
		this.boardGame = boardGame;
	}

	public Display getBoardAttack() {
		return boardAttack;
	}

	public void setBoardAttack(Display boardAttack) {
		this.boardAttack = boardAttack;
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

	public boolean shipsAreHit(Coord missileCoord) {

		// return true if one of player's ships is hit by missile

		boolean hit = false;

		// for each ship, we see if it is hit by the missile
		for (int i = 0; i < this.getPlayerShips().size(); i++) {

			if (this.getPlayerShips().get(i).isHit(missileCoord)) {
				hit = true;
			}
		}
		return hit;
	}

	public boolean shipsAreDestroyed(Coord missileCoord) {

		// return true if one of the ships is destroyed by the missile
		boolean destroyed = false;

		for (int i = 0; i < this.getPlayerShips().size(); i++) {
			// if not already destroyed
			if (!(this.getPlayerShips().get(i).isDestroyed())) {
				// but hit by missile
				if (this.getPlayerShips().get(i).isHit(missileCoord)) {
					// and now destroyed because of this missile
					if (this.getPlayerShips().get(i).isDestroyed()) {
						destroyed = true;
					}
				}
			}
		}
		return destroyed;
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