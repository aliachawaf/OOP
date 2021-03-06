package chawaf.alia;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import chawaf.alia.Core.Ship;
import chawaf.alia.PlayMode.HumanVSArtificial0;
import chawaf.alia.PlayMode.HumanVSArtificial1;
import chawaf.alia.PlayMode.HumanVSArtificial2;
import chawaf.alia.PlayMode.HumanVSHuman;


public class Battleship {

	private static Scanner scanner;

	public static void main(String[] args) {

		scanner = new Scanner(System.in);

		System.out
				.println(" 1 : Human VS Human \n 2 : Human VS ArtificialPlayer \n");
		System.out.print("Enter the number of the game you want to play : ");

		boolean checkNotException = false;
		int game = 2;

		while (!checkNotException) {
			try {
				do {
					game = scanner.nextInt();
					scanner.nextLine();

					if (game<1 || game>2) {
						System.out.print("\nThe game number you've entered is diffrent of 1 and 2 ! Re-enter it : ");
					}

				} while (game < 1 || game > 2);

				checkNotException = true;

			} catch (java.util.InputMismatchException e) {
				System.out.print("\nThe game number you've entered is not a number ! Re-enter it : ");
				scanner.nextLine();

			} 
		}		




		/* input gridSize */

		int gridSize = 10;

		checkNotException = false;

		System.out.print("\nEnter the grid size you want between 10-25 (ex : enter 10 to have a grid 10x10) : ");

		while (!checkNotException) {
			try {
				do {
					gridSize = scanner.nextInt();
					scanner.nextLine();

					if (gridSize < 10 || gridSize > 25) {
						System.out.print("\nThe size you've entered is not between 10 and 25 ! Re-enter it : ");
					}

				} while (gridSize < 10 || gridSize > 25);

				checkNotException = true;

			} catch (java.util.InputMismatchException e) {
				System.out.print("\nThe size you've entered is not a number ! Re-enter it : ");
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
		int sizeShipToAdd = 3;

		checkNotException = false;


		/* ask if want to add a ship to the game */

		System.out.println("\nCurrently, each player has 5 ships : " + player1_ships);
		System.out.print("\nDo you want to add one more ship to each player (yes/no) ? ");

		do {
			addShip = scanner.nextLine();

			if (!addShip.matches("no") && !addShip.matches("yes")) {
				System.out.print("Your answer is different of 'no' or 'yes'. Re-enter it : ");
			}

		} while (!addShip.matches("no") && !addShip.matches("yes"));


		if (addShip.matches("yes")) {

			System.out.print("Enter its name : ");
			nameShipToAdd = scanner.nextLine();

			System.out.print("Enter its size : ");

			do {
				try {
					// ask for the size of the ship he want to add

					do {
						sizeShipToAdd = scanner.nextInt();

						if (sizeShipToAdd < 2 || sizeShipToAdd > 5) {
							System.out.print("This size is not between 2 and 5. Re-enter it : ");
						}

					} while (sizeShipToAdd < 2 || sizeShipToAdd > 5);

					checkNotException = true;

				} catch (java.util.InputMismatchException e) {
					System.out.print("\nYour input is not a number ! Re-enter it : ");
					scanner.nextLine();
				}
			} while (!checkNotException);

			Ship shipToAddPlayer1 = new Ship(nameShipToAdd, sizeShipToAdd);
			Ship shipToAddPlayer2 = new Ship(nameShipToAdd, sizeShipToAdd);

			player1_ships.add(shipToAddPlayer1);
			player2_ships.add(shipToAddPlayer2);
		}

		int level = 0;

		switch (game) {

			case 1:
				HumanVSHuman.playBattleship(gridSize, player1_ships, player2_ships,	1);
			case 2:

				System.out.println("\n 0 : easy \n 1 : medium \n 2 : hard\n");
				System.out.print("Enter the number of difficulty level you want : ");

				checkNotException = false ;

				do {
					try {
						do {
							level = scanner.nextInt();
							scanner.nextLine();

							if (level < 0 || level > 2) {
								System.out.print("\nThe level you've entered is not between 0 and 2 ! Re-enter it : ");
							}

						} while (level < 0 || level > 2);

						checkNotException = true;

					} catch (java.util.InputMismatchException e) {
						System.out.print("\nThe level you've entered is not a number ! Re-enter it : ");
						scanner.nextLine();

					} catch (StringIndexOutOfBoundsException e) {
						System.out.print("The level is missing. Re-enter it :");
						scanner.nextLine();
					}
				} while (!checkNotException);


				switch (level) {
					case 0:
						HumanVSArtificial0.playBattleship(gridSize, player1_ships, player2_ships, 1);
					case 1:
						HumanVSArtificial1.playBattleship(gridSize, player1_ships, player2_ships, 1);
					case 2:
						HumanVSArtificial2.playBattleship(gridSize, player1_ships, player2_ships, 1);
				}
		}
	}
}
