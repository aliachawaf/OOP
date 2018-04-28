public class Display {

	private Player player;
	private char gameBoard[][];

	// constructor
	public Display(Player player, int mapSize) {
		this.player = player;
		this.gameBoard = new char[mapSize + 1][mapSize + 1];
	}

	// getters & setters
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public char[][] getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(char[][] gameBoard) {
		this.gameBoard = gameBoard;
	}

	// methods
	public void initBoard() {

		this.gameBoard[0][0] = ' ';
		char i = 'A';

		// first line : letters
		for (int j = 1; j < this.gameBoard.length; j++) {
			this.gameBoard[0][j] = i;
			i++;
		}

		// others lines : dots with the number of line at the beginning
		for (int k = 1; k < this.gameBoard.length; k++) {

			for (int l = 0; l < this.gameBoard.length; l++) {

				if (l == 0 && k != 10) {
					this.gameBoard[k][l] = Integer.toString(k).charAt(0);
				} else if (l == 0 && k == 10) {
					this.gameBoard[k][l] = ' ';
				} else {
					this.gameBoard[k][l] = '.';
				}
			}
		}
	}

	public void updateBoard(Ship s) {

		// ship s placed on the board is registered with 'o' instead of dots
		
		int column, line;
		
		for (int i = 0; i < s.shipListCoord().size(); i++) {

			column = (int) s.shipListCoord().get(i).getColumn() - 96;
			line = s.shipListCoord().get(i).getLine();

			this.gameBoard[line][column] = 'o';
		}
	}

	public void displayBoard() {

		for (int k = 0; k < this.gameBoard.length; k++) {
			System.out.println();
			for (int l = 0; l < this.gameBoard[k].length; l++) {
				System.out.print(" " + this.gameBoard[k][l]);
			}
		}
		System.out.println();
		System.out.println();
	}
	
	/*
	 * afficher map actuel du joueur 
	 * afficher map des tirs du joueurs sur la
	 * carte adverse
	 */

}
