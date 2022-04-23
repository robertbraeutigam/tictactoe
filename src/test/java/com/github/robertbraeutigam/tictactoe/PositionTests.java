package com.github.robertbraeutigam.tictactoe;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.github.robertbraeutigam.tictactoe.Position.PositionFactory;
import com.github.robertbraeutigam.tictactoe.Stubs.PlayerStub;

import org.junit.jupiter.api.Test;

final class PositionTests {
    @Test
    void occupiesASpot() throws SpotAlreadyOccupiedException {
        Board board = mock(Board.class);
        Player player = mock(PlayerStub.class);
        Position position = new Position(board, 2, 1);

        position.setOccupiedBy(player);

        verify(board).occupySpot(2, 1, player);
    }

    @Test
    void handlesSpotAlreadyOccupiedException() throws SpotAlreadyOccupiedException {
        Board board = mock(Board.class);
        Player player = mock(PlayerStub.class);
        doThrow(SpotAlreadyOccupiedException.class)
            .when(board).occupySpot(anyInt(), anyInt(), any());
        Position position = new Position(board, 3, 2);

        assertThrows(SpotAlreadyOccupiedException.class, () -> position.setOccupiedBy(player));
 
        verify(board).occupySpot(3, 2, player);
    }

    @Test
    void positionFactoryWorksWithoutErrors() {
        Board board = mock(Board.class);
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.createPosition(board, 1, 0);
    }
}
