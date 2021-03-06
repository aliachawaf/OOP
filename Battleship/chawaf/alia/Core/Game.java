package chawaf.alia.Core;

import chawaf.alia.Player.Player;

public class Game {

	private Player player1;
	private Player player2;
	private Player currentPlayer;

	public Game(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
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

	public boolean NotEnded() {

		if (this.getCurrentPlayer().listShipDestroyed().size() == this.getCurrentPlayer().getPlayerShips().size()) {
			return false;
		} else {
			return true;
		}
	}

	public Player winnerEndGame() {
		
		if (this.player1.listShipDestroyed().size() == this.player1.getPlayerShips().size()) {
			return this.player2;
		} else {
			return this.player1;
		}
	}
}
