package com.github.robertbraeutigam.tictactoe;

import java.io.PrintStream;

final class CatScratchEndGameState implements EndGameState {
    public void display(PrintStream outStream) {
        outStream.println("Cat Scratch!");
    }
}
