package com.github.robertbraeutigam.tictactoe;

import java.util.List;

/**
 * How a player sees the board. That is, there are cells on the board,
 * all of which are either free, taken by the enemy or owned by the player.
 */
public interface View {
   /**
    * @return All the cells on the board, from the perspective of this view.
    */
   List<Cell> cells();

   default Cell cellAt(Position position) {
      return cells().stream()
         .filter(cell -> cell.isAt(position))
         .findFirst()
         .orElseThrow(() -> new IllegalArgumentException("no cell at "+position+", all cells were: "+cells()));
   }

   void draw(UI ui);

   interface Cell {
      /**
       * @return True iff the cell is at the given position.
       */
      boolean isAt(Position position);

      /**
       * @return True iff the cell is not taken by either player.
       */
      boolean isEmpty();

      /**
       * @return True iff the cell is taken by the player using this view.
       */
      boolean isMine();

      /**
       * @return True iff the cell is taken by the opponent.
       */
      boolean isEnemys();

      /**
       * Own this cell. This can only be done if empty. It not, exception is thrown.
       */
      void mark();
   }

   interface UI {
      /**
       * Draw the board. Board is a 2D array, with position (0,0) as first element, x axis
       * in the rows, y in columns.
       */
      void drawBoard(Mark[][] board);

      enum Mark {
         EMPTY, MINE, ENEMYS;
      }
   }
}


