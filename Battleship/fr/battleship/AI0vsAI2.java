package fr.battleship;

import java.util.List;

import chawaf.alia.Core.Coord;
import chawaf.alia.Core.Game;
import chawaf.alia.Core.Ship;
import chawaf.alia.Player.AI.ArtificialPlayer0;
import chawaf.alia.Player.AI.ArtificialPlayer2;

public class AI0vsAI2 {

	public static String playBattleship(List<Ship> player0_ships, List<Ship> player1_ships, int currentPlayer) {

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

		/* Place all their ships on grid */

		player0.placeAllShips(player0_ships);
		player2.placeAllShips(player1_ships);

		
		// ********* ATTACK *********//
		
		while (game.NotEnded()) {
			if (game.getCurrentPlayer() == player0) {
				
				missileCoord = player0.sendMissile();

				player2.isAnyoneHit(missileCoord);
				

			} else {
				
				if (hit && !destroyed) {
					missileCoord = player2.sendMissileAroundShipHit(missileCoordHit);
				} else {
					missileCoord = player2.sendMissile();
					destroyed = false;
				}

				int n2 = player0.listShipDestroyed().size();

				if (player0.isAnyoneHit(missileCoord)) {
										
					missileCoordHit = missileCoord;

					hit = true;

					if (player0.listShipDestroyed().size() > n2) {
						destroyed = true;
						hit = false;
					}

				} else {
					player2.getListCoordMissileSentNotHit().add(missileCoord);
				}
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
