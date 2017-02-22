package com.github.robertbraeutigam.tictactoe.console;

import com.github.robertbraeutigam.tictactoe.human.UI;
import com.github.robertbraeutigam.tictactoe.Position;
import static java.util.Arrays.asList;

public final class ConsoleUI implements UI {
   private final Character mineMark;
   private final Character enemysMark;

   public ConsoleUI(Character mineMark, Character enemysMark) {
      this.mineMark = mineMark;
      this.enemysMark = enemysMark;
   }

   @Override
   public void drawBoard(Mark[][] board) {
      System.console().printf("TODO Board\n");
   }

   @Override
   public Position askForMove() {
      String positionInput = System.console().readLine("Enter your move (x,y): ");
      return new Position(Integer.parseInt(positionInput.split(",")[0]), Integer.parseInt(positionInput.split(",")[1]));
   }
}
