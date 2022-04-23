package com.github.robertbraeutigam.tictactoe;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class CatScratchEndGameStateTests {
    ByteArrayOutputStream outStreamContents;
    PrintStream outStream;
    CatScratchEndGameState endGameState;

    @BeforeEach
    void init() {
        outStreamContents = new ByteArrayOutputStream();
        outStream = new PrintStream(outStreamContents);
        endGameState = new CatScratchEndGameState();
    }

    @Test
    void writesMessageToOutput() {
        endGameState.display(outStream);

        assertEquals(outStreamContents.toString(), "Cat Scratch!\n");
    }
}
