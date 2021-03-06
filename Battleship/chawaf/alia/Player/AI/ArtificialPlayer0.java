package chawaf.alia.Player.AI;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import chawaf.alia.Core.Coord;
import chawaf.alia.Core.Ship;
import chawaf.alia.Player.Player;

public class ArtificialPlayer0 extends Player implements ArtificialIntelligence {

	protected int gridSize;

	public ArtificialPlayer0(int gridSize) {
		super(gridSize);
		this.gridSize = gridSize;
	}

	// getters & setters
	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	// methods
	public Coord choseOneCoord() {
		// chose randomly a column and a line
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String columnsGrid = alphabet.substring(0, this.gridSize);
		Random random = new Random();
		char randomColumn = columnsGrid.charAt(random.nextInt(columnsGrid.length()));
		
		int randomLine = ThreadLocalRandom.current().nextInt(1, this.gridSize + 1);

		return new Coord(randomColumn, randomLine, this.gridSize);
	}
	
	public void placeOneShip(Ship ship) {

		// ship is placed randomly on board game

		boolean checkCoordIsFree;
		Coord startCoord;

		int randomDirection;
		boolean checkEndCoord;
		char column;

		Coord endCoord = new Coord(this.gridSize);

		do {
			
			do {
				/* random start coord */
				startCoord = this.choseOneCoord();

				/* check this coord is free */
				ship.setStartCoord(startCoord);
				ship.setEndCoord(startCoord);
				checkCoordIsFree = ship.checkPlaceIsFree(this.listCoordTaken());

			} while (!(checkCoordIsFree));
			
			/* to avoid losing startCoord value */
			Coord start = new Coord(this.gridSize);
			start.setColumn(startCoord.getColumn());
			start.setLine(startCoord.getLine());
			
			do {

				/*
				 * random direction is chosen 
				 * 1 : left to right 
				 * 2 : right to left 
				 * 3 : top to bottom 
				 * 4 : bottom to top
				 */

				randomDirection = ThreadLocalRandom.current().nextInt(1, 5);

				if (randomDirection == 1) {
					/* increment column by ship's size */
					column = start.getColumn();
					for (int o = 1; o < ship.getSize(); o++) {
						column++;
					}
					/* set up endCoord according to the randomDirection */
					endCoord.setColumn(column);
					endCoord.setLine(start.getLine());

				} else if (randomDirection == 2) {
					/* decrement column by ship's size */
					column = start.getColumn();
					for (int l = 1; l < ship.getSize(); l++) {
						column--;
					}
					endCoord.setColumn(column);
					endCoord.setLine(start.getLine());

				} else if (randomDirection == 3) {
					endCoord.setColumn(start.getColumn());
					endCoord.setLine(start.getLine() + ship.getSize() - 1);

				} else if (randomDirection == 4) {
					endCoord.setColumn(start.getColumn());
					endCoord.setLine(start.getLine() - ship.getSize() + 1);
				}

				checkEndCoord = endCoord.checkCoord();

			} while (!(checkEndCoord));

			startCoord.putCoordInOrder(endCoord);

			ship.setStartCoord(startCoord);
			ship.setEndCoord(endCoord);

			/*
			 * If we have to pass again in while, and start was interchanged
			 * with end, we give start coord its initial value
			 */
			startCoord = start;

		} while (!(ship.checkPlaceIsFree(this.listCoordTaken())));

		this.getPlayerShips().add(ship);
	}

	public void placeAllShips(List<Ship> list) {
		
		for (Ship s : list){
			placeOneShip(s);
		}
	}

	public Coord sendMissile() {

		return this.choseOneCoord();
	}

	public String toString() {
		return "Beginner AI";
	}
}
