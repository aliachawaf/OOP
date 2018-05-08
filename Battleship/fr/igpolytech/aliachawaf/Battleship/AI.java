package fr.igpolytech.aliachawaf.Battleship;
import java.util.ArrayList;


public class AI extends Player {
	
	private int mapSize;

	public AI(int playerNumber, int mapSize) {
		super(playerNumber, mapSize);
		this.mapSize = mapSize;
	}
	
	
	// getters & setters
	public int getMapSize() {
		return mapSize;
	}

	public void setMapSize(int mapSize) {
		this.mapSize = mapSize;
	}

	// methods
/*
	public void placeShipsOnBoard(ArrayList<Ship> list){
		
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String alphabetMap = alphabet.substring(0, this.mapSize);
		
		System.out.println("alphabet : " + alphabetMap);
		
		//for each ship
		for (int i=0; i<list.size(); i++){
			
			
			
		}
		
	}*/
	
	/* placer ses bateaux (random)
	 * tirer avec une strategie
	 * 
	 */

}



