package com.github.robertbraeutigam.tictactoe.view;

import com.github.robertbraeutigam.tictactoe.Board;
import java.util.List;

/**
 * How a player sees the board. That is, there are cells on the board,
 * all of which are either free, taken by the enemy or owned by the player.
 */
public interface View extends Board {
   /**
    * @return All the cells on the board, from the perspective of this view.
    */
   List<Cell> cells();

   default Cell cellAt(Position position) {
      return cells().stream()
         .filter(cell -> cell.isAt(position))
         .findFirst()
         .orElseThrow(() -> new IllegalArgumentException("no cell at "+position));
   }

   @Override
   default boolean isFull() {
      return cells().stream().anyMatch(Cell::isEmpty);
   }

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
}


