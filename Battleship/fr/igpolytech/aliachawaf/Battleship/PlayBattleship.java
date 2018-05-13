package fr.igpolytech.aliachawaf.Battleship;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import fr.igpolytech.aliachawaf.Battleship.HumanVSHuman;
import fr.igpolytech.aliachawaf.Battleship.HumanVSArtificial;

public class PlayBattleship {

	private static Scanner scanner;

	public static void main(String[] args) {
		
		scanner = new Scanner(System.in);
		
		System.out.println(" 1 : Human VS Human \n 2 : Human VS ArtificialPlayer \n 3 : Artificial VS Artificial\n");
		System.out.print("Enter the number of the game you want to play : ");
		
		int game = scanner.nextInt();
		
		/* input mapSize */
		int mapSize = 10;
		boolean checkNotException = false;

		System.out.print("\nEnter the map size you want between 5-25 (ex : enter 10 to have a map 10x10) : ");

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
		
		String size = Integer.toString(mapSize);
		
		
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

		
		//System.out.println("Currently, each player has 5 ships : " + player1_ships);
		
		//System.out.println("Do you want to add one more ship (yes/no) ? ");
		
		if (game == 1){
			HumanVSHuman.main(size, player1_ships, player2_ships);
		} else if (game == 2){
			HumanVSArtificial.main(size, player1_ships, player2_ships);
		}
		

	}

}