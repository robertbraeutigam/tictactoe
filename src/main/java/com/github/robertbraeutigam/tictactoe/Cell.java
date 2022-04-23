package com.github.robertbraeutigam.tictactoe;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class Cell {
    private final PrintStream outStream;
    private Optional<Player> occupant = Optional.empty();
    Cell(PrintStream outStream) {
        this.outStream = outStream;
    }

    Action createOccupyAction(Player activePlayer) throws SpotAlreadyOccupiedException {
        if (occupant.isPresent()) {
            throw new SpotAlreadyOccupiedException();
        }

        return () -> {
            if (occupant.isPresent()) {
                throw new RuntimeException("Can not occupy a spot that has already been occupied");
            }

            occupant = Optional.of(activePlayer);
        };
    }

    List<Action> getAvailableActions(Player activePlayer) {
        try {
            return Arrays.asList(createOccupyAction(activePlayer));
        } catch (SpotAlreadyOccupiedException ex) {
            return new ArrayList<Action>();
        }
    }

    void display() {
        occupant.ifPresentOrElse(
            player -> player.displaySymbol(),
            () -> outStream.print(" ")
        );
    }

    static class MultipleCellActions {
        Optional<EndGameState> checkOccupiedBySamePlayer(List<Cell> threeInARow) {
            if (threeInARow.size() != 3) {
                throw new IllegalArgumentException("This method is intended to operate on three-in-a-rows, but it received a list of a different size.");
            }
            if (threeInARow.get(0).occupant.isEmpty()) {
                return Optional.empty();
            }

            Player firstOccupant = threeInARow.get(0).occupant.orElseThrow();
            boolean winnerFound = threeInARow.stream().allMatch(cell -> (
                cell.occupant
                    .map(player -> firstOccupant.isSamePlayerAs(player))
                    .orElse(false)
            ));

            return winnerFound ? Optional.of(new GameWonEndGameState(firstOccupant)) : Optional.empty();
        }

        Optional<EndGameState> checkIfAllAreOccupied(List<Cell> cells) {
            boolean isCatScratch = cells.stream().allMatch(cell -> cell.occupant.isPresent());
            return isCatScratch ? Optional.of(new CatScratchEndGameState()) : Optional.empty();
        }
    }

    static class CellFactory {
        Cell createCell(PrintStream outStream) {
            return new Cell(outStream);
        }
    }
}
