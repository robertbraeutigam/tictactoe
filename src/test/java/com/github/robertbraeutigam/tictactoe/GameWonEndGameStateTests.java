package com.github.robertbraeutigam.tictactoe;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class GameWonEndGameStateTests {
    class MinimalPlayerFake extends Player {
        MinimalPlayerFake() {
            super(null, '\0');
        }

        @Override
        void displaySymbol() {
            outStream.write('Z');
        }

        @Override
        void doTurn() {}
    }

    ByteArrayOutputStream outStreamContents;
    PrintStream outStream;
    Player player;
    GameWonEndGameState endGameState;

    @BeforeEach
    void init() {
        outStreamContents = new ByteArrayOutputStream();
        outStream = new PrintStream(outStreamContents);
        player = new MinimalPlayerFake();
        endGameState = new GameWonEndGameState(player);
    }

    @Test
    void writesMessageToOutput() {
        endGameState.display(outStream);

        assertEquals(outStreamContents.toString(), "Player Z won!\n");
    }
}
