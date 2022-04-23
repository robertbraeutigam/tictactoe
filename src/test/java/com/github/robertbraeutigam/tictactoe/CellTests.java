package com.github.robertbraeutigam.tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

import com.github.robertbraeutigam.tictactoe.Cell.MultipleCellActions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class CellTests {
    class MinimalPlayerFake extends Player {
        MinimalPlayerFake() {
            super(null, '\0');
        }

        @Override
        void displaySymbol() {
            outStream.write('Z');
        }

        @Override
        boolean isSamePlayerAs(Player anotherPlayer) {
            return this == anotherPlayer;
        }

        @Override
        void doTurn() {}
    }

    ByteArrayOutputStream outStreamContents;
    PrintStream outStream;
    Cell cell;
    MultipleCellActions multipleCellActions;

    @BeforeEach
    void init() {
        outStreamContents = new ByteArrayOutputStream();
        outStream = new PrintStream(outStreamContents);
        cell = new Cell(outStream);
        multipleCellActions = new MultipleCellActions();
    }

    @Test
    void displaysASpaceWhenUnoccupied() {
        cell.display();

        assertEquals(outStreamContents.toString(), " ");
    }

    @Test
    void displaysPlayerSymbolWhenOccupied() throws SpotAlreadyOccupiedException {
        Player player = new MinimalPlayerFake();
        cell.createOccupyAction(player).run();

        cell.display();

        assertEquals(outStreamContents.toString(), "Z");
    }

    @Test
    void providesListWithAnActionWhenUnoccupied() {
        Player player = new MinimalPlayerFake();
        List<Action> actions = cell.getAvailableActions(player);

        assertEquals(actions.size(), 1);

        cell.display(); // Should output a space
        actions.get(0).run();
        cell.display(); // Should output a "Z"

        assertEquals(outStreamContents.toString(), " Z");
    }

    @Test
    void forbidsOccupyingAnAlreadyOccupiedCell() throws SpotAlreadyOccupiedException {
        Player player = new MinimalPlayerFake();
        cell.createOccupyAction(player).run();

        assertThrows(SpotAlreadyOccupiedException.class, () -> cell.createOccupyAction(player));
    }

    @Test
    void forbidsUsingAnExistingOccupyActionOnAnOccupiedSpot() throws SpotAlreadyOccupiedException {
        Player player = new MinimalPlayerFake();
        Action occupyAction = cell.createOccupyAction(player);

        occupyAction.run();
        RuntimeException ex = assertThrows(RuntimeException.class, occupyAction::run);

        assertEquals(ex.getMessage(), "Can not occupy a spot that has already been occupied");
    }

    @Test
    void ableToVerifyAllCellsAreOccupied() throws SpotAlreadyOccupiedException {
        Player player = new MinimalPlayerFake();
        Cell cell1 = cell;
        cell1.createOccupyAction(player).run();
        Cell cell2 = new Cell(outStream);
        cell2.createOccupyAction(player).run();

        Optional<EndGameState> maybeEndGameState = multipleCellActions.checkIfAllAreOccupied(List.of(cell1, cell2));

        assertTrue(maybeEndGameState.orElseThrow() instanceof CatScratchEndGameState);
    }

    @Test
    void ableToVerifySomeCellsAreUnoccupied() throws SpotAlreadyOccupiedException {
        Player player = new MinimalPlayerFake();
        Cell cell1 = cell;
        cell1.createOccupyAction(player).run();
        Cell cell2 = new Cell(outStream);

        Optional<EndGameState> maybeEndGameState = multipleCellActions.checkIfAllAreOccupied(List.of(cell1, cell2));

        assertTrue(maybeEndGameState.isEmpty());
    }

    @Test
    void ableToVerifyAllCellsHoldSamePlayer() throws SpotAlreadyOccupiedException {
        Player player = new MinimalPlayerFake();
        Cell cell1 = cell;
        cell1.createOccupyAction(player).run();
        Cell cell2 = new Cell(outStream);
        cell2.createOccupyAction(player).run();
        Cell cell3 = new Cell(outStream);
        cell3.createOccupyAction(player).run();

        Optional<EndGameState> maybeEndGameState = multipleCellActions.checkOccupiedBySamePlayer(List.of(cell1, cell2, cell3));

        assertTrue(maybeEndGameState.orElseThrow() instanceof GameWonEndGameState);
    }

    @Test
    void ableToNoticeFirstCellOfThreeInARowHasADifferentPlayer() throws SpotAlreadyOccupiedException {
        Player player1 = new MinimalPlayerFake();
        Player player2 = new MinimalPlayerFake();
        Cell cell1 = cell;
        cell1.createOccupyAction(player1).run();
        Cell cell2 = new Cell(outStream);
        cell2.createOccupyAction(player2).run();
        Cell cell3 = new Cell(outStream);
        cell3.createOccupyAction(player2).run();

        Optional<EndGameState> maybeEndGameState = multipleCellActions.checkOccupiedBySamePlayer(List.of(cell1, cell2, cell3));

        assertTrue(maybeEndGameState.isEmpty());
    }

    @Test
    void ableToNoticeFirstCellOfThreeInARowIsEmpty() throws SpotAlreadyOccupiedException {
        Player player = new MinimalPlayerFake();
        Cell cell1 = cell;
        Cell cell2 = new Cell(outStream);
        cell2.createOccupyAction(player).run();
        Cell cell3 = new Cell(outStream);
        cell3.createOccupyAction(player).run();

        Optional<EndGameState> maybeEndGameState = multipleCellActions.checkOccupiedBySamePlayer(List.of(cell1, cell2, cell3));

        assertTrue(maybeEndGameState.isEmpty());
    }

    @Test
    void ableToNoticeLastCellOfThreeInARowHasADifferentPlayer() throws SpotAlreadyOccupiedException {
        Player player1 = new MinimalPlayerFake();
        Player player2 = new MinimalPlayerFake();
        Cell cell1 = cell;
        cell1.createOccupyAction(player1).run();
        Cell cell2 = new Cell(outStream);
        cell2.createOccupyAction(player1).run();
        Cell cell3 = new Cell(outStream);
        cell3.createOccupyAction(player2).run();

        Optional<EndGameState> maybeEndGameState = multipleCellActions.checkOccupiedBySamePlayer(List.of(cell1, cell2, cell3));

        assertTrue(maybeEndGameState.isEmpty());
    }

    @Test
    void ableToNoticeLastCellOfThreeInARowIsEmpty() throws SpotAlreadyOccupiedException {
        Player player = new MinimalPlayerFake();
        Cell cell1 = cell;
        cell1.createOccupyAction(player).run();
        Cell cell2 = new Cell(outStream);
        cell2.createOccupyAction(player).run();
        Cell cell3 = new Cell(outStream);

        Optional<EndGameState> maybeEndGameState = multipleCellActions.checkOccupiedBySamePlayer(List.of(cell1, cell2, cell3));

        assertTrue(maybeEndGameState.isEmpty());
    }

    @Test
    void exactlyThreeCellsMustBeProvidedToComparePlayers() throws SpotAlreadyOccupiedException {
        Player player = new MinimalPlayerFake();
        Cell cell1 = cell;
        cell1.createOccupyAction(player).run();
        Cell cell2 = new Cell(outStream);
        cell2.createOccupyAction(player).run();

        assertThrows(
            IllegalArgumentException.class,
            () -> multipleCellActions.checkOccupiedBySamePlayer(List.of(cell1, cell2))
        );
    }
}
