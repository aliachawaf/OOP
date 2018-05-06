import java.util.ArrayList;
import java.util.Scanner;

//test

public class Main {

	private static Scanner scanner;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);

		/* input mapSize */
		int mapSize = 10;
		boolean checkNotException = false;

		System.out.print("Enter the map size you want between 5-25 (ex : enter 10 to have a map 10x10) : ");

		while (!checkNotException) {
			try {
				do {
					mapSize = scanner.nextInt();
					scanner.nextLine();

					if (mapSize < 5 || mapSize > 25) {
						System.out
								.print("\nThe size you've entered is not between 5 and 25 ! Re-enter it : ");
					}

				} while (mapSize < 5 || mapSize > 25);

				checkNotException = true;

			} catch (java.util.InputMismatchException e) {
				System.out
						.print("\nThe size you've entered is not a number ! Re-enter it : ");
				scanner.nextLine();
			} catch (StringIndexOutOfBoundsException e) {
				System.out.print("Map size is missing. Re-enter it :");
				scanner.nextLine();
			}
		}
		
		System.out.println("mapsize : " + (char)(mapSize + 64));

		/* set up 5 ships for each player */
		Ship carrier_1 = new Ship("carrier", 5);
		Ship battleship_1 = new Ship("battleship", 4);
		Ship cruiser_1 = new Ship("cruiser", 3);
		Ship submarine_1 = new Ship("submarine", 3);
		Ship destroyer_1 = new Ship("destroyer", 2);

		Ship carrier_2 = new Ship("carrier", 5);
		Ship battleship_2 = new Ship("battleship", 4);
		Ship cruiser_2 = new Ship("cruiser", 3);
		Ship submarine_2 = new Ship("submarine", 3);
		Ship destroyer_2 = new Ship("destroyer", 2);

		/* set up a list of ships for each player */
		ArrayList<Ship> player1_ships = new ArrayList<Ship>();
		player1_ships.add(carrier_1);
		player1_ships.add(battleship_1);
		player1_ships.add(cruiser_1);
		player1_ships.add(submarine_1);
		player1_ships.add(destroyer_1);

		ArrayList<Ship> player2_ships = new ArrayList<Ship>();
		player2_ships.add(carrier_2);
		player2_ships.add(battleship_2);
		player2_ships.add(cruiser_2);
		player2_ships.add(submarine_2);
		player2_ships.add(destroyer_2);

		/* set up 2 players */
		Player player1 = new Player(1, mapSize);
		Player player2 = new Player(2, mapSize);

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

		ArrayList<Ship> listShipNotPlacedYet = new ArrayList<Ship>();

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
			game.getCurrentPlayer().getBoardGame().displayBoard();

			System.out.println("********* PLAYER "
					+ game.getCurrentPlayer().getPlayerNumber() + " *********");

			for (int i = 0; i < 5; i++) {

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

					while (!checkNotException) {
						try {
							// ask for the size of the ship he want to place
							// until !=2,3,4,5
							do {
								sizeShipToPlace = scanner.nextInt();
								scanner.nextLine();

								if (sizeShipToPlace != 5
										&& sizeShipToPlace != 4
										&& sizeShipToPlace != 3
										&& sizeShipToPlace != 2) {
									System.out
											.print("\nSize entered not equal to 2,3,4 or 5. Re-enter it : ");
								}
								// when size is chosen, we find the index of the
								// ship with this size in the list
								else {

									indexShipToPlace = -1;

									for (int g = 0; g < listShipNotPlacedYet
											.size(); g++) {
										if (sizeShipToPlace == listShipNotPlacedYet
												.get(g).getSize()) {
											indexShipToPlace = g;
										}
									}

									if (indexShipToPlace == -1) {
										System.out
												.print("\nAll your ships with this size are already placed on the board !");
										System.out.print(" Re-enter it : ");
									}
								}

							} while (sizeShipToPlace != 5
									&& sizeShipToPlace != 4
									&& sizeShipToPlace != 3
									&& sizeShipToPlace != 2
									|| indexShipToPlace == -1);

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
					}

				} else {
					indexShipToPlace = 0; // if there is only one ship not
											// placed, index is 1 by default
				}

				Ship currentShipToPlace = listShipNotPlacedYet
						.get(indexShipToPlace);

				System.out
						.println("\nEnter start and end positions for the ship "
								+ currentShipToPlace.getName()
								+ " (size "
								+ currentShipToPlace.getSize() + ") :");

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
					
					System.out.println("check start and end coord : " + checkStartCoord + checkEndCoord );

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
					checkPlaceIsFree = currentShipToPlace.checkPlaceIsFree(game
							.getCurrentPlayer().getPlayerShips());

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

				System.out.print("je suis la ");
				
				/*
				 * once coord for the ship are chosen and correct, we add this
				 * ship to the player's list ships placed
				 */
				game.getCurrentPlayer().getPlayerShips()
						.add(currentShipToPlace);

				/* update and display new board with the ship placed */
				game.getCurrentPlayer().getBoardGame()
						.updateBoard(currentShipToPlace);
				game.getCurrentPlayer().getBoardGame().displayBoard();

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

			System.out.println(" ********* PLAYER "
					+ game.getCurrentPlayer().getPlayerNumber() + " *********");

			/* display board attack */
			System.out.println("Your current board attack : ");
			game.getCurrentPlayer().getBoardAttack().displayBoard();

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
						.println(" [ YES !!! ] \n You've hit a ship ! Your new board attack : ");

				/* update and display boards */
				game.getCurrentPlayer().getBoardAttack()
						.updateBoardAttack(missileCoord, 1);
				game.opponentPlayer().getBoardGame()
						.updateBoardAttack(missileCoord, 1);
				game.getCurrentPlayer().getBoardAttack().displayBoard();

				/* check if ship is destroyed (one more ship in the list) */
				if (game.opponentPlayer().listShipDestroyed().size() > n) {
					System.out.println("You've DESTROYED the ship. ");
				}
			}

			else {
				System.out
						.println(" [ NO !!! ]\n You haven't hit any ship. Your new board attack : ");

				/* update and display board attack */
				game.getCurrentPlayer().getBoardAttack()
						.updateBoardAttack(missileCoord, 0);
				game.getCurrentPlayer().getBoardAttack().displayBoard();
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