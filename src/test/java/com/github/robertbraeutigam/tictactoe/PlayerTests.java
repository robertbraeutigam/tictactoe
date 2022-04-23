package com.github.robertbraeutigam.tictactoe;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class PlayerTests {
    static class MinimalPlayerStub extends Player {
        MinimalPlayerStub(PrintStream outStream, char characterSymbol) {
            super(outStream, characterSymbol);
        }

        @Override
        void doTurn() {}
    }

    ByteArrayOutputStream outStreamContents;
    PrintStream outStream;
    MinimalPlayerStub player;

    @BeforeEach
    void init() {
        outStreamContents = new ByteArrayOutputStream();
        outStream = new PrintStream(outStreamContents);
        player = new MinimalPlayerStub(outStream, 'Z');
    }

    @Test
    void displaysTheProvidedSymbol() {
        player.displaySymbol();

        assertEquals(outStreamContents.toString(), "Z");
    }

    @Test
    void comparesPlayersWithSameSymbolAsTheSame() {
        PrintStream anotherOutStream = new PrintStream(outStreamContents);
        MinimalPlayerStub anotherPlayer = new MinimalPlayerStub(anotherOutStream, 'Z');

        boolean isSame = player.isSamePlayerAs(anotherPlayer);

        assertTrue(isSame);
    }

    @Test
    void comparesPlayersWithDifferentSymbolAsDifferent() {
        MinimalPlayerStub anotherPlayer = new MinimalPlayerStub(outStream, 'W');

        boolean isSame = player.isSamePlayerAs(anotherPlayer);

        assertFalse(isSame);
    }
}
