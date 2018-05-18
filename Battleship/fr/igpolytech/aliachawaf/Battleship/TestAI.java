package fr.igpolytech.aliachawaf.Battleship;

import java.util.ArrayList;
import java.util.List;

public class TestAI {

	public static void main(String[] args) {

		/* set up 5 ships for each player */
		Ship carrier_0 = new Ship("carrier", 5);
		Ship battleship_0 = new Ship("battleship", 4);
		Ship cruiser_0 = new Ship("cruiser", 3);
		Ship submarine_0 = new Ship("submarine", 3);
		Ship destroyer_0 = new Ship("destroyer", 2);

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
		List<Ship> player0_ships = new ArrayList<Ship>();
		player0_ships.add(carrier_0);
		player0_ships.add(battleship_0);
		player0_ships.add(cruiser_0);
		player0_ships.add(submarine_0);
		player0_ships.add(destroyer_0);

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

		/* play artificial players game */

		int scoreAI0 = 0;
		int scoreAI1 = 0;
		int scoreAI2 = 0;
		int currentPlayer = 0;
		String winner;
		
		for (int i = 0; i < 100; i++) {
			System.out.print(i + " ");
			winner = AI0vsAI1.main(player0_ships, player1_ships, currentPlayer);
			
			
			if (winner.matches("Medium AI")){
				scoreAI1++;
			} else {
				scoreAI0++;
			}
			
			if (currentPlayer==0){
				currentPlayer=1;
			} else {
				currentPlayer=0;
			}
		}
		
		System.out.print(scoreAI0 + " " + scoreAI1);
	}

}
