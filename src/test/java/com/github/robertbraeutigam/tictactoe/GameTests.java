package com.github.robertbraeutigam.tictactoe;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

final class GameTests {
    static class EndGameStateFake implements EndGameState {
        public void display(PrintStream outStream) {
            outStream.println("GAME_OVER");
        }
    }

    ByteArrayOutputStream outStreamContents;
    PrintStream outStream;
    TurnTracker turnTracker;
    Board board;
    Game game;

    @BeforeEach
    void init() {
        outStreamContents = new ByteArrayOutputStream();
        outStream = new PrintStream(outStreamContents);
        turnTracker = mock(TurnTracker.class);
        board = mock(Board.class);
        game = new Game(outStream, turnTracker, board);

        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                outStream.print('Z');
                return null;
            }
        }).when(turnTracker).displayCurrentPlayerSymbol();
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                outStream.println("BOARD_CONTENTS");
                return null;
            }
        }).when(board).display();
    }

    @Test
    void displaysTheCorrectOutput() {
        when(board.checkForEndOfGame())
            .thenReturn(Optional.empty())
            .thenReturn(Optional.of(new EndGameStateFake()));

        game.start();

        String expectedOutput = String.join("\n",
            "",
            "Z's turn.",
            "BOARD_CONTENTS",
            "",
            "Z's turn.",
            "BOARD_CONTENTS",
            "BOARD_CONTENTS",
            "GAME_OVER",
            ""
        );

        assertEquals(outStreamContents.toString(), expectedOutput);
    }

    @Test
    void advancesToNextTurn() {
        when(board.checkForEndOfGame())
            .thenReturn(Optional.empty())
            .thenReturn(Optional.empty())
            .thenReturn(Optional.of(new EndGameStateFake()));

        game.start();

        verify(turnTracker, times(2)).advanceToNextTurn();
    }
}
