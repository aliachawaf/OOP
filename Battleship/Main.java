import java.util.ArrayList;
import java.util.Arrays;
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
		ArrayList<Ship> player1_ships = new ArrayList(Arrays.asList(carrier_1,
				battleship_1, cruiser_1, submarine_1, destroyer_1));
		ArrayList<Ship> player2_ships = new ArrayList(Arrays.asList(carrier_2,
				battleship_2, cruiser_2, submarine_2, destroyer_2));

		// set up 2 players
		Player player1 = new Player(1, player1_ships);
		Player player2 = new Player(2, player2_ships);

		// set up a game
		Game game = new Game(player1, player2);

		// set up 2 displays of game board (size 10) for each player
		Display player1_board = new Display(player1, 10);
		Display player1_board2 = new Display(player1, 10);
		Display player2_board = new Display(player2, 10);
		Display player2_board2 = new Display(player2, 10);

		// initialization of boards
		player1_board.initBoard();
		player1_board2.initBoard();
		player2_board.initBoard();
		player2_board2.initBoard();

		// variables
		boolean inputNotFinished = true;
		String start;
		String end;

		// ask player 1 then 2 to place his ships
		while (inputNotFinished) {

			player1_board.displayBoard();

			System.out.println("********* PLAYER "
					+ game.getCurrentPlayer().getPlayerNumber() + " *********");
			
			for (int i = 0; i < game.getCurrentPlayer().getPlayerShips().size(); i++) {

				System.out.println("enter first and last position for your ship "
						+ game.getCurrentPlayer().getPlayerShips().get(i)
								.getName()
						+ " (size "
						+ game.getCurrentPlayer().getPlayerShips().get(i)
								.getSize() + ") :");

				// input coord
				start = scanner.nextLine();
				end = scanner.nextLine();

				// update ship coord
				game.getCurrentPlayer().getPlayerShips().get(i)
						.setStartCoord(start);
				game.getCurrentPlayer().getPlayerShips().get(i)
						.setEndCoord(end);
				
				// display new board
				player1_board.updateBoard(game.getCurrentPlayer()
						.getPlayerShips().get(i));
				player1_board.displayBoard();
			}

			if (game.getCurrentPlayer() == player2) {
				inputNotFinished = false;
			}

			game.changePlayer();

		}

		
		/*
		 * while (game.gameNotEnded()) {
		 * 
		 * }
		 */

		System.out.println("the winner is " + game.winnerEndGame());
	}

}
