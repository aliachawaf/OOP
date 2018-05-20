package chawaf.alia.Player;

import java.util.ArrayList;
import java.util.List;

import chawaf.alia.Core.Coord;
import chawaf.alia.Core.Display;
import chawaf.alia.Core.Ship;

public abstract class Player {

	protected List<Ship> playerShips;
	protected Display gridGame;
	protected Display gridAttack;

	// constructor
	public Player(int gridSize) {
		this.playerShips = new ArrayList<Ship>();
		this.gridGame = new Display(gridSize);
		this.gridAttack = new Display(gridSize);
	}

	// getters & setters
	public List<Ship> getPlayerShips() {
		return playerShips;
	}

	public void setPlayerShips(List<Ship> playerShips) {
		this.playerShips = playerShips;
	}

	public Display getGridGame() {
		return gridGame;
	}

	public void setGridGame(Display gridGame) {
		this.gridGame = gridGame;
	}

	public Display getGridAttack() {
		return gridAttack;
	}

	public void setGridAttack(Display gridAttack) {
		this.gridAttack = gridAttack;
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