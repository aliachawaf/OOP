package chawaf.alia.PlayMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import chawaf.alia.Core.Coord;
import chawaf.alia.Core.Game;
import chawaf.alia.Core.Ship;
import chawaf.alia.Player.HumanPlayer;
import chawaf.alia.Player.Player;
import chawaf.alia.Player.AI.ArtificialPlayer1;

public class HumanVSArtificial1 {

	private static Scanner scanner;

	public static void playBattleship(int gridSize, List<Ship> player1_ships,
			List<Ship> player2_ships, int currentPlayer) {

		/*
		 * we save players's ships in case they want to play again at the end of
		 * this game
		 */
		List<Ship> p1 = new ArrayList<Ship>();
		List<Ship> p2 = new ArrayList<Ship>();

		p1.addAll(player1_ships);
		p2.addAll(player2_ships);

		boolean checkNotException = false;

		scanner = new Scanner(System.in);

		/* set up 2 players */
		String name;
		System.out.print("\nEnter your name : ");
		name = scanner.nextLine();
		Player player1 = new HumanPlayer(name, gridSize);

		ArtificialPlayer1 player2 = new ArtificialPlayer1(gridSize);

		/* set up a game */
		Game game = new Game(player1, player2);

		if (currentPlayer == 1) {
			game.setCurrentPlayer(player1);
		} else {
			game.setCurrentPlayer(player2);
		}

		/* initialization of grids' display */
		player1.getGridGame().initGrid();
		player1.getGridAttack().initGrid();

		/* variables used for input */
		String start = null;
		String end = null;
		String missile = null;
		Coord startCoord = null;
		Coord endCoord = null;
		Coord missileCoord = null;
		Coord missileCoordHit = null;

		int indexShipToPlace = 1; // used to ask which ship to place
		int sizeShipToPlace = 5;

		/* variable used for loops */
		int l;

		/* variables used for checking */
		boolean checkCoordsMatchWithSize, checkNotDiagonal, checkStartCoord, checkEndCoord;
		boolean checkMissileCoord, checkPlaceIsFree;
		boolean hit = false;
		boolean destroyed = false;

		// ********* SET UP SHIPS ON THE BOARDS *********//

		/* asking player 1 to place his ships in the order he want */

		/* display game grid for current player */
		System.out.println(player1.getGridGame());

		System.out.println("*************** " + player1 + " **************\n");

		System.out
				.println("You have to place your 5 ships on your grid in the order you want.\n");

		int numberOfShips = player1_ships.size();

		for (int i = 0; i < numberOfShips; i++) {

			System.out.println("Ships not placed on the grid : "
					+ player1_ships);

			/*
			 * ask player which ship he want to place now (only if there is more
			 * than one ship not placed)
			 */
			if (player1_ships.size() > 1) {

				System.out.print("\nWhich one do you want to place now ? ");
				System.out.print("Enter its size : ");

				checkNotException = false;

				do {
					try {
						// ask for the size of the ship he want to place
						do {
							sizeShipToPlace = scanner.nextInt();
							scanner.nextLine();

							indexShipToPlace = -1;

							for (int g = 0; g < player1_ships.size(); g++) {
								if (sizeShipToPlace == player1_ships.get(g)
										.getSize()) {
									indexShipToPlace = g;
								}
							}

							if (indexShipToPlace == -1) {
								System.out.print("\nNo ship with this size !");
								System.out.print(" Re-enter it : ");
							}

						} while (indexShipToPlace == -1);

						checkNotException = true;

					} catch (java.util.InputMismatchException e) {
						System.out
								.print("\nYour input is not a number ! Re-enter it : ");
						scanner.nextLine();
					} catch (StringIndexOutOfBoundsException e) {
						System.out
								.print("\nYour input is missing ! Re-enter it : ");
						scanner.nextLine();
					}
				} while (!checkNotException);

			} else {
				indexShipToPlace = 0; // if there is only one ship not
										// placed, index is 1 by default
			}

			Ship currentShipToPlace = player1_ships.get(indexShipToPlace);

			System.out.println("\nWrite start and end positions for the ship "
					+ currentShipToPlace.getName() + " (size "
					+ currentShipToPlace.getSize()
					+ ") :     (press ENTER after each one)");

			do {

				/* input coord for the ship chosen */

				checkNotException = false;

				while (!checkNotException) {
					try {
						start = scanner.nextLine();
						end = scanner.nextLine();
						startCoord = new Coord(start, gridSize);
						endCoord = new Coord(end, gridSize);

						checkNotException = true;

					} catch (NumberFormatException e) {
						System.out
								.print("\nOne of the lines you've entered is not a number !");
						System.out.print(" Re-enter the 2 coords : ");
						scanner.nextLine();
					} catch (StringIndexOutOfBoundsException e) {
						System.out
								.print("One of the 2 coords is missing. Re-enter them : ");
						scanner.nextLine();
					}
				}

				/*
				 * interchange the 2 coord if not in the right direction (bottom
				 * to top / right to left)
				 */
				startCoord.putCoordInOrder(endCoord);

				/* update ship's coord with the inputs */
				currentShipToPlace.setStartCoord(startCoord);
				currentShipToPlace.setEndCoord(endCoord);

				/* check */
				checkStartCoord = startCoord.checkCoord();
				checkEndCoord = endCoord.checkCoord();

				checkCoordsMatchWithSize = currentShipToPlace
						.checkCoordsMatchWithSize();

				checkNotDiagonal = currentShipToPlace.checkNotDiagonal();

				/*
				 * we check with all previous ships placed if the place chosen
				 * is free
				 */
				/*
				 * nb : current player ships list contains only the ships which
				 * are already placed on the grid
				 */
				checkPlaceIsFree = currentShipToPlace.checkPlaceIsFree(player1
						.listCoordTaken());

				if (!(checkPlaceIsFree)) {
					System.out.println("The place you've chosen is not free !");
					System.out.println(" Re-enter coords : ");
				} else if (!(checkStartCoord) || !(checkEndCoord)) {
					System.out
							.println("Coords you've entered are not in the right format !");
					System.out
							.print("Look at the grid game columns' and lines' label.");
					System.out.println(" Re-enter them : ");
				} else if (!(checkCoordsMatchWithSize)) {
					System.out
							.print("Those coord do not match with the ship's size !");
					System.out.println(" Re-enter them : ");
				} else if (!(checkNotDiagonal)) {
					System.out.print("You can't place this ship in diagonal !");
					System.out.println(" Re-enter coords : ");
				}

			} while (!(checkPlaceIsFree) || !(checkCoordsMatchWithSize)
					|| !(checkNotDiagonal) || !(checkStartCoord)
					|| !(checkEndCoord));

			/*
			 * once coord for the ship are chosen and correct, we add this ship
			 * to the player's list ships placed
			 */
			player1.getPlayerShips().add(currentShipToPlace);

			/* update and display new grid with the ship placed */
			player1.getGridGame().updateGrid(currentShipToPlace);

			System.out.println(player1.getGridGame());

			/* remove the ship placed from the list of ships not placed yet */
			player1_ships.remove(indexShipToPlace);
		}

		for (l = 0; l <= 50; l++) {
			System.out.println();
		}

		player2.placeAllShips(player2_ships);
		System.out.println(player2.getGridGame());

		for (l = 0; l <= 50; l++) {
			System.out.println();
		}

		// ********* ATTACK *********//

		while (game.NotEnded()) {

			System.out.println(" *************** " + game.getCurrentPlayer()
					+ " **************");

			if (game.getCurrentPlayer() == player1) {

				/* display grid attack */
				System.out.println("Your current grid attack : ");
				System.out.println(game.getCurrentPlayer().getGridAttack());

				/* input missile position */
				System.out.print("Choose a missile position to attack : ");

				do {

					checkNotException = false;

					while (!checkNotException) {
						try {
							missile = scanner.nextLine();
							missileCoord = new Coord(missile, gridSize);

							checkNotException = true;

						} catch (NumberFormatException e) {
							System.out
									.print("\nThe lines of the missile position you've entered is not a number ! Re-enter it : ");
							scanner.nextLine();
						} catch (StringIndexOutOfBoundsException e) {
							System.out
									.print("\nMissile coord is missing. Re-enter it :");
							scanner.nextLine();
						}
					}

					checkMissileCoord = missileCoord.checkCoord();

					if (!(checkMissileCoord)) {
						System.out
								.println("Missile position you've entered is not in the right format !");
						System.out
								.print("Look at labels of colums and lines. Re-enter it : ");
					}
				} while (!(checkMissileCoord));

				/* number of opponent's ships destroyed before attacking */
				int n = player2.listShipDestroyed().size();

				if (player2.isAnyoneHit(missileCoord)) {

					System.out
							.println("\n  YES !!!  \n\n You've hit a ship ! Your new grid attack : ");

					/* update and display grids */
					player1.getGridAttack().updateGridAttack(missileCoord, 1);
					player2.getGridGame().updateGridAttack(missileCoord, 1);
					System.out.println(player1.getGridAttack());
					// System.out.println("AI : " + player2.getGridGame());

					/* check if ship is destroyed (one more ship in the list) */
					if (player2.listShipDestroyed().size() > n) {
						System.out.println("You've DESTROYED the ship. ");
					}
				}

				else {
					System.out
							.println("\n  NO !!! \n\n You haven't hit any ship. Your new grid attack : ");

					/* update and display grid attack */
					player1.getGridAttack().updateGridAttack(missileCoord, 0);

					System.out.println(player1.getGridAttack());
				}

				System.out.println("ships destroyed : "
						+ player2.listShipDestroyed());

				for (l = 0; l <= 5; l++) {
					System.out.println();
				}
			} else {

				if (hit && !destroyed) {
					missileCoord = player2
							.sendMissileAroundShipHit(missileCoordHit);
				} else {
					missileCoord = player2.sendMissile();
					destroyed = false;
				}

				int n2 = player1.listShipDestroyed().size();

				if (player1.isAnyoneHit(missileCoord)) {
					/* update and display grids */
					player2.getGridAttack().updateGridAttack(missileCoord, 1);
					player1.getGridGame().updateGridAttack(missileCoord, 1);
					System.out.println("You've been hit on " + missileCoord
							+ ". Your new grid game : "
							+ player1.getGridGame());

					missileCoordHit = missileCoord;

					hit = true;

					if (player1.listShipDestroyed().size() > n2) {
						destroyed = true;
						hit = false;
					}

				} else {
					/* update and display grid attack */
					player2.getGridAttack().updateGridAttack(missileCoord, 0);
					player1.getGridGame().updateGridAttack(missileCoord, 0);
					System.out
							.println("You've not been hit. Your new grid game : "
									+ player1.getGridGame());
				}
				for (l = 0; l <= 5; l++) {
					System.out.println();
				}
			}
			game.changePlayer();
		}

		System.out.println(" ************ Game ended ! ************ \n");
		System.out.println("The winner is " + game.winnerEndGame() + " !");


		System.out.print("\nDo you want to play again (yes/no) ? ");
		String playAgain;
		
		do {
			playAgain = scanner.nextLine();

			if (!playAgain.matches("no") && !playAgain.matches("yes")) {
				System.out
						.print("Your answer is different of 'no' or 'yes'. Re-enter it : ");
			}

		} while (!playAgain.matches("no") && !playAgain.matches("yes"));


		if (playAgain.matches("yes")) {
			game.getPlayer1().clearCoordHitAllShips();
			game.getPlayer2().clearCoordHitAllShips();

			if (currentPlayer == 1) {
				currentPlayer = 2;
				HumanVSArtificial1.playBattleship(gridSize, p1, p2, currentPlayer);
			} else {
				currentPlayer = 1;
				HumanVSArtificial1.playBattleship(gridSize, p1, p2, currentPlayer);
			}
		}

		scanner.close();

	}
}
