package fr.igpolytech.aliachawaf.Battleship;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ArtificialPlayer0 extends Player implements ArtificialIntelligence {

	protected int playerNumber;
	protected int mapSize;

	public ArtificialPlayer0(int playerNumber, int mapSize) {
		super(mapSize);
		this.playerNumber = playerNumber;
		this.mapSize = mapSize;
	}

	// getters & setters
	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public int getMapSize() {
		return mapSize;
	}

	public void setMapSize(int mapSize) {
		this.mapSize = mapSize;
	}

	// methods

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

		do {
			/* random start coord */
			startCoord = this.choseOneCoord();

			/* check this coord is free */ 
			s.setStartCoord(startCoord);
			s.setEndCoord(startCoord);
			checkCoordIsFree = s.checkPlaceIsFree(this.listCoordTaken());
			
		} while (!(checkCoordIsFree));

		
		

		int directionRandom;
		boolean checkEndCoord;
		char column;
		
		Coord endCoord = new Coord(this.mapSize);

		Coord start = new Coord(this.mapSize);
		start.setColumn(startCoord.getColumn());
		start.setLine(startCoord.getLine());

		do {

			do {

				/*
				 * random direction is chosen 1 : left to right 2 : right to
				 * left 3 : top to bottom 4 : bottom to top
				 */

				directionRandom = ThreadLocalRandom.current().nextInt(1, 5);

				if (directionRandom == 1) {
					/* increment column by ship's size */
					column = start.getColumn();
					for (int o = 1; o < s.getSize(); o++) {
						column++;
					}
					endCoord.setColumn(column);
					endCoord.setLine(start.getLine());

				} else if (directionRandom == 2) {
					/* decrement column by ship's size */
					column = start.getColumn();
					for (int l = 1; l < s.getSize(); l++) {
						column--;
					}
					endCoord.setColumn(column);
					endCoord.setLine(start.getLine());

				} else if (directionRandom == 3) {
					endCoord.setColumn(start.getColumn());
					endCoord.setLine(start.getLine() + s.getSize() - 1);

				} else if (directionRandom == 4) {
					endCoord.setColumn(start.getColumn());
					endCoord.setLine(start.getLine() - s.getSize() + 1);
				}

				checkEndCoord = endCoord.checkCoord();

			} while (!(checkEndCoord));
			
			startCoord.putCoordInOrder(endCoord);
			
			s.setStartCoord(startCoord);
			s.setEndCoord(endCoord);

			/* If we have to pass again in while, and start was interchanged with end, we give start coord its initial value */
			startCoord = start; 


		} while (!(s.checkPlaceIsFree(this.listCoordTaken())));

		this.getPlayerShips().add(s);
		this.getBoardGame().updateBoard(s);
		System.out.println(this.getBoardGame());
	}

	public void placeAllShips(List<Ship> list) {

		Iterator<Ship> it = list.iterator();
		
		while (it.hasNext()){
			placeOneShip(it.next());
		}
	}

	public Coord sendMissile() {

		return this.choseOneCoord();
	}
	
	public String toString(){
		return "ArtificialPlayer";
	}
}