package fr.battleship;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import chawaf.alia.Core.Ship;

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

		int currentPlayer = 0;
		int i;
		String winner;
		
		int score0 = 0;
		int score1 = 0;
		
		for( i = 0; i < 100; i++) {
			
			winner = AI0vsAI1.playBattleship(player0_ships, player1_ships, currentPlayer);
			
			/* update scores */
			if (winner.matches("Medium AI")){
				score1++;
			} else {
				score0++;
			}
			
			/* change first player for next game */
			if (currentPlayer==0){
				currentPlayer=1;
			} else {
				currentPlayer=0;
			}
		}
		
		int score2 = 0;
		int score3 = 0;
		currentPlayer = 0;
		
		for (i = 0; i < 100; i++) {
			
			winner = AI0vsAI2.playBattleship(player0_ships, player2_ships, currentPlayer);
			
			/* update scores */
			if (winner.matches("Hard AI")){
				score3++;
			} else {
				score2++;
			}
			
			/* change first player for next game */
			if (currentPlayer==0){
				currentPlayer=2;
			} else {
				currentPlayer=0;
			}
		}
		
		
		currentPlayer = 1;

		int score4 = 0;
		int score5 = 0;
		
		for (i = 0; i < 100; i++) {
			
			winner = AI1vsAI2.playBattleship(player1_ships, player2_ships, currentPlayer);
			
			/* update scores */
			if (winner.matches("Hard AI")){
				score5++;
			} else {
				score4++;
			}
			
			/* change first player for next game */
			if (currentPlayer==1){
				currentPlayer=2;
			} else {
				currentPlayer=1;
			}
		}
		
		try {
			FileWriter file = new FileWriter("fr/battleship/ai_proof.csv");
			file.append("AI NAME1; score1; AI NAME2; score2\n");
			file.append("AI Level Beginner; " + score0 + "; Level Medium; " + score1 + "\n" );
			file.append("AI Level Beginner; " + score2 + "; Level Hard; " + score3 + "\n");
			file.append("AI Level Medium; " + score4 + "; Level Medium; " + score5 + "\n");
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
