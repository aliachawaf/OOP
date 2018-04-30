public class Display {

	private Player player;
	private char gameBoard[][];

	// constructor
	public Display(Player player, int mapSize) {
		this.player = player;
		this.gameBoard = new char[mapSize + 1][mapSize + 2];
		/*
		 * we add one more line to display the letters we add 2 more columns to
		 * display line numbers
		 */
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

		// initializes the board with letters for columns, line numbers and dots
		// all over it
		this.gameBoard[0][0] = ' ';
		this.gameBoard[0][1] = ' ';
		char i = 'A';

		// first line : letters
		for (int j = 2; j <= this.gameBoard.length; j++) {
			this.gameBoard[0][j] = i;
			i++;
		}

		// others lines : dots with the number of line at the beginning
		for (int k = 1; k < this.gameBoard.length; k++) {

			for (int l = 0; l <= this.gameBoard.length; l++) {

				if (l == 0 && k < 10) {
					this.gameBoard[k][l] = ' ';
				} else if (l == 0 && k >= 10) {
					this.gameBoard[k][l] = Integer.toString(k).charAt(0);
				} else if (l == 1 && k < 10) {
					this.gameBoard[k][l] = Integer.toString(k).charAt(0);
				} else if (l == 1 && k >= 10) {
					this.gameBoard[k][l] = Integer.toString(k).charAt(1);
				} else {
					this.gameBoard[k][l] = '.';
				}
			}
		}
	}

	public void updateBoard(Ship s) {

		// ship s placed on the board is registered with many 'o' instead of
		// dots

		int column, line;

		for (int i = 0; i < s.shipListCoord().size(); i++) {

			column = (int) Character.toLowerCase(s.shipListCoord().get(i)
					.getColumn()) - 96 + 1; // we add 1 because we use 2 columns
											// to display line number
			line = s.shipListCoord().get(i).getLine();

			this.gameBoard[line][column] = 'o';
		}
	}

	public void updateBoardAttack(Coord missile, int hit) {

		// updates boards attack when a player has sent a missile

		/*
		 * hit==1 means the player attacking has hit a ship. Then, this ship is
		 * registered with an 'x' instead of a dot on missile position
		 */
		if (hit == 1) {
			this.gameBoard[missile.getLine()][(int) Character
					.toLowerCase(missile.getColumn()) - 96 + 1] = 'x';
		}
		/*
		 * else, hit==0 means the player attacking hasn't hit a ship. Then, an
		 * 'o' is put instead of a dot on missile position
		 */
		else {
			this.gameBoard[missile.getLine()][(int) Character
					.toLowerCase(missile.getColumn()) - 96 + 1] = 'o';
		}
	}

	public void displayBoard() {

		// displays the board on console for the players
		for (int k = 0; k < this.gameBoard.length; k++) {
			System.out.println();
			for (int l = 0; l < this.gameBoard[k].length; l++) {
				System.out.print(" " + this.gameBoard[k][l]);
			}
		}
		System.out.println();
		System.out.println();
	}
}
