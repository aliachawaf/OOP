package chawaf.alia.Player.AI;

import java.util.List;

import chawaf.alia.Core.Coord;
import chawaf.alia.Core.Ship;

public interface ArtificialIntelligence {

	public Coord choseOneCoord();
	
	public void placeOneShip(Ship s);
	
	public void placeAllShips(List<Ship> list);
	
	public Coord sendMissile();
	
}
