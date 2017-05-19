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
      view.show(ui::draw);
      view.show(cells -> ui.askForMove().selectFrom(cells).mark());
   }
}

