package com.github.robertbraeutigam.tictactoe;

import java.util.function.Consumer;

/**
 * How a player sees the board. That is, there are cells on the board,
 * all of which are either free, taken by the enemy or owned by the player.
 */
public interface View {
   void show(Consumer<Cell[][]> cellConsumer);

   interface Cell {
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
      default boolean isEnemys() {
          return !isMine() && !isEmpty();
      }

      /**
       * Own this cell. This can only be done if empty. It not, exception is thrown.
       */
      void mark();
   }
}


