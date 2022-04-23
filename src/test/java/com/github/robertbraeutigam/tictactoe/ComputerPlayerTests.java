package com.github.robertbraeutigam.tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.github.robertbraeutigam.tictactoe.Stubs.ActionStub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class ComputerPlayerTests {
    ByteArrayOutputStream outStreamContents;
    ThreadSleeper threadSleeper;
    Board board;
    Player player;

    @BeforeEach
    void init() {
        outStreamContents = new ByteArrayOutputStream();
        PrintStream outStream = new PrintStream(outStreamContents);
        threadSleeper = mock(ThreadSleeper.class);
        board = mock(Board.class);
        player = new ComputerPlayer(outStream, threadSleeper, board, 'Z');
    }
    
    @Test
    void logsCorrectStuffOutDuringTurn() {
        when(board.getRandomAvailableActions(player))
            .thenReturn(mock(ActionStub.class));

        player.doTurn();

        assertEquals(outStreamContents.toString(), "Computer's turn...\n");
    }

    @Test
    void callsStubsWithCorrectValues() throws InterruptedException {
        ActionStub actionStub = mock(ActionStub.class);
        when(board.getRandomAvailableActions(player))
            .thenReturn(actionStub);

        player.doTurn();

        verify(threadSleeper).sleepInSeconds(anyInt());
        verify(board).getRandomAvailableActions(player);
        verify(actionStub).run();
    }

    @Test
    void throwsIfSleepIsInterrupted() throws InterruptedException {
        doThrow(InterruptedException.class)
            .when(threadSleeper)
            .sleepInSeconds(anyInt());

        RuntimeException ex = assertThrows(RuntimeException.class, player::doTurn);

        assertTrue(ex.getCause() instanceof InterruptedException);
    }
}
