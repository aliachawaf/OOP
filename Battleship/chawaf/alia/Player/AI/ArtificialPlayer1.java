package chawaf.alia.Player.AI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import chawaf.alia.Core.Coord;
import chawaf.alia.Core.Ship;
import chawaf.alia.Player.Player;

public class ArtificialPlayer1 extends Player implements ArtificialIntelligence {

	private int gridSize;
	private List<Coord> listCoordMissileSent;

	public ArtificialPlayer1(int gridSize) {
		super(gridSize);
		this.gridSize = gridSize;
		this.listCoordMissileSent = new ArrayList<Coord>();
	}

	// getters & setters
	public List<Coord> getListCoordMissileSent() {
		return listCoordMissileSent;
	}

	public int getGridSize() {
		return gridSize;
	}

	//methods
	public Coord choseOneCoord() {
		// chose randomly a column and a line
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String columnsBoard = alphabet.substring(0, this.gridSize);
		Random random = new Random();
		char randomColumn = columnsBoard.charAt(random.nextInt(columnsBoard
				.length()));

		int randomLine = ThreadLocalRandom.current().nextInt(1,
				this.gridSize + 1);

		return new Coord(randomColumn, randomLine, this.gridSize);
	}

	public void placeOneShip(Ship s) {

		// ship is placed randomly on board game

		boolean checkCoordIsFree;
		Coord startCoord;

		

		int randomDirection;
		boolean checkEndCoord;
		char column;

		Coord start = new Coord(this.gridSize);
		Coord endCoord = new Coord(this.gridSize);


		do {

			do {
				/* random start coord */
				startCoord = this.choseOneCoord();

				/* check this coord is free */
				s.setStartCoord(startCoord);
				s.setEndCoord(startCoord);
				checkCoordIsFree = s.checkPlaceIsFree(this.listCoordTaken());

			} while (!(checkCoordIsFree));
						
			start.setColumn(startCoord.getColumn());
			start.setLine(startCoord.getLine());
			
			do {

				/*
				 * random direction is chosen 1 : left to right 2 : right to
				 * left 3 : top to bottom 4 : bottom to top
				 */

				randomDirection = ThreadLocalRandom.current().nextInt(1, 5);

				if (randomDirection == 1) {
					/* increment column by ship's size */
					column = start.getColumn();
					for (int o = 1; o < s.getSize(); o++) {
						column++;
					}
					endCoord.setColumn(column);
					endCoord.setLine(start.getLine());

				} else if (randomDirection == 2) {
					/* decrement column by ship's size */
					column = start.getColumn();
					for (int l = 1; l < s.getSize(); l++) {
						column--;
					}
					endCoord.setColumn(column);
					endCoord.setLine(start.getLine());

				} else if (randomDirection == 3) {
					endCoord.setColumn(start.getColumn());
					endCoord.setLine(start.getLine() + s.getSize() - 1);

				} else if (randomDirection == 4) {
					endCoord.setColumn(start.getColumn());
					endCoord.setLine(start.getLine() - s.getSize() + 1);
				}

				checkEndCoord = endCoord.checkCoord();

			} while (!(checkEndCoord));

			startCoord.putCoordInOrder(endCoord);

			s.setStartCoord(startCoord);
			s.setEndCoord(endCoord);

			/*
			 * If we have to pass again in while, and start was interchanged
			 * with end, we give start coord its initial value
			 */
			startCoord = start;

		} while (!(s.checkPlaceIsFree(this.listCoordTaken())));

		this.getPlayerShips().add(s);
	}

	public void placeAllShips(List<Ship> list) {
		
		for (Ship s : list){
			placeOneShip(s);
		}
	}

	public Coord sendMissile() {

		// chose a missileCoord randomly which was never sent before
		Coord missileCoord;

		do {
			missileCoord = this.choseOneCoord();
		} while (this.listCoordMissileSent.contains(missileCoord));

		this.listCoordMissileSent.add(missileCoord);
		return missileCoord;
	}


	public Coord sendMissileAroundShipHit(Coord hit) {

		Coord missileCoord = new Coord(this.gridSize);
		int randomDirection;
		char column;
		boolean checkMissileCoord;

		do {
			/*
			 * random direction is chosen 1 : left to right 2 : right to left 3
			 * : top to bottom 4 : bottom to top
			 */

			randomDirection = ThreadLocalRandom.current().nextInt(1, 9);

			if (randomDirection == 1) {
				column = hit.getColumn();
				column++;
				missileCoord.setColumn(column);
				missileCoord.setLine(hit.getLine());

			} else if (randomDirection == 2) {
				column = hit.getColumn();
				column--;
				missileCoord.setColumn(column);
				missileCoord.setLine(hit.getLine());

			} else if (randomDirection == 3) {
				missileCoord.setColumn(hit.getColumn());
				missileCoord.setLine(hit.getLine() + 1);

			} else if (randomDirection == 4) {
				missileCoord.setColumn(hit.getColumn());
				missileCoord.setLine(hit.getLine() - 1);
			
			} else if (randomDirection == 5) {
				column = hit.getColumn();
				column++;
				column++;
				missileCoord.setColumn(column);
				missileCoord.setLine(hit.getLine());

			} else if (randomDirection == 6) {
				column = hit.getColumn();
				column--;
				column--;
				missileCoord.setColumn(column);
				missileCoord.setLine(hit.getLine());

			} else if (randomDirection == 7) {
				missileCoord.setColumn(hit.getColumn());
				missileCoord.setLine(hit.getLine() + 2);

			} else if (randomDirection == 8) {
				missileCoord.setColumn(hit.getColumn());
				missileCoord.setLine(hit.getLine() - 2);
			} 
			checkMissileCoord = missileCoord.checkCoord();

		} while (!(checkMissileCoord) ); //|| this.listCoordMissileSent.contains(missileCoord));
		
		this.listCoordMissileSent.add(missileCoord);
		return missileCoord;
	}

	
	public String toString() {
		return "Medium AI";
	}

}