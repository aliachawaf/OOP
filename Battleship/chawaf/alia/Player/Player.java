package chawaf.alia.Player;

import java.util.ArrayList;
import java.util.List;

import chawaf.alia.Coord;
import chawaf.alia.Display;
import chawaf.alia.Ship;

public abstract class Player {

	protected List<Ship> playerShips;
	protected Display boardGame;
	protected Display boardAttack;

	// constructor
	public Player(int gridSize) {
		this.playerShips = new ArrayList<Ship>();
		this.boardGame = new Display(gridSize);
		this.boardAttack = new Display(gridSize);
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

		for (Ship s : this.getPlayerShips()){
			if (!(s.getCoordHit().isEmpty()) && !(s.isDestroyed())) {

				list.add(s);
			}
		}

		return list;
	}

	public boolean isAnyoneHit(Coord missileCoord) {

		// return true if one of player's ships is hit by missile
		boolean hit = false;
	
		for (Ship s : this.getPlayerShips()){
			if (s.isHit(missileCoord)) {
				hit = true;
			}
		}

		return hit;
	}

	public List<Ship> listShipDestroyed() {

		List<Ship> list = new ArrayList<Ship>();
		
		for (Ship s : this.getPlayerShips()){
			if (s.isDestroyed()){
				list.add(s);
			}
		}

		return list;
	}

	public List<Coord> listCoordTaken() {

		/*
		 * for each ship, we add all his coord in the list of Coord taken
		 * returned
		 */

		List<Coord> list = new ArrayList<Coord>();

		for(Ship s : this.getPlayerShips()){
			list.addAll(s.shipListCoord());
		}
		
		return list;
	}
	
	public void clearCoordHitAllShips(){
		
		for (Ship s : this.getPlayerShips()){
			s.getCoordHit().clear();
		}
	}
}
