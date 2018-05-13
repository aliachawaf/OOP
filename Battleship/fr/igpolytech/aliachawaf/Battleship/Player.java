package fr.igpolytech.aliachawaf.Battleship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Player {

	protected List<Ship> playerShips;
	protected Display boardGame;
	protected Display boardAttack;

	// constructor
	public Player(int mapSize) {
		this.playerShips = new ArrayList<Ship>();
		this.boardGame = new Display(mapSize);
		this.boardAttack = new Display(mapSize);
	}

	// getters & setters
	public List<Ship> getPlayerShips() {
		return playerShips;
	}

	public void setPlayerShips(List<Ship> playerShips) {
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

	public List<Ship> listShipHit() {

		/*
		 * for each player's ship, we see if its list CoordHit is empty. If not,
		 * that means the ship is hit and we add it in the list returned
		 * 
		 * NB : only ships hit are returned (and not destroyed !)
		 */

		List<Ship> list = new ArrayList<Ship>();

		Iterator<Ship> it = this.playerShips.iterator();
		Ship next;
		
		while (it.hasNext()) {
			next = it.next();
			if (!(next.getCoordHit().isEmpty())
					&& !(next.isDestroyed())) {

				list.add(next);
			}
		}

		return list;
	}

	public boolean isAnyoneHit(Coord missileCoord) {

		// return true if one of player's ships is hit by missile
		boolean hit = false;

		Iterator<Ship> it = this.getPlayerShips().iterator();

		// for each ship, we see if it is hit by the missile
		while (it.hasNext()) {
			if (it.next().isHit(missileCoord)) {
				hit = true;
			}
		}

		return hit;
	}

	public List<Ship> listShipDestroyed() {

		List<Ship> list = new ArrayList<Ship>();

		Iterator<Ship> it = this.playerShips.iterator();
		Ship next;

		while (it.hasNext()) {
			next = it.next();
			if (next.isDestroyed()) {

				list.add(next);
			}
		}

		return list;
	}

	public List<Coord> listCoordTaken(){
		
		/* for each ship, we add all his coord in the list of Coord taken returned */
		
		List<Coord> list = new ArrayList<Coord>();
		
		Iterator<Ship> it = this.getPlayerShips().iterator();
		
		while (it.hasNext()){
			
			list.addAll(it.next().shipListCoord());
						
		}
		return list;
	}
}