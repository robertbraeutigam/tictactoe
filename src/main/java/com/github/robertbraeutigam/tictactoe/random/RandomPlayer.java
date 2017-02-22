package com.github.robertbraeutigam.tictactoe.random;

import com.github.robertbraeutigam.tictactoe.Player;
import com.github.robertbraeutigam.tictactoe.View;
import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Player that just marks a random free spot.
 */
public final class RandomPlayer implements Player {
   private final View view;
   private final Random rnd;

   public RandomPlayer(View view) {
      this.view = view;
      this.rnd = new Random();
   }

   @Override
   public void makeMove() {
      List<View.Cell> emptyCells =
         view.cells().stream().filter(View.Cell::isEmpty).collect(Collectors.toList());
      emptyCells.get(rnd.nextInt(emptyCells.size())).mark();
   }
}
