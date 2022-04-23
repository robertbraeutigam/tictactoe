package com.github.robertbraeutigam.tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.github.robertbraeutigam.tictactoe.Cell.CellFactory;
import com.github.robertbraeutigam.tictactoe.Cell.MultipleCellActions;
import com.github.robertbraeutigam.tictactoe.Stubs.PlayerStub;
import com.github.robertbraeutigam.tictactoe.Stubs.ActionStub;
import com.github.robertbraeutigam.tictactoe.Stubs.EndGameStateStub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

final class BoardTests {
    ByteArrayOutputStream outStreamContents;
    PrintStream outStream;
    Random random;
    MultipleCellActions multipleCellActions;
    CellFactory cellFactory;
    Cell[][] cells;
    Board board;

    @BeforeEach
    void init() {
        outStreamContents = new ByteArrayOutputStream();
        outStream = new PrintStream(outStreamContents);
        random = mock(Random.class);
        multipleCellActions = mock(MultipleCellActions.class);
        cellFactory = mock(CellFactory.class);

        cells = new Cell[][] {
            new Cell[] {
                mock(Cell.class),
                mock(Cell.class),
                mock(Cell.class),
            },
            new Cell[] {
                mock(Cell.class),
                mock(Cell.class),
                mock(Cell.class),
            },
            new Cell[] {
                mock(Cell.class),
                mock(Cell.class),
                mock(Cell.class),
            },
        };

        when(cellFactory.createCell(outStream))
            .thenReturn(cells[0][0])
            .thenReturn(cells[0][1])
            .thenReturn(cells[0][2])
            .thenReturn(cells[1][0])
            .thenReturn(cells[1][1])
            .thenReturn(cells[1][2])
            .thenReturn(cells[2][0])
            .thenReturn(cells[2][1])
            .thenReturn(cells[2][2]);

        board = new Board(outStream, random, multipleCellActions, cellFactory);
    }

    @Test
    void displaysABoard() {
        Answer<Void> blankAnswer = new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                outStream.print(' ');
                return null;
            }
        };
        Answer<Void> zAnswer = new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                outStream.print('Z');
                return null;
            }
        };
        Answer<Void> yAnswer = new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                outStream.print('Y');
                return null;
            }
        };

        doAnswer(blankAnswer).when(cells[0][0]).display();
        doAnswer(zAnswer).when(cells[0][1]).display();
        doAnswer(yAnswer).when(cells[0][2]).display();
        doAnswer(zAnswer).when(cells[1][0]).display();
        doAnswer(yAnswer).when(cells[1][1]).display();
        doAnswer(blankAnswer).when(cells[1][2]).display();
        doAnswer(yAnswer).when(cells[2][0]).display();
        doAnswer(blankAnswer).when(cells[2][1]).display();
        doAnswer(zAnswer).when(cells[2][2]).display();

        board.display();

        String expectedOutput = String.join("\n",
            "",
            "  a b c",
            "       ",
            "1  |Z|Y",
            "  -+-+-",
            "2 Z|Y| ",
            "  -+-+-",
            "3 Y| |Z",
            "",
            ""
        );
        assertEquals(outStreamContents.toString(), expectedOutput);
    }

    @Test
    void occupiesASpot() throws SpotAlreadyOccupiedException {
        Player player = mock(PlayerStub.class);
        Action action = mock(ActionStub.class);
        when(cells[2][0].createOccupyAction(player))
            .thenReturn(action);

        board.occupySpot(0, 2, player);

        verify(action).run();
    }

    @Test
    void failsToOccupyAnAlreadyOccupiedSpot() throws SpotAlreadyOccupiedException {
        Player player = mock(PlayerStub.class);
        when(cells[2][0].createOccupyAction(player))
            .thenThrow(SpotAlreadyOccupiedException.class);

        assertThrows(SpotAlreadyOccupiedException.class, () -> board.occupySpot(0, 2, player));
    }

    @Test
    void failsWhenParamIsOutOfBounds() throws SpotAlreadyOccupiedException {
        Player player = mock(PlayerStub.class);
        assertThrows(IllegalArgumentException.class, () -> board.occupySpot(0, 3, player));
        assertThrows(IllegalArgumentException.class, () -> board.occupySpot(0, -1, player));
        assertThrows(IllegalArgumentException.class, () -> board.occupySpot(3, 0, player));
        assertThrows(IllegalArgumentException.class, () -> board.occupySpot(-1, 0, player));
    }

    @Test
    void getsRandomAvailableActions() {
        Player player = mock(PlayerStub.class);
        Action action1 = mock(ActionStub.class);
        Action action2 = mock(ActionStub.class);
        Action action3 = mock(ActionStub.class);
        Action action4 = mock(ActionStub.class);
        Action action5 = mock(ActionStub.class);

        when(cells[0][0].getAvailableActions(player)).thenReturn(Arrays.asList());
        when(cells[0][1].getAvailableActions(player)).thenReturn(Arrays.asList());
        when(cells[0][2].getAvailableActions(player)).thenReturn(Arrays.asList(action1));
        when(cells[1][0].getAvailableActions(player)).thenReturn(Arrays.asList());
        when(cells[1][1].getAvailableActions(player)).thenReturn(Arrays.asList(action2, action3));
        when(cells[1][2].getAvailableActions(player)).thenReturn(Arrays.asList(action4));
        when(cells[2][0].getAvailableActions(player)).thenReturn(Arrays.asList());
        when(cells[2][1].getAvailableActions(player)).thenReturn(Arrays.asList());
        when(cells[2][2].getAvailableActions(player)).thenReturn(Arrays.asList(action5));

        // We'll call getRandomAvailableActions() multiple times with different
        // random values, to ensure it's able to get them all.
        when(random.nextInt(5))
            .thenReturn(0)
            .thenReturn(1)
            .thenReturn(2)
            .thenReturn(3)
            .thenReturn(4);

        assertEquals(board.getRandomAvailableActions(player), action1);
        assertEquals(board.getRandomAvailableActions(player), action2);
        assertEquals(board.getRandomAvailableActions(player), action3);
        assertEquals(board.getRandomAvailableActions(player), action4);
        assertEquals(board.getRandomAvailableActions(player), action5);
    }

    @Test
    void throwsIfThereAreNoAvailableActions() {
        Player player = mock(PlayerStub.class);

        when(cells[0][0].getAvailableActions(player)).thenReturn(Arrays.asList());
        when(cells[0][1].getAvailableActions(player)).thenReturn(Arrays.asList());
        when(cells[0][2].getAvailableActions(player)).thenReturn(Arrays.asList());
        when(cells[1][0].getAvailableActions(player)).thenReturn(Arrays.asList());
        when(cells[1][1].getAvailableActions(player)).thenReturn(Arrays.asList());
        when(cells[1][2].getAvailableActions(player)).thenReturn(Arrays.asList());
        when(cells[2][0].getAvailableActions(player)).thenReturn(Arrays.asList());
        when(cells[2][1].getAvailableActions(player)).thenReturn(Arrays.asList());
        when(cells[2][2].getAvailableActions(player)).thenReturn(Arrays.asList());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> board.getRandomAvailableActions(player));
 
        assertEquals(ex.getMessage(), "There are no available actions to pick from.");
    }

    @Test
    void detectsCatScratch() {
        EndGameState endGameState = mock(EndGameStateStub.class);
        when(multipleCellActions.checkOccupiedBySamePlayer(any()))
            .thenReturn(Optional.empty());
        when(multipleCellActions.checkIfAllAreOccupied(any()))
            .thenReturn(Optional.of(endGameState));
        
        Optional<EndGameState> maybeResult = board.checkForEndOfGame();
        assertTrue(maybeResult.isPresent());
        assertEquals(maybeResult.orElseThrow(), endGameState);

        verify(multipleCellActions).checkIfAllAreOccupied(Arrays.asList(
            cells[0][0], cells[0][1], cells[0][2],
            cells[1][0], cells[1][1], cells[1][2],
            cells[2][0], cells[2][1], cells[2][2]
        ));
    }

    @Test
    void detectsHorizontalThreeInARow() {
        EndGameState endGameState = mock(EndGameStateStub.class);
        when(multipleCellActions.checkOccupiedBySamePlayer(any()))
            .thenAnswer(invocation -> 
                invocation.getArgument(0, List.class).equals(Arrays.asList(cells[1][0], cells[1][1], cells[1][2]))
                    ? Optional.of(endGameState)
                    : Optional.empty()
            );
        when(multipleCellActions.checkIfAllAreOccupied(any()))
            .thenReturn(Optional.empty());
        
        Optional<EndGameState> maybeResult = board.checkForEndOfGame();
        assertTrue(maybeResult.isPresent());
        assertEquals(maybeResult.orElseThrow(), endGameState);
    }

    @Test
    void detectsVerticalThreeInARow() {
        EndGameState endGameState = mock(EndGameStateStub.class);
        when(multipleCellActions.checkOccupiedBySamePlayer(any()))
            .thenAnswer(invocation -> 
                invocation.getArgument(0, List.class).equals(Arrays.asList(cells[0][1], cells[1][1], cells[2][1]))
                    ? Optional.of(endGameState)
                    : Optional.empty()
            );
        when(multipleCellActions.checkIfAllAreOccupied(any()))
            .thenReturn(Optional.empty());
        
        Optional<EndGameState> maybeResult = board.checkForEndOfGame();
        assertTrue(maybeResult.isPresent());
        assertEquals(maybeResult.orElseThrow(), endGameState);
    }

    @Test
    void detectsNegativeDiagonalThreeInARow() {
        EndGameState endGameState = mock(EndGameStateStub.class);
        when(multipleCellActions.checkOccupiedBySamePlayer(any()))
            .thenAnswer(invocation -> 
                invocation.getArgument(0, List.class).equals(Arrays.asList(cells[0][0], cells[1][1], cells[2][2]))
                    ? Optional.of(endGameState)
                    : Optional.empty()
            );
        when(multipleCellActions.checkIfAllAreOccupied(any()))
            .thenReturn(Optional.empty());
        
        Optional<EndGameState> maybeResult = board.checkForEndOfGame();
        assertTrue(maybeResult.isPresent());
        assertEquals(maybeResult.orElseThrow(), endGameState);
    }

    @Test
    void detectsPositiveDiagonalThreeInARow() {
        EndGameState endGameState = mock(EndGameStateStub.class);
        when(multipleCellActions.checkOccupiedBySamePlayer(any()))
            .thenAnswer(invocation -> 
                invocation.getArgument(0, List.class).equals(Arrays.asList(cells[0][2], cells[1][1], cells[2][0]))
                    ? Optional.of(endGameState)
                    : Optional.empty()
            );
        when(multipleCellActions.checkIfAllAreOccupied(any()))
            .thenReturn(Optional.empty());
        
        Optional<EndGameState> maybeResult = board.checkForEndOfGame();
        assertTrue(maybeResult.isPresent());
        assertEquals(maybeResult.orElseThrow(), endGameState);
    }

    @Test
    void detectsThatMoreMovesAreAvailable() {
        when(multipleCellActions.checkOccupiedBySamePlayer(any()))
            .thenReturn(Optional.empty());
        when(multipleCellActions.checkIfAllAreOccupied(any()))
            .thenReturn(Optional.empty());
        
        Optional<EndGameState> maybeResult = board.checkForEndOfGame();
        assertTrue(maybeResult.isEmpty());
    }
}
