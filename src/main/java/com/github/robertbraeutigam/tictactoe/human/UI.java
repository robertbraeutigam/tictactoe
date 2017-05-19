package com.github.robertbraeutigam.tictactoe.human;

import com.github.robertbraeutigam.tictactoe.View.Cell;

public interface UI {
   void draw(Cell[][] cells);

   Position askForMove();

   interface Position {
      Cell selectFrom(Cell[][] cells);
   }
}
