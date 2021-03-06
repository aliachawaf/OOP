package chawaf.alia.Player.AI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import chawaf.alia.Core.Coord;
import chawaf.alia.Core.Ship;
import chawaf.alia.Player.Player;

public class ArtificialPlayer2 extends Player implements ArtificialIntelligence {

	private int gridSize;
	private List<Coord> listCoordMissileSent;
	private List<Coord> listCoordMissileSentNotHit;

	public ArtificialPlayer2(int gridSize) {
		super(gridSize);
		this.gridSize = gridSize;
		this.listCoordMissileSent = new ArrayList<Coord>();
		this.listCoordMissileSentNotHit = new ArrayList<Coord>();
	}

	// getters & setters
	public List<Coord> getListCoordMissileSent() {
		return listCoordMissileSent;
	}
	
	public List<Coord> getListCoordMissileSentNotHit() {
		return listCoordMissileSentNotHit;
	}

	public int getgridSize() {
		return gridSize;
	}

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

		Coord endCoord = new Coord(this.gridSize);
		Coord start = new Coord(this.gridSize);

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
	}

	public void placeAllShips(List<Ship> list) {

		for (Ship s : list) {
			placeOneShip(s);
		}
	}

	public Coord sendMissile() {

		/* Send missiles only on 'white' coord of the board (like chess) */
		Coord missileCoord = new Coord(this.gridSize);

		int randomIndex;

		if (this.hasSentMissilesOnAllWhiteCoord()) {

			do {
				missileCoord = this.choseOneCoord();
			} while (this.listCoordWithLineColumnSameParity().contains(
					missileCoord)
					|| this.listCoordMissileSent.contains(missileCoord)
					|| !this.checkCoordIsNotInIntersectionOfMissiles(missileCoord));

		} else {

			do {
				randomIndex = ThreadLocalRandom.current().nextInt(0,
						this.listCoordWithLineColumnSameParity().size());

				missileCoord = this.listCoordWithLineColumnSameParity().get(
						randomIndex);
				
				//System.out.println("not intersec " + this.checkCoordIsNotInIntersectionOfMissiles(missileCoord));
				
			} while (this.listCoordMissileSent.contains(missileCoord));
		}

		this.listCoordMissileSent.add(missileCoord);
		return missileCoord;

	}
	
	public List<Coord> listCoordWithLineColumnSameParity(){
		
		/* return all the 'white' coord of the grid (line and column have same parity) */
		
		List<Coord> list = new ArrayList<Coord>();
		
		boolean lineParity, columnParity, lineColumnSameParity;
		
		for (int line=1; line<=this.gridSize; line++){
			
			for (int column=1; column<=this.gridSize; column++){
				
				lineParity = (line % 2 == 0);

				columnParity = (column % 2 == 0);

				lineColumnSameParity = (lineParity & columnParity)
						|| (!lineParity & !columnParity);
				
				if (lineColumnSameParity){
					
					char col = (char)(column+64);
					Coord c = new Coord(col, line, this.gridSize);
					list.add(c);
					
				}
			}
			
		}
		return list;
	}

	public Coord sendMissileAroundShipHit(Coord hit) {

		Coord missileCoord = new Coord(this.gridSize);
		int randomDirection;
		char column;
		boolean checkMissileCoord;
		
		if (this.hasSentMissilesOnAllWhiteCoord()) {

			do {
				missileCoord = this.choseOneCoord();
			} while (this.listCoordWithLineColumnSameParity().contains(
					missileCoord)
					|| this.listCoordMissileSent.contains(missileCoord)
					|| !this.checkCoordIsNotInIntersectionOfMissiles(missileCoord));

		} else {

			do {
				/*
				 * random direction is chosen 1 : left to right 2 : right to
				 * left 3 : top to bottom 4 : bottom to top
				 */

				randomDirection = ThreadLocalRandom.current().nextInt(1, 17);
				
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

				} else if (randomDirection == 9) {
					column = hit.getColumn();
					column++;
					column++;
					column++;
					missileCoord.setColumn(column);
					missileCoord.setLine(hit.getLine());

				} else if (randomDirection == 10) {
					column = hit.getColumn();
					column--;
					column--;
					column--;
					missileCoord.setColumn(column);
					missileCoord.setLine(hit.getLine());
				
				} else if (randomDirection == 11) {
					missileCoord.setColumn(hit.getColumn());
					missileCoord.setLine(hit.getLine() + 3);
				
				} else if (randomDirection == 12 ) {
					missileCoord.setColumn(hit.getColumn());
					missileCoord.setLine(hit.getLine() - 3);

				} else if (randomDirection == 13 ) {
					column = hit.getColumn();
					column++;
					column++;
					column++;
					column++;
					missileCoord.setColumn(column);
					missileCoord.setLine(hit.getLine());
					
				} else if (randomDirection == 14 ) {
					column = hit.getColumn();
					column--;
					column--;
					column--;
					column--;
					missileCoord.setColumn(column);
					missileCoord.setLine(hit.getLine());

				} else if (randomDirection == 15 ) {
					missileCoord.setColumn(hit.getColumn());
					missileCoord.setLine(hit.getLine() + 4);

				} else if (randomDirection == 16) {
					missileCoord.setColumn(hit.getColumn());
					missileCoord.setLine(hit.getLine() - 4);

				} 
				
				checkMissileCoord = missileCoord.checkCoord();
				//System.out.println("not intersec " + this.checkCoordIsNotInIntersectionOfMissiles(missileCoord));

				
			} while (!(checkMissileCoord)
					|| this.listCoordMissileSent.contains(missileCoord));
			
		}
		this.listCoordMissileSent.add(missileCoord);
		return missileCoord;
	}
	
	public boolean hasSentMissilesOnAllWhiteCoord(){
		
		/* return true if missiles have been sent on all the 'white' coords (like chess) */
		
		int numberMissileSentOnWhiteCoord = 0;

		for (Coord c : this.listCoordWithLineColumnSameParity()) {

			if (this.listCoordMissileSent.contains(c)) {
				numberMissileSentOnWhiteCoord++;
			}

		}
		
		return (numberMissileSentOnWhiteCoord == this.listCoordWithLineColumnSameParity().size()); 
	
	}

	public boolean checkCoordIsNotInIntersectionOfMissiles(Coord c){
		
		/* checks if the coord is not between 4 missiles sent (right, left, top, bottom) */
		
		Coord c2 = new Coord(this.gridSize);
		
		boolean checkCoordTop = true;
		boolean checkCoordBottom = true;
		boolean checkCoordLeft = true;
		boolean checkCoordRight = true;
		boolean check = false;
		
		c2.setColumn(c.getColumn());
		c2.setLine(c.getLine()+1);
		
		if (!c2.checkCoord() || this.getListCoordMissileSentNotHit().contains(c2)){
			checkCoordBottom = false;
		}
		
		c2.setLine(c.getLine()-1);
		
		if (!c2.checkCoord() || this.getListCoordMissileSentNotHit().contains(c2)){
			checkCoordTop = false;
		}
		
		c2.setLine(c.getLine());
		
		char column = c.getColumn();
		column++;
		c2.setColumn(column);
		
		if (!c2.checkCoord() || this.getListCoordMissileSentNotHit().contains(c2)){
			checkCoordRight = false;
		}
		
		column = c.getColumn();
		column--;
		c2.setColumn(column);
		
		if (!c2.checkCoord() || this.getListCoordMissileSentNotHit().contains(c2)){
			checkCoordLeft = false;
		}
		
		if (checkCoordBottom || checkCoordLeft || checkCoordRight || checkCoordTop){
			check = true ;
		}

		return check;	
	}
	

	public boolean checkDontTouchAShip(Ship s) {

		boolean check = true;
		Coord c = new Coord(this.gridSize);
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