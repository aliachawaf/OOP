package fr.igpolytech.aliachawaf.Battleship;

import java.util.ArrayList;
import java.util.List;

public class ArtificialPlayer2 extends Player implements ArtificialIntelligence {

	private int playerNumber;
	private int mapSize;
	private List<Coord> listCoordMissileSent; 
	
	public ArtificialPlayer2(int playerNumber, int mapSize) {
		super(mapSize);
		this.playerNumber = playerNumber;
		this.mapSize = mapSize;
		this.listCoordMissileSent = new ArrayList<Coord>();
	}

	public Coord choseOneCoord() {
		// TODO Auto-generated method stub
		return null;
	}

	public void placeOneShip(Ship s) {
		// TODO Auto-generated method stub
		
	}

	public void placeAllShips(List<Ship> list) {
		// TODO Auto-generated method stub
		
	}

	public Coord sendMissile() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
