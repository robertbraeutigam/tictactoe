package com.github.robertbraeutigam.tictactoe;

import java.io.PrintStream;

abstract class Player {
    private final PrintStream outStream;
    private final char characterSymbol;
    Player(PrintStream outStream, char characterSymbol) {
        this.outStream = outStream;
        this.characterSymbol = characterSymbol;
    }

    void displaySymbol() {
        outStream.print(Character.toString(characterSymbol));
    }

    boolean isSamePlayerAs(Player other) {
        return characterSymbol == other.characterSymbol;
    }

    abstract void doTurn();
}
