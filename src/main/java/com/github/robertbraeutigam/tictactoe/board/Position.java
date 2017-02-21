package com.github.robertbraeutigam.tictactoe.view;

/**
 * A position of a cell on a two dimensional board, specified by
 * the usual X and Y coordinates.
 */
public final class Position {
   private final int x;
   private final int y;

   public Position(int x, int y) {
      this.x = x;
      this.y = y;
   }

   @Override
   public int hashCode() {
      return x + 13 * y;
   }

   @Override
   public boolean equals(Object o) {
      if ((o == null) || (!(o instanceof Position))) {
         return false;
      }
      Position p = (Position) o;
      return x == p.x && y == p.y;
   }

   @Override
   public String toString() {
      return "("+x+","+y+")";
   }
}

