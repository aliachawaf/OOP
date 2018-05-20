package fr.battleship;

import java.util.List;

import chawaf.alia.ArtificialPlayer0;
import chawaf.alia.ArtificialPlayer2;
import chawaf.alia.Coord;
import chawaf.alia.Game;
import chawaf.alia.Ship;

public class AI0vsAI2 {

	public static String playBattleship(List<Ship> player0_ships, List<Ship> player1_ships,
			int currentPlayer) {

		int gridSize = 10;
		boolean hit = false;
		boolean destroyed = false;
		Coord missileCoord = new Coord(gridSize);
		Coord missileCoordHit = new Coord(gridSize);

		/* set up 2 players */
		ArtificialPlayer0 player0 = new ArtificialPlayer0(gridSize);
		ArtificialPlayer2 player2 = new ArtificialPlayer2(gridSize);

		/* set up a game */
		Game game = new Game(player0, player2);

		if (currentPlayer == 0) {
			game.setCurrentPlayer(player0);
		} else {
			game.setCurrentPlayer(player2);
		}

		/* initialization of boards' display */
		player0.getBoardGame().initBoard();
		player0.getBoardAttack().initBoard();
		player2.getBoardGame().initBoard();
		player2.getBoardAttack().initBoard();

		/* Place all their ships on grid */

		player0.placeAllShips(player0_ships);
		player2.placeAllShips(player1_ships);

		
		
		// ********* ATTACK *********//
		
		while (game.NotEnded()) {
			if (game.getCurrentPlayer() == player0) {
				missileCoord = player0.sendMissile();

				if (player2.isAnyoneHit(missileCoord)) {
					/* update and display boards */
					player0.getBoardAttack().updateBoardAttack(missileCoord, 1);
					player2.getBoardGame().updateBoardAttack(missileCoord, 1);

				} else {
					/* update and display board attack */
					player0.getBoardAttack().updateBoardAttack(missileCoord, 0);
					player2.getBoardGame().updateBoardAttack(missileCoord, 0);

				}

			} else {
				
				if (hit && !destroyed) {
					missileCoord = player2
							.sendMissileAroundShipHit(missileCoordHit);
				} else {
					missileCoord = player2.sendMissile();
					destroyed = false;
				}

				int n2 = player0.listShipDestroyed().size();

				if (player0.isAnyoneHit(missileCoord)) {
					/* update and display boards */
					player2.getBoardAttack().updateBoardAttack(missileCoord, 1);
					player0.getBoardGame().updateBoardAttack(missileCoord, 1);
					
					//player2.getListCoordMissileSentHit().add(missileCoord);
					//System.out.println("list hit : " + player2.getListCoordMissileSentHit());
					
					missileCoordHit = missileCoord;

					hit = true;

					if (player0.listShipDestroyed().size() > n2) {
						destroyed = true;
						hit = false;
					}

				} else {
					/* update and display board attack */
					player2.getBoardAttack().updateBoardAttack(missileCoord, 0);
					player0.getBoardGame().updateBoardAttack(missileCoord, 0);
					
					player2.getListCoordMissileSentNotHit().add(missileCoord);
					//System.out.println("list not hit : " + player2.getListCoordMissileSentNotHit());
				}
				//System.out.println("list sent : " + player2.getListCoordMissileSent());
				//System.out.println("AI2 " + player2.boardAttack);
			}
			game.changePlayer();
		}

		String winner = game.winnerEndGame().toString();

		/* clear hit coord for all ships for next game */
		player0.clearCoordHitAllShips();
		player2.clearCoordHitAllShips();
		
		return winner;

	}
}