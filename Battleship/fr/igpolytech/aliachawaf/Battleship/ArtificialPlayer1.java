package fr.igpolytech.aliachawaf.Battleship;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ArtificialPlayer1 extends Player implements ArtificialIntelligence {

	private int playerNumber;
	private int mapSize;
	
	public ArtificialPlayer1(int playerNumber, int mapSize) {
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
		
		
	} 

	public void placeAllShips(List<Ship> list) {
		// TODO Auto-generated method stub
		
	}

	public Coord sendMissile() {
		// TODO Auto-generated method stub
		return null;
	}
	
}