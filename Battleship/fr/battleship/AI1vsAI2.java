package fr.battleship;

import java.util.List;

import chawaf.alia.Core.Coord;
import chawaf.alia.Core.Game;
import chawaf.alia.Core.Ship;
import chawaf.alia.Player.AI.ArtificialPlayer1;
import chawaf.alia.Player.AI.ArtificialPlayer2;

public class AI1vsAI2 {

	public static String playBattleship(List<Ship> player1_ships, List<Ship> player2_ships,
			int currentPlayer) {

		int gridSize = 10;
		Coord missileCoord = new Coord(gridSize);
		
		boolean hitPlayer1 = false;
		boolean destroyedPlayer1 = false;
		Coord missileCoordHitPlayer1 = new Coord(gridSize);
		
		boolean hitPlayer2 = false;
		boolean destroyedPlayer2 = false;
		Coord missileCoordHitPlayer2 = new Coord(gridSize);

		/* set up 2 players */
		ArtificialPlayer1 player1 = new ArtificialPlayer1(gridSize);
		ArtificialPlayer2 player2 = new ArtificialPlayer2(gridSize);

		/* set up a game */
		Game game = new Game(player1, player2);

		if (currentPlayer == 1) {
			game.setCurrentPlayer(player1);
		} else {
			game.setCurrentPlayer(player2);
		}

		/* Place all their ships */

		player1.placeAllShips(player1_ships);
		player2.placeAllShips(player2_ships);
		
		// ********* ATTACK *********//
		
		while (game.NotEnded()) {
			
			if (game.getCurrentPlayer() == player1) {
				
				if (hitPlayer1 && !destroyedPlayer1) {
					
					missileCoord = player1.sendMissileAroundShipHit(missileCoordHitPlayer1);
				
				} else {
					missileCoord = player1.sendMissile();
					destroyedPlayer1 = false;
				}

				int n = player2.listShipDestroyed().size();

				if (player2.isAnyoneHit(missileCoord)) {

					missileCoordHitPlayer1 = missileCoord;

					hitPlayer1 = true;

					if (player2.listShipDestroyed().size() > n) {
						destroyedPlayer1 = true;
						hitPlayer1 = false;
					}
				} 
				
			} else {
				if (hitPlayer2 && !destroyedPlayer2) {
					missileCoord = player2.sendMissileAroundShipHit(missileCoordHitPlayer2);
				} else {
					missileCoord = player2.sendMissile();
					destroyedPlayer2 = false;
				}

				int n2 = player1.listShipDestroyed().size();

				if (player1.isAnyoneHit(missileCoord)) {
					
					missileCoordHitPlayer2 = missileCoord;

					hitPlayer2 = true;

					if (player1.listShipDestroyed().size() > n2) {
						
						destroyedPlayer2 = true;
						hitPlayer2 = false;
					}

				} else {
					
					player2.getListCoordMissileSentNotHit().add(missileCoord);

				}
			}
			game.changePlayer();
		}

		String winner = game.winnerEndGame().toString();

		/* clear hit coord for all ships for next game */
		player1.clearCoordHitAllShips();
		player2.clearCoordHitAllShips();
		
		return winner;
	}
}
