package com.github.robertbraeutigam.tictactoe.main;

import com.github.robertbraeutigam.tictactoe.square.SquareBoard;
import com.github.robertbraeutigam.tictactoe.human.HumanPlayer;
import com.github.robertbraeutigam.tictactoe.console.ConsoleUI;
import com.github.robertbraeutigam.tictactoe.Game;
import com.github.robertbraeutigam.tictactoe.Board;

public final class GameBuilder {
   public Game build() {
      Board board = new SquareBoard(3);
      return new Game(board,
            new HumanPlayer(board.getPlayer1View(), new ConsoleUI('X', 'O')),
            new HumanPlayer(board.getPlayer2View(), new ConsoleUI('O', 'X')));
   }
}
