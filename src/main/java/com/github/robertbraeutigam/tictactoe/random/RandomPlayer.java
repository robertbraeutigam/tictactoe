package com.github.robertbraeutigam.tictactoe.random;

import com.github.robertbraeutigam.tictactoe.Player;
import com.github.robertbraeutigam.tictactoe.View;
import com.github.robertbraeutigam.tictactoe.View.Cell;
import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.Arrays.asList;

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
      view.show(this::makeMove);
   }

   private void makeMove(Cell[][] cells) {
      Stream<Cell> cellStream = asList(cells).stream().flatMap(row -> asList(row).stream());
      Stream<Cell> emptyCellsStream = cellStream.filter(View.Cell::isEmpty);
      List<View.Cell> emptyCells = emptyCellsStream.collect(Collectors.toList());
      emptyCells.get(rnd.nextInt(emptyCells.size())).mark();
   }
}
