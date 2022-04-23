package com.github.robertbraeutigam.tictactoe;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.github.robertbraeutigam.tictactoe.Stubs.PlayerStub;

import static org.mockito.Mockito.never;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class TurnTrackerTests {
    Player player1;
    Player player2;
    TurnTracker turnTracker;

    @BeforeEach()
    void init() {
        player1 = mock(PlayerStub.class);
        player2 = mock(PlayerStub.class);
        turnTracker = new TurnTracker(player1, player2);
    }

    @Test
    void doesFirstPlayerTurn() {
        turnTracker.doCurrentPlayerTurn();

        verify(player1).doTurn();
        verify(player2, never()).doTurn();
    }

    @Test
    void doesSecondPlayerTurn() {
        turnTracker.advanceToNextTurn();
        turnTracker.doCurrentPlayerTurn();

        verify(player1, never()).doTurn();
        verify(player2).doTurn();
    }

    @Test
    void isFirstPlayersTurnAfterTwoTurnAdvancements() {
        turnTracker.advanceToNextTurn();
        turnTracker.advanceToNextTurn();
        turnTracker.doCurrentPlayerTurn();

        verify(player1).doTurn();
        verify(player2, never()).doTurn();
    }

    @Test
    void displaysFirstPlayerSymbol() {
        turnTracker.displayCurrentPlayerSymbol();

        verify(player1).displaySymbol();
        verify(player2, never()).displaySymbol();
    }

    @Test
    void displaysSecondPlayerSymbol() {
        turnTracker.advanceToNextTurn();
        turnTracker.displayCurrentPlayerSymbol();

        verify(player1, never()).displaySymbol();
        verify(player2).displaySymbol();
    }
}
