package com.github.robertbraeutigam.tictactoe;

public interface Board {
   /**
    * @return True iff the board contains a full horizontal, vertical or diagonal row
    * of same marks.
    */
   boolean containsRow();

   /**
    * @return True iff there are no more empty spaces on the board.
    */
   boolean isFull();
}

