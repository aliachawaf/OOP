package fr.igpolytech.aliachawaf.Battleship;

import java.util.List;

public interface ArtificialIntelligence {

	public Coord choseOneCoord();
	
	public void placeOneShip(Ship s);
	
	public void placeAllShips(List<Ship> list);
	
	public Coord sendMissile();
	
}
