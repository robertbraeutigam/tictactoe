package com.github.robertbraeutigam.tictactoe;

public interface Board {
   /**
    * Get the view for this board for player 1.
    */
   View getPlayer1View();

   /**
    * Get the view for this board for player 2.
    */
   View getPlayer2View();

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

