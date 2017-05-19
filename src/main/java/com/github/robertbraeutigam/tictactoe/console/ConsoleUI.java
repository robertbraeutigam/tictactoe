package com.github.robertbraeutigam.tictactoe.console;

import com.github.robertbraeutigam.tictactoe.human.UI;
import com.github.robertbraeutigam.tictactoe.View.Cell;
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
   public void draw(Cell[][] cells) {
      asList(cells).forEach(this::drawRow);
   }

   private void drawRow(Cell[] row) {
      System.console().printf(
            asList(row).stream()
            .map(this::markToDisplayMark)
            .collect(Collectors.joining("  "))
         +"\n");
   }

   private String markToDisplayMark(Cell cell) {
      if (cell.isMine()) {
            return ""+mineMark;
      } else if (cell.isEnemys()) {
            return ""+enemysMark;
      } else {
            return ".";
      }
   }

   @Override
   public Position askForMove() {
      String positionInput = System.console().readLine("Enter your move '"+mineMark+"' (x,y): ");
      int x = Integer.parseInt(positionInput.split(",")[0]);
      int y = Integer.parseInt(positionInput.split(",")[1]);
      return new Position() {
         @Override
         public Cell selectFrom(Cell[][] cells) {
            return cells[y][x];
         }
      };
   }
}
