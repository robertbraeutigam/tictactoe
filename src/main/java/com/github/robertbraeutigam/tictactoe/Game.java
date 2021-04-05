package com.github.robertbraeutigam.tictactoe;

/**
 * Main logic of the Tic-Tac-Toe game. It is played on a Board by
 * two Players, who make their move in turns until the board contains
 * a full row, or the board is full.
 */
public final class Game {
   private final Board board;
   private final Player[] players;
   private int turn = 0;

   /**
    * Construct a game with the given board and players. Player 1 will start the game.
    */
   public Game(Board board, Player player1, Player player2) {
      this.board = board;
      this.players = new Player[] { player1, player2 };
   }

   public void play() {
      while (!board.containsRow() && !board.isFull()) {
         nextPlayer().makeMove();
      }
   }

   private Player nextPlayer() {
      return players[(turn++) % players.length];
   }
}
