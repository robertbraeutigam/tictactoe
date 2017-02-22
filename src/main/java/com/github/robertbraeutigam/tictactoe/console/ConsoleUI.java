package com.github.robertbraeutigam.tictactoe.console;

import com.github.robertbraeutigam.tictactoe.human.UI;
import com.github.robertbraeutigam.tictactoe.Position;
import static java.util.Arrays.asList;
import java.util.stream.Collectors;

public final class ConsoleUI implements UI {
   private final Character mineMark;
   private final Character enemysMark;

   public ConsoleUI(Character mineMark, Character enemysMark) {
      this.mineMark = mineMark;
      this.enemysMark = enemysMark;
   }

   @Override
   public void drawBoard(Mark[][] board) {
      asList(board).forEach(this::drawRow);
   }

   private void drawRow(Mark[] row) {
      System.console().printf(
            asList(row).stream()
            .map(this::markToDisplayMark)
            .collect(Collectors.joining("  "))
         +"\n");
   }

   private String markToDisplayMark(Mark mark) {
      switch (mark) {
         case MINE:
            return ""+mineMark;
         case ENEMYS:
            return ""+enemysMark;
         default:
            return ".";
      }
   }

   @Override
   public Position askForMove() {
      String positionInput = System.console().readLine("Enter your move '"+mineMark+"' (x,y): ");
      return new Position(Integer.parseInt(positionInput.split(",")[0]), Integer.parseInt(positionInput.split(",")[1]));
   }
}
