package fr.igpolytech.aliachawaf.Battleship;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class HumanVSHuman {

	private static Scanner scanner;

	public static void main(int mapSize, List<Ship> player1_ships, List<Ship> player2_ships) {
			
		boolean checkNotException = false;
		
		scanner = new Scanner(System.in);

		/* set up 2 players */
		String name;
		System.out.print("Player 1, enter your name : ");
		name = scanner.nextLine();
		Player player1 = new HumanPlayer(name, mapSize);
		
		System.out.print("Player 2, enter your name : ");
		name = scanner.nextLine();
		Player player2 = new HumanPlayer(name, mapSize);

		/* set up a game */
		Game game = new Game(player1, player2); // by default, current player is
												// player1

		/* initialization of boards' display */
		player1.getBoardGame().initBoard();
		player1.getBoardAttack().initBoard();
		player2.getBoardGame().initBoard();
		player2.getBoardAttack().initBoard();

		/* variables used for input */
		String start = null;
		String end = null;
		String missile = null;
		Coord startCoord = null;
		Coord endCoord = null;
		Coord missileCoord = null;
		int indexShipToPlace = 1; // used to ask which ship to place
		int sizeShipToPlace = 5;

		/* variable used for loops */
		int l;

		/* variables used for checking */
		boolean checkCoordsMatchWithSize, checkNotDiagonal, checkStartCoord, checkEndCoord;
		boolean checkMissileCoord, checkPlaceIsFree;

		List<Ship> listShipNotPlacedYet = new ArrayList<Ship>();

		// ********* SET UP SHIPS ON THE BOARDS *********//

		/* asking player 1 then 2 to place his ships in the order they want */
		for (int p = 0; p < 2; p++) {

			/* initialize the list of ships not placed yet for current player */

			if (game.getCurrentPlayer() == player1) {
				listShipNotPlacedYet = player1_ships;
			} else {
				listShipNotPlacedYet = player2_ships;
			}

			/* display game board for current player */
			System.out.println(game.getCurrentPlayer().getBoardGame());

			System.out.println("*************** " + game.getCurrentPlayer() + " **************\n");

			System.out.println("You have to place your 5 ships on your board in the order you want.\n");
			
			Iterator<Ship> itShipNotPlacedYet = listShipNotPlacedYet.iterator();
			
			while (itShipNotPlacedYet.hasNext()){
			
				System.out.println("Ships not placed on the board : "
						+ listShipNotPlacedYet);

				/*
				 * ask player which ship he want to place now (only if there is
				 * more than one ship not placed)
				 */
				if (listShipNotPlacedYet.size() > 1) {

					System.out.print("\nWhich one do you want to place now ? ");
					System.out.print("Enter its size : ");

					checkNotException = false;

					do {
						try {
							// ask for the size of the ship he want to place
							// until !=2,3,4,5
							do {
								sizeShipToPlace = scanner.nextInt();
								scanner.nextLine();

								indexShipToPlace = -1;

								for (int g = 0; g < listShipNotPlacedYet.size(); g++) {
									if (sizeShipToPlace == listShipNotPlacedYet
											.get(g).getSize()) {
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

				Ship currentShipToPlace = listShipNotPlacedYet
						.get(indexShipToPlace);

				System.out
						.println("\nWrite start and end positions for the ship "
								+ currentShipToPlace.getName()
								+ " (size "
								+ currentShipToPlace.getSize() + ") :     (press ENTER after each one)");

				do {

					/* input coord for the ship chosen */

					checkNotException = false;

					while (!checkNotException) {
						try {
							start = scanner.nextLine();
							end = scanner.nextLine();
							startCoord = new Coord(start, mapSize);
							endCoord = new Coord(end, mapSize);

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
					 * interchange the 2 coord if not in the right direction
					 * (bottom to top / right to left)
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
					 * we check with all previous ships placed if the place
					 * chosen is free
					 */
					/*
					 * nb : current player ships list contains only the ships
					 * which are already placed on the board
					 */
					//checkPlaceIsFree = currentShipToPlace.checkPlaceIsFree(game
						//	.getCurrentPlayer().getPlayerShips());
					
					checkPlaceIsFree = currentShipToPlace.checkPlaceIsFree(game
							.getCurrentPlayer().listCoordTaken());

					if (!(checkPlaceIsFree)) {
						System.out
								.println("The place you've chosen is not free !");
						System.out.println(" Re-enter coords : ");
					} else if (!(checkStartCoord) || !(checkEndCoord)) {
						System.out
								.println("Coords you've entered are not in the right format !");
						System.out
								.print("Look at the board game columns' and lines' label.");
						System.out.println(" Re-enter them : ");
					} else if (!(checkCoordsMatchWithSize)) {
						System.out
								.print("Those coord do not match with the ship's size !");
						System.out.println(" Re-enter them : ");
					} else if (!(checkNotDiagonal)) {
						System.out
								.print("You can't place this ship in diagonal !");
						System.out.println(" Re-enter coords : ");
					}

				} while (!(checkPlaceIsFree) || !(checkCoordsMatchWithSize) || !(checkNotDiagonal) || !(checkStartCoord) || !(checkEndCoord));
				
				/*
				 * once coord for the ship are chosen and correct, we add this
				 * ship to the player's list ships placed
				 */
				game.getCurrentPlayer().getPlayerShips()
						.add(currentShipToPlace);

				/* update and display new board with the ship placed */
				game.getCurrentPlayer().getBoardGame()
						.updateBoard(currentShipToPlace);
				System.out.println(game.getCurrentPlayer().getBoardGame());

				/* remove the ship placed from the list of ships not placed yet */
				listShipNotPlacedYet.remove(indexShipToPlace);
			}

			game.changePlayer();

			for (l = 0; l <= 40; l++) {
				System.out.println();
			}
		}

		// ********* ATTACK *********//

		while (game.NotEnded()) {

			System.out.println(" *************** " + game.getCurrentPlayer() + " **************");

			/* display board attack */
			System.out.println("Your current board attack : ");
			System.out.println(game.getCurrentPlayer().getBoardAttack());

			/* input missile position */
			System.out.print("Choose a missile position to attack : ");

			do {

				checkNotException = false;

				while (!checkNotException) {
					try {
						missile = scanner.nextLine();
						missileCoord = new Coord(missile, mapSize);

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
			int n = game.opponentPlayer().listShipDestroyed().size();

			if (game.opponentPlayer().isAnyoneHit(missileCoord)) {

				System.out
						.println("\n  YES !!!  \n\n You've hit a ship ! Your new board attack : ");

				/* update and display boards */
				game.getCurrentPlayer().getBoardAttack()
						.updateBoardAttack(missileCoord, 1);
				game.opponentPlayer().getBoardGame()
						.updateBoardAttack(missileCoord, 1);
				System.out.println(game.getCurrentPlayer().getBoardAttack());

				/* check if ship is destroyed (one more ship in the list) */
				if (game.opponentPlayer().listShipDestroyed().size() > n) {
					System.out.println("You've DESTROYED the ship. ");
				}
			}

			else {
				System.out
						.println("\n  NO !!! \n\n You haven't hit any ship. Your new board attack : ");

				/* update and display board attack */
				game.getCurrentPlayer().getBoardAttack()
						.updateBoardAttack(missileCoord, 0);
				System.out.println(game.getCurrentPlayer().getBoardAttack());
			}

			System.out.println("ships destroyed : "
					+ game.opponentPlayer().listShipDestroyed());

			for (l = 0; l <= 5; l++) {
				System.out.println();
			}
			game.changePlayer();
		}

		System.out.println(" ************ Game ended ! ************ \n");
		System.out.println("The winner is " + game.winnerEndGame() + " !");

		scanner.close();
	}
}