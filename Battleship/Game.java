public class Game {

	private Player player1;
	private Player player2;
	private Player currentPlayer;

	// contructor : by default, current player is player1
	public Game(Player player1, Player player2) {
		super();
		this.player1 = player1;
		this.player2 = player2;
		this.currentPlayer = player1;
	}

	// getters & setters
	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	// methods
	public Player opponentPlayer() {

		if (this.currentPlayer == this.player1) {
			return this.player2;
		} else {
			return this.player1;
		}
	}

	public void changePlayer() {
		this.currentPlayer = this.opponentPlayer();
	}

	public boolean gameNotEnded() {

		// if (this.currentPlayer().listShipDestroyed().size()==5 ????

		if (this.player1.listShipDestroyed().size() < 5
				|| this.player2.listShipDestroyed().size() < 5) {

			return true;

		} else {
			return false;
		}

	}

	public Player winnerEndGame() {

		if (this.player1.listShipDestroyed().size() == 5) {
			return this.player2;
		} else {
			return this.player1;
		}

	}

}
