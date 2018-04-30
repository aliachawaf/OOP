import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	private static Scanner scanner;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);

		// input mapSize
		int mapSize;
		do {
			System.out.print("Enter the map size you want (between 5-25) : ");
			mapSize = scanner.nextInt();
			scanner.nextLine();
		} while (mapSize < 5 || mapSize > 25);

		// set up 5 ships for each player
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

		// set up a shipList for each player
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

		// set up 2 players
		Player player1 = new Player(1, player1_ships, mapSize);
		Player player2 = new Player(2, player2_ships, mapSize);

		// set up a game
		Game game = new Game(player1, player2);

		// initialization of boards
		player1.getBoardGame().initBoard();
		player1.getBoardAttack().initBoard();
		player2.getBoardGame().initBoard();
		player2.getBoardAttack().initBoard();

		// variables
		String start, end, missile;
		boolean checkSizeCoord, checkNotDiagonal; // used when players are
													// placing their ships
		boolean checkStartCoord, checkEndCoord, checkMissileCoord, checkPlaceFree, checkCoordFree;
		int l; // used only for loops 'for'
		Coord missileCoord;

		// ask player 1 then 2 to place his ships
		for (int a = 0; a < 2; a++) {

			// display game board for current player
			game.getCurrentPlayer().getBoardGame().displayBoard();

			System.out.println("********* PLAYER "
					+ game.getCurrentPlayer().getPlayerNumber() + " *********");

			// ask coord for each ship
			for (int i = 0; i < game.getCurrentPlayer().getPlayerShips().size(); i++) {

				Ship currentShip = game.getCurrentPlayer().getPlayerShips()
						.get(i);

				System.out.println("enter extreme positions for your ship "
						+ currentShip.getName() + " (size "
						+ currentShip.getSize() + ") :");

				do {
					// input coord
					start = scanner.nextLine();
					end = scanner.nextLine();

					Coord startCoord = new Coord(start, mapSize);
					Coord endCoord = new Coord(end, mapSize);

					// interchange the 2 coord if not in the right direction
					startCoord.putCoordInOrder(endCoord);

					// update ship coord with the inputs
					currentShip.setStartCoord(startCoord);
					currentShip.setEndCoord(endCoord);

					// checking
					checkStartCoord = startCoord.checkCoord();
					checkEndCoord = endCoord.checkCoord();

					checkSizeCoord = currentShip.checkSizeCoord();

					checkNotDiagonal = currentShip.checkNotDiagonal();

					// if not the first ship placed
					if (i > 0) {
						// we check with all previous ships placed if the place
						// chosen is free
						for (l = 0; l < i; l++) {
							Ship previousShip = game.getCurrentPlayer()
									.getPlayerShips().get(l);
							checkCoordFree = currentShip
									.checkPlaceFree(previousShip);
							if (!(checkCoordFree)) {
								checkPlaceFree = false;
								System.out.println("place is free : "
										+ checkPlaceFree);
							}

						}
					}

					if (!(checkStartCoord) || !(checkEndCoord)) {
						System.out
								.println("Coords you've entered are not in the right format !");
						System.out
								.println("Look at the board game columns' and lines' label. Re-enter them : ");
					} else if (!(checkSizeCoord)) {
						System.out
								.println("those coord do not match with the ship's size, re-enter them : ");
					} else if (!(checkNotDiagonal)) {
						System.out
								.println("you can't place this ship in diagonal ! re-enter coords : ");
					}

				} while (!(checkSizeCoord)
						|| !(checkNotDiagonal || !(checkStartCoord) || !(checkEndCoord)));

				// update and display new board
				/*
				 * if (game.getCurrentPlayer() == player1) {
				 * p1_board.updateBoard(player1.getPlayerShips().get(i));
				 * p1_board.displayBoard(); } else {
				 * p2_board.updateBoard(player2.getPlayerShips().get(i));
				 * p2_board.displayBoard(); }
				 */

				game.getCurrentPlayer().getBoardGame().updateBoard(currentShip);
				game.getCurrentPlayer().getBoardGame().displayBoard();

			}
			game.changePlayer();

			for (l = 0; l <= 25; l++) {
				System.out.println();
			}
		}

		while (game.gameNotEnded()) {

			System.out.println("********* PLAYER "
					+ game.getCurrentPlayer().getPlayerNumber() + " *********");

			// display board attack
			System.out.println("Your current board attack : ");
			game.getCurrentPlayer().getBoardAttack().displayBoard();

			// input missile position
			System.out.print("choose a missile position to attack : ");
			do {
				missile = scanner.nextLine();
				missileCoord = new Coord(missile, mapSize);
				checkMissileCoord = missileCoord.checkCoord();

				if (!(checkMissileCoord)) {
					System.out
							.println("Missile position you've entered is not in the right format !");
					System.out
							.println("Column must be between A-J, and line between 1-10. Re-enter them : ");
				}

			} while (!(checkMissileCoord));

			// for each opponent's ship, check if one of them is hit and then
			// destroyed
			/*
			 * for (int i = 0; i <
			 * game.opponentPlayer().getPlayerShips().size(); i++) {
			 * 
			 * if (game.opponentPlayer().getPlayerShips().get(i)
			 * .isHit(missileCoord)) {
			 * 
			 * System.out
			 * .println("You've hit a ship ! Your new board attack : ");
			 * 
			 * // update and display boards
			 * game.getCurrentPlayer().getBoardAttack()
			 * .updateBoardAttack(missileCoord, 1);
			 * game.opponentPlayer().getBoardGame()
			 * .updateBoardAttack(missileCoord, 1);
			 * game.getCurrentPlayer().getBoardAttack().displayBoard();
			 * 
			 * hit = true; }
			 * 
			 * if (game.opponentPlayer().getPlayerShips().get(i).isDestroyed())
			 * {
			 * 
			 * System.out.println("You've DESTROYED the ship " +
			 * game.opponentPlayer().getPlayerShips().get(i) .getName() +
			 * " (size " + game.opponentPlayer().getPlayerShips().get(i)
			 * .getSize() + ") !"); }
			 * 
			 * }
			 */

			/*if (game.opponentPlayer().shipsAreDestroyed(missileCoord)) {
				System.out.println("You've DESTROYED the ship ");
			} */
			
			// number of opponent's ships destroyed before attacking
			int nb = game.opponentPlayer().listShipDestroyed().size(); 
			
			if (game.opponentPlayer().shipsAreHit(missileCoord)) {
				System.out
						.println("You've hit a ship ! Your new board attack : ");

				// update and display boards
				game.getCurrentPlayer().getBoardAttack().updateBoardAttack(missileCoord, 1);
				game.opponentPlayer().getBoardGame().updateBoardAttack(missileCoord, 1);
				game.getCurrentPlayer().getBoardAttack().displayBoard();
				
				// check if ship is destroyed (one more ship in the list)
				if (game.opponentPlayer().listShipDestroyed().size()>nb){
					System.out.println("You've DESTROYED the ship. ");
				}
			} else {
				System.out.println("You haven't hit any ship. Your new board attack : ");

				// update and display board attack
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

		System.out.println(" Game ended ! The winner is "
				+ game.winnerEndGame() + " !");

		scanner.close();
	}

}
