package fr.igpolytech.aliachawaf.Battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ArtificialPlayer2 extends Player implements ArtificialIntelligence {

	private int mapSize;
	private List<Coord> listCoordMissileSent;

	public ArtificialPlayer2(int mapSize) {
		super(mapSize);
		this.mapSize = mapSize;
		this.listCoordMissileSent = new ArrayList<Coord>();
	}

	// getters & setters
	public List<Coord> getListCoordMissileSent() {
		return listCoordMissileSent;
	}

	public void setListCoordMissileSent(List<Coord> listCoordMissileSent) {
		this.listCoordMissileSent = listCoordMissileSent;
	}

	public int getMapSize() {
		return mapSize;
	}

	public Coord choseOneCoord() {
		// chose randomly a column and a line
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String columnsBoard = alphabet.substring(0, this.mapSize);
		Random random = new Random();
		char randomColumn = columnsBoard.charAt(random.nextInt(columnsBoard
				.length()));

		int randomLine = ThreadLocalRandom.current().nextInt(1,
				this.mapSize + 1);

		return new Coord(randomColumn, randomLine, this.mapSize);
	}

	public void placeOneShip(Ship s) {

		// ship is placed randomly on board game

		boolean checkCoordIsFree;
		Coord startCoord;

		int randomDirection;
		boolean checkEndCoord;
		char column;

		Coord endCoord = new Coord(this.mapSize);
		Coord start = new Coord(this.mapSize);

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

		} while (!checkDontTouchAShip(s)
				|| !s.checkPlaceIsFree(this.listCoordTaken()));

		this.getPlayerShips().add(s);
		this.getBoardGame().updateBoard(s);
		System.out.println(this.getBoardGame());
	}

	public void placeAllShips(List<Ship> list) {

		for (Ship s : list) {
			placeOneShip(s);
		}
	}

	// public Coord sendMissile() {
	//
	// // chose a missileCoord randomly which was never sent before
	// Coord missileCoord;
	//
	// do {
	// missileCoord = this.choseOneCoord();
	// } while (this.listCoordMissileSent.contains(missileCoord));
	//
	// this.listCoordMissileSent.add(missileCoord);
	// return missileCoord;
	// }

	public Coord sendMissile() {

		/* Send missiles only on 'white' coord of the board (like chess) */
		Coord missileCoord = new Coord(this.mapSize);
		boolean lineParity;
		boolean columnParity;
		boolean checkLineColumnSameParity;
		int column;
		
		do {
			//missile chosen randomly until its line and column have the same parity
			missileCoord = this.choseOneCoord();

			lineParity = (missileCoord.getLine() % 2 == 0);

			column = (int) Character.toLowerCase(missileCoord.getColumn()) - 96;
			System.out.println("int column random : " + column);

			columnParity = (column % 2 == 0);

			checkLineColumnSameParity = (lineParity & columnParity)
					|| (!lineParity & !columnParity);

			System.out.println("missile : " + missileCoord + "same parity :"
					+ checkLineColumnSameParity);

		} while (this.listCoordMissileSent.contains(missileCoord)
				|| !checkLineColumnSameParity);

		this.listCoordMissileSent.add(missileCoord);
		return missileCoord;

	}

	public Coord sendMissileAroundShipHit(Coord hit) {

		Coord missileCoord = new Coord(this.mapSize);
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

		} while (!(checkMissileCoord)
				|| this.listCoordMissileSent.contains(missileCoord));

		this.listCoordMissileSent.add(missileCoord);
		return missileCoord;
	}

	public boolean checkDontTouchAShip(Ship s) {

		boolean check = true;
		Coord c = new Coord(this.mapSize);
		char column;

		if (s.isVertical()) {

			c.setColumn(s.getStartCoord().getColumn());

			/* check if the coord on the top of the ship is free */
			c.setLine(s.getStartCoord().getLine() - 1);

			if (c.checkCoord() && this.listCoordTaken().contains(c)) {
				check = false;
			}

			/* check if the coord on the bottom of the ship is free */
			c.setLine(s.getEndCoord().getLine() + 1);

			if (c.checkCoord() && this.listCoordTaken().contains(c)) {
				check = false;
			}

			/* check if coords on the right side of the ship are free */
			column = s.getStartCoord().getColumn();
			column--;
			c.setColumn(column);

			for (Coord shipCoord : s.shipListCoord()) {

				c.setLine(shipCoord.getLine());

				if (c.checkCoord() && this.listCoordTaken().contains(c)) {
					check = false;
				}
			}

			/* check if coords on the left side of the ship are free */
			column = s.getEndCoord().getColumn();
			column++;
			c.setColumn(column);

			for (Coord shipCoord : s.shipListCoord()) {

				c.setLine(shipCoord.getLine());

				if (c.checkCoord() && this.listCoordTaken().contains(c)) {
					check = false;
				}
			}

		} else {

			c.setLine(s.getStartCoord().getLine());

			/* check if the coord on the left of the ship is free */
			column = s.getStartCoord().getColumn();
			column--;
			c.setColumn(column);

			if (c.checkCoord() && this.listCoordTaken().contains(c)) {
				check = false;
			}

			/* check if the coord on the right of the ship is free */
			column = s.getEndCoord().getColumn();
			column++;
			c.setColumn(column);

			if (c.checkCoord() && this.listCoordTaken().contains(c)) {
				check = false;
			}

			/* check if coords on the top side of the ship are free */
			c.setLine(s.getStartCoord().getLine() - 1);

			for (Coord shipCoord : s.shipListCoord()) {

				c.setColumn(shipCoord.getColumn());

				if (c.checkCoord() && this.listCoordTaken().contains(c)) {
					check = false;
				}
			}

			/* check if coords on the bottom side of the ship are free */
			c.setLine(s.getStartCoord().getLine() + 1);

			for (Coord shipCoord : s.shipListCoord()) {

				c.setColumn(shipCoord.getColumn());

				if (c.checkCoord() && this.listCoordTaken().contains(c)) {
					check = false;
				}
			}
		}

		return check;
	}

	@Override
	public String toString() {
		return "Hard AI";
	}

}