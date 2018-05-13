package fr.igpolytech.aliachawaf.Battleship;
public class Display {

	private char gameBoard[][];

	// constructor
	public Display(int mapSize) {
		this.gameBoard = new char[mapSize + 1][mapSize + 2];
		/*
		 * we add one more line to display the letters ( on 1st line) we add 2
		 * more columns to display line numbers (on 1st and 2nd columns)
		 */
	}

	// getters & setters
	public char[][] getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(char[][] gameBoard) {
		this.gameBoard = gameBoard;
	}

	// methods
	public void initBoard() {
		
		/*
		 * initializes the board with letters for columns, line numbers and dots
		 * all over it
		 */
		
		/* 1st line is used to display letters
		 * 1st and 2nd columns are used to display line number
		 * So there is a blank on 1st and 2nd column of the 1st line
		 */
		this.gameBoard[0][0] = ' ';
		this.gameBoard[0][1] = ' ';
		
		// first line : letters
		char i = 'A';
		for (int j = 2; j <= this.gameBoard.length; j++) {
			this.gameBoard[0][j] = i;
			i++;
		}

		// others lines : dots with the number of line at the beginning
		for (int k = 1; k < this.gameBoard.length; k++) {

			for (int l = 0; l <= this.gameBoard.length; l++) {

				if (l == 0 && k < 10) {
					this.gameBoard[k][l] = ' ';
				} else if ((l == 0 && k >= 10) || (l == 1 && k < 10)) {
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

		/*
		 * the ship s placed on the board is registered with many 'o' instead of
		 * dots
		 */

		int column, line;
		
		for (int i = 0; i < s.shipListCoord().size(); i++) {

			column = (int) Character.toLowerCase(s.shipListCoord().get(i)
					.getColumn()) - 96 + 1; // we add 1 because we use 2 columns
											// to display line number
			line = s.shipListCoord().get(i).getLine();

			this.gameBoard[line][column] = '\u25ef';
		}
	}

	public void updateBoardAttack(Coord missile, int hit) {

		// updates board attack when a player has sent a missile

		/*
		 * hit==1 means the player attacking has hit a ship. Then, this ship is
		 * registered with an 'x' instead of a dot on missile position
		 */
		if (hit == 1) {
			this.gameBoard[missile.getLine()][(int)Character.toLowerCase(missile.getColumn()) - 96 + 1] = '\u26d2';
		}
		/*
		 * else, hit==0 means the player attacking hasn't hit any ship. 
		 * Then, an 'o' is put instead of a dot on missile position
		 */
		else {
			this.gameBoard[missile.getLine()][(int) Character
					.toLowerCase(missile.getColumn()) - 96 + 1] = '\u26cc';
		}
	}

	@Override
	public String toString() {

		String boardDisplay = "";
		
		for (int k = 0; k < this.gameBoard.length; k++) {
			boardDisplay = boardDisplay.concat("\n");
			for (int l = 0; l < this.gameBoard[k].length; l++) {
				if (l==1){
					boardDisplay = boardDisplay.concat("" + this.gameBoard[k][l]);}
				else {
					boardDisplay = boardDisplay.concat(" " + this.gameBoard[k][l]);
				}
			}
		}
		boardDisplay = boardDisplay.concat("\n\n");
		return boardDisplay;
	}
}