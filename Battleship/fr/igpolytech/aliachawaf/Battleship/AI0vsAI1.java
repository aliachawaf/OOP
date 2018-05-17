package fr.igpolytech.aliachawaf.Battleship;

import java.util.List;

public class AI0vsAI1 {

	/**
	 * @param args
	 */
	public static void main(List<Ship> player0_ships, List<Ship> player1_ships) {

		int mapSize = 14;
		boolean hit = false;
		boolean destroyed = false;
		Coord missileCoord = new Coord(mapSize);
		Coord missileCoordHit = new Coord(mapSize);

		/* set up 2 players */
		ArtificialPlayer0 player0 = new ArtificialPlayer0(mapSize);
		ArtificialPlayer1 player1 = new ArtificialPlayer1(mapSize);

		/* set up a game */
		Game game = new Game(player0, player1);

		game.setCurrentPlayer(player0);

		/* initialization of boards' display */
		player0.getBoardGame().initBoard();
		player0.getBoardAttack().initBoard();
		player1.getBoardGame().initBoard();
		player1.getBoardAttack().initBoard();

		/* Place all their ships on grid */

		player0.placeAllShips(player0_ships);
		player1.placeAllShips(player1_ships);
		

		// ********* ATTACK *********//

		while (game.NotEnded()) {
			if (game.getCurrentPlayer() == player0) {
				missileCoord = player0.sendMissile();

				if (player1.isAnyoneHit(missileCoord)) {
					/* update and display boards */
					player0.getBoardAttack().updateBoardAttack(missileCoord, 1);
					player1.getBoardGame().updateBoardAttack(missileCoord, 1);

				} else {
					/* update and display board attack */
					player0.getBoardAttack().updateBoardAttack(missileCoord, 0);
					player1.getBoardGame().updateBoardAttack(missileCoord, 0);

				}

				
			} else {
				if (hit && !destroyed) {
					missileCoord = player1
							.sendMissileAroundShipHit(missileCoordHit);
				} else {
					missileCoord = player1.sendMissile();
					destroyed = false;
				}

				int n2 = player0.listShipDestroyed().size();

				if (player0.isAnyoneHit(missileCoord)) {
					/* update and display boards */
					player1.getBoardAttack().updateBoardAttack(missileCoord, 1);
					player0.getBoardGame().updateBoardAttack(missileCoord, 1);

					missileCoordHit = missileCoord;

					hit = true;

					if (player0.listShipDestroyed().size() > n2) {
						destroyed = true;
						hit = false;
					}

				} else {
					/* update and display board attack */
					player1.getBoardAttack().updateBoardAttack(missileCoord, 0);
					player0.getBoardGame().updateBoardAttack(missileCoord, 0);
				
				}
				
			}
			game.changePlayer();
		}

		
		System.out.println(game.winnerEndGame());

	}

}
