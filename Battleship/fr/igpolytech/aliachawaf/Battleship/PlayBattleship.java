package fr.igpolytech.aliachawaf.Battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayBattleship {

	private static Scanner scanner;

	public static void main(String[] args) {

		scanner = new Scanner(System.in);

		System.out
				.println(" 1 : Human VS Human \n 2 : Human VS ArtificialPlayer \n");
		System.out.print("Enter the number of the game you want to play : ");

		int game = scanner.nextInt();

		/* input mapSize */
		int mapSize = 10;
		boolean checkNotException = false;

		System.out
				.print("\nEnter the map size you want between 10-25 (ex : enter 10 to have a map 10x10) : ");

		while (!checkNotException) {
			try {
				do {
					mapSize = scanner.nextInt();
					scanner.nextLine();

					if (mapSize < 10 || mapSize > 25) {
						System.out
								.print("\nThe size you've entered is not between 10 and 25 ! Re-enter it : ");
					}

				} while (mapSize < 10 || mapSize > 25);

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
		List<Ship> player1_ships = new ArrayList<Ship>();
		player1_ships.add(carrier_1);
		player1_ships.add(battleship_1);
		player1_ships.add(cruiser_1);
		player1_ships.add(submarine_1);
		player1_ships.add(destroyer_1);

		List<Ship> player2_ships = new ArrayList<Ship>();
		player2_ships.add(carrier_2);
		player2_ships.add(battleship_2);
		player2_ships.add(cruiser_2);
		player2_ships.add(submarine_2);
		player2_ships.add(destroyer_2);

		String addShip, nameShipToAdd;
		int sizeShipToAdd;
		

		/* ask if want to add a ship to the game */
		System.out.println("\nCurrently, each player has 5 ships : "
				+ player1_ships);
		System.out
				.print("\nDo you want to add one more ship to each player (yes/no) ? ");

		do {
			addShip = scanner.nextLine();

			if (!addShip.matches("no") && !addShip.matches("yes")) {
				System.out
						.print("Your answer is different of 'no' or 'yes'. Re-enter it : ");
			}

		} while (!addShip.matches("no") && !addShip.matches("yes"));

		if (addShip.matches("yes")) {

			System.out.print("Enter its name : ");
			nameShipToAdd = scanner.nextLine();

			System.out.print("Enter its size : ");
			sizeShipToAdd = scanner.nextInt();

			Ship shipToAddPlayer1 = new Ship(nameShipToAdd, sizeShipToAdd);
			Ship shipToAddPlayer2 = new Ship(nameShipToAdd, sizeShipToAdd);

			player1_ships.add(shipToAddPlayer1);
			player2_ships.add(shipToAddPlayer2);
		}

		
		int level;
		
		if (game == 1) {
			HumanVSHuman.playBattleship(mapSize, player1_ships, player2_ships);
		} else if (game == 2) {

			System.out.println("\n 0 : easy \n 1 : medium \n 2 : hard\n");
			System.out
					.print("Enter the number of difficulty level you want : ");

			level = scanner.nextInt();

			if (level == 0) {
				HumanVSArtificial0.playBattleship(mapSize, player1_ships, player2_ships, 1);
			} else if (level == 1) {
				HumanVSArtificial1.playBattleship(mapSize, player1_ships, player2_ships, 1);
			} else {
				HumanVSArtificial2.playBattleship(mapSize, player1_ships, player2_ships, 1);
			}
		}

	}
}