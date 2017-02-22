package com.github.robertbraeutigam.tictactoe.human;

import com.github.robertbraeutigam.tictactoe.View;
import com.github.robertbraeutigam.tictactoe.Position;

public interface UI extends View.UI {
   Position askForMove();
}
