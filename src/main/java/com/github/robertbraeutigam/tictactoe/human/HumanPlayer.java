package com.github.robertbraeutigam.tictactoe.human;

import com.github.robertbraeutigam.tictactoe.Player;
import com.github.robertbraeutigam.tictactoe.View;

public final class HumanPlayer implements Player {
   private final View view;
   private final UI ui;

   public HumanPlayer(View view, UI ui) {
      this.view = view;
      this.ui = ui;
   }

   @Override
   public void makeMove() {
      view.draw(ui);
      view.cellAt(ui.askForMove()).mark();
   }
}

