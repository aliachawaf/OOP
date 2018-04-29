import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	private static Scanner scanner;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);

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
		Player player1 = new Player(1, player1_ships);
		Player player2 = new Player(2, player2_ships);

		// set up a game
		Game game = new Game(player1, player2);

		// set up 2 displays of game board (size 10) for each player
		Display p1_board = new Display(player1, 10);
		Display p1_board_attack = new Display(player1, 10);
		Display p2_board = new Display(player2, 10);
		Display p2_board_attack = new Display(player2, 10);

		// initialization of boards
		p1_board.initBoard();
		p1_board_attack.initBoard();
		p2_board.initBoard();
		p2_board_attack.initBoard();

		// variables
		String start, end, missile;
		boolean hit = false; // used when a player is attacking
		boolean checkSizeCoord, checkNotDiagonal; // used when players are
													// placing their ships
		boolean checkStartCoord, checkEndCoord, checkMissileCoord;
		int l; // used only for loops 'for'
		Coord missileCoord;

		// ask player 1 then 2 to place his ships
		for (int a = 0; a < 2; a++) {

			if (game.getCurrentPlayer() == player1) {
				p1_board.displayBoard();
			} else {
				p2_board.displayBoard();
			}

			System.out.println("********* PLAYER "
					+ game.getCurrentPlayer().getPlayerNumber() + " *********");

			for (int i = 0; i < game.getCurrentPlayer().getPlayerShips().size(); i++) {

				System.out
						.println("enter first and last position for your ship "
								+ game.getCurrentPlayer().getPlayerShips()
										.get(i).getName()
								+ " (size "
								+ game.getCurrentPlayer().getPlayerShips()
										.get(i).getSize() + ") :");

				do {

					// input coord
					start = scanner.nextLine();
					end = scanner.nextLine();

					Coord startCoord = new Coord(start);
					Coord endCoord = new Coord(end);

					// update ship coord
					game.getCurrentPlayer().getPlayerShips().get(i)
							.setStartCoord(startCoord);
					game.getCurrentPlayer().getPlayerShips().get(i)
							.setEndCoord(endCoord);

					checkSizeCoord = game.getCurrentPlayer().getPlayerShips()
							.get(i).checkSizeCoord();
					checkNotDiagonal = game.getCurrentPlayer().getPlayerShips()
							.get(i).checkNotDiagonal();

					// checks
					checkStartCoord = startCoord.checkCoord();
					checkEndCoord = endCoord.checkCoord();

					if (!(checkStartCoord) || !(checkEndCoord)) {
						System.out
								.println("Coords you've entered are not in the right format !");
						System.out
								.println("Columns must be between A-J, and lines between 1-10. Re-enter them : ");
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
				if (game.getCurrentPlayer() == player1) {
					p1_board.updateBoard(player1.getPlayerShips().get(i));
					p1_board.displayBoard();
				} else {
					p2_board.updateBoard(player2.getPlayerShips().get(i));
					p2_board.displayBoard();
				}
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
			if (game.getCurrentPlayer() == player1) {
				p1_board_attack.displayBoard();
			} else {
				p2_board_attack.displayBoard();
			}

			// input missile position
			System.out.println("choose a missile position to attack : ");
			do {
				missile = scanner.nextLine();
				missileCoord = new Coord(missile);
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
			for (int i = 0; i < game.opponentPlayer().getPlayerShips().size(); i++) {

				if (game.opponentPlayer().getPlayerShips().get(i)
						.isHit(missileCoord)) {

					System.out
							.println("You've hit a ship ! Your new board attack : ");

					// update and display boards
					if (game.getCurrentPlayer() == player1) {
						p2_board.updateBoardAttack(missileCoord, 1);
						p1_board_attack.updateBoardAttack(missileCoord, 1);
						p1_board_attack.displayBoard();
					} else {
						p1_board.updateBoardAttack(missileCoord, 1);
						p2_board_attack.updateBoardAttack(missileCoord, 1);
						p2_board_attack.displayBoard();
					}

					hit = true;
				}

				if (game.opponentPlayer().getPlayerShips().get(i).isDestroyed()) {

					System.out.println("You've DESTROYED the ship "
							+ game.opponentPlayer().getPlayerShips().get(i)
									.getName() + " !");
				}

				for (l = 0; l <= 25; l++) {
					System.out.println();
				}
			}

			if (!hit) {
				System.out
						.println("You haven't hit any ship ! Your new board attack : ");

				// update boards
				if (game.getCurrentPlayer() == player1) {
					p1_board_attack.updateBoardAttack(missileCoord, 0);
					p1_board_attack.displayBoard();
				} else {
					p2_board_attack.updateBoardAttack(missileCoord, 0);
					p2_board_attack.displayBoard();
				}
				for (l = 0; l <= 25; l++) {
					System.out.println();
				}
			}

			hit = false;

			game.changePlayer();
		}

		System.out.println(" Game ended ! The winner is "
				+ game.winnerEndGame() + " !");
	}
}
