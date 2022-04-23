package com.github.robertbraeutigam.tictactoe;

import java.io.PrintStream;

final class GameWonEndGameState implements EndGameState {
    private final Player player;
    GameWonEndGameState(Player player) {
        this.player = player;
    }

    public void display(PrintStream outStream) {
        outStream.print("Player ");
        player.displaySymbol();
        outStream.print(" won!\n");
    }
}
