package chawaf.alia.Core;

public class Display {

	private char gameGrid[][];

	// constructor
	public Display(int gridSize) {
		this.gameGrid = new char[gridSize + 1][gridSize + 2];
		/*
		 * we add one more line to display the letters ( on 1st line) we add 2
		 * more columns to display line numbers (on 1st and 2nd columns)
		 */
	}

	// getters & setters
	public char[][] getGameGrid() {
		return gameGrid;
	}

	public void setGameGrid(char[][] gameGrid) {
		this.gameGrid = gameGrid;
	}

	// methods
	public void initGrid() {
		
		/*
		 * initializes the grid with letters for columns, line numbers and dots
		 * all over it
		 */
		
		/* 1st line is used to display letters
		 * 1st and 2nd columns are used to display line number
		 * So there is a blank on 1st and 2nd column of the 1st line
		 */
		this.gameGrid[0][0] = ' ';
		this.gameGrid[0][1] = ' ';
		
		// first line : letters
		char i = 'A';
		for (int j = 2; j <= this.gameGrid.length; j++) {
			this.gameGrid[0][j] = i;
			i++;
		}

		// others lines : dots with the number of line at the beginning
		for (int k = 1; k < this.gameGrid.length; k++) {

			for (int l = 0; l <= this.gameGrid.length; l++) {

				if (l == 0 && k < 10) {
					this.gameGrid[k][l] = ' ';
				} else if ((l == 0 && k >= 10) || (l == 1 && k < 10)) {
					this.gameGrid[k][l] = Integer.toString(k).charAt(0);
				} else if (l == 1 && k >= 10) {
					this.gameGrid[k][l] = Integer.toString(k).charAt(1);
				} else {
					this.gameGrid[k][l] = '.';
				}
			}
		}
	}

	public void updateGrid(Ship s) {

		/*
		 * the ship s placed on the grid is registered with many 'o' instead of
		 * dots
		 */

		int column, line;
		
		for (int i = 0; i < s.shipListCoord().size(); i++) {

			column = (int) Character.toLowerCase(s.shipListCoord().get(i)
					.getColumn()) - 96 + 1; // we add 1 because we use 2 columns
											// to display line number
			line = s.shipListCoord().get(i).getLine();

			this.gameGrid[line][column] = '\u25ef';
		}
	}

	public void updateGridAttack(Coord missile, int hit) {

		// updates grid attack when a player has sent a missile

		/*
		 * hit==1 means the player attacking has hit a ship. Then, this ship is
		 * registered with an 'x' instead of a dot on missile position
		 */
		if (hit == 1) {
			this.gameGrid[missile.getLine()][(int)Character.toLowerCase(missile.getColumn()) - 96 + 1] = '\u26d2';
		}
		/*
		 * else, hit==0 means the player attacking hasn't hit any ship. 
		 * Then, an 'o' is put instead of a dot on missile position
		 */
		else {
			this.gameGrid[missile.getLine()][(int) Character
					.toLowerCase(missile.getColumn()) - 96 + 1] = '\u26cc';
		}
	}

	@Override
	public String toString() {

		String gridDisplay = "";
		
		for (int k = 0; k < this.gameGrid.length; k++) {
			gridDisplay = gridDisplay.concat("\n");
			for (int l = 0; l < this.gameGrid[k].length; l++) {
				if (l==1){
					gridDisplay = gridDisplay.concat("" + this.gameGrid[k][l]);}
				else {
					gridDisplay = gridDisplay.concat(" " + this.gameGrid[k][l]);
				}
			}
		}
		gridDisplay = gridDisplay.concat("\n\n");
		return gridDisplay;
	}
}
