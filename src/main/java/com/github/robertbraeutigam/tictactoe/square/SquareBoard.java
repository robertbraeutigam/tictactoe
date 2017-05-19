package com.github.robertbraeutigam.tictactoe.square;

import com.github.robertbraeutigam.tictactoe.Board;
import com.github.robertbraeutigam.tictactoe.View;
import java.util.stream.IntStream;
import java.util.function.Consumer;
import static java.lang.Math.abs;

/**
 * A normal square shaped board.
 */
public final class SquareBoard implements Board {
   private final int size;
   // The board, 0 - Empty, (1) - Player1, (-1) - Player2
   private final int[] board;
   // The score (sum) of marks in given row (size horizontal rows, size vertical rows, 2 diagonal rows)
   private final int[] boardScore;

   public SquareBoard(int size) {
      this.size = size;
      this.board = new int[size*size];
      this.boardScore = new int[2 * size + 2];
   }

   @Override
   public View getPlayer1View() {
      return new SquareView(1);
   }

   @Override
   public View getPlayer2View() {
      return new SquareView(-1);
   }

   @Override
   public boolean containsRow() {
      return IntStream.of(boardScore).anyMatch(sum -> abs(sum) == size);
   }

   @Override
   public boolean isFull() {
      return IntStream.of(board).allMatch(mark -> mark != 0);
   }

   private final class SquareView implements View {
      private final int identity;

      private SquareView(int identity) {
         this.identity = identity;
      }

      @Override
      public void show(Consumer<Cell[][]> cellConsumer) {
          cellConsumer.accept(cells());
      }

      private Cell[][] cells() {
         Cell[][] cells = new Cell[size][size];
         for (int y=0; y<size; y++) {
            for (int x=0; x<size; x++) {
               int index = x+y*size;
               cells[y][x] = new SquareCell(index, identity);
            }
         }
         return cells;
      }
   }

   private final class SquareCell implements View.Cell {
      private final int index;
      private final int identity;
      private final int x;
      private final int y;

      public SquareCell(int index, int identity) {
         this.index = index;
         this.identity = identity;
         this.x = index % size;
         this.y = index / size;
      }

      @Override
      public boolean isEmpty() {
         return board[index] == 0;
      }

      @Override
      public boolean isMine() {
         return board[index] == identity;
      }

      @Override
      public String toString() {
         return "Cell "+board[index]+" at "+x+","+y;
      }

      @Override
      public void mark() {
         if (!isEmpty()) {
            throw new IllegalStateException("can not mark cell at ("+x+","+y+"), it is already taken");
         }
         board[index] = identity;
         boardScore[x] += identity;
         boardScore[size + y] += identity;
         if (x == y) {
            boardScore[2*size] += identity;
         }
         if (x+y == size-1) {
            boardScore[2*size+1] += identity;
         }
      }
   }
}

