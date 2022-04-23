package com.github.robertbraeutigam.tictactoe;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.github.robertbraeutigam.tictactoe.Cell.CellFactory;
import com.github.robertbraeutigam.tictactoe.Cell.MultipleCellActions;

class Board {
    private final PrintStream outStream;
    private final Random random;
    private MultipleCellActions multipleCellActions;
    private final int BOARD_SIZE = 3;
    private final Cell[][] contents;
    Board(PrintStream outStream, Random random, MultipleCellActions multipleCellActions, CellFactory cellFactory) {
        this.outStream = outStream;
        this.random = random;
        this.multipleCellActions = multipleCellActions;
        this.contents = new Cell[][]{
            new Cell[]{
                cellFactory.createCell(outStream),
                cellFactory.createCell(outStream),
                cellFactory.createCell(outStream)
            },
            new Cell[]{
                cellFactory.createCell(outStream),
                cellFactory.createCell(outStream),
                cellFactory.createCell(outStream)
            },
            new Cell[]{
                cellFactory.createCell(outStream),
                cellFactory.createCell(outStream),
                cellFactory.createCell(outStream)
            },
        };
        assert contents.length == BOARD_SIZE;
        assert contents[0].length == BOARD_SIZE;
    }

    void display() {
        outStream.println();
        outStream.println("  a b c");
        outStream.println("       ");
        for (int rowNum = 0; rowNum < BOARD_SIZE; ++rowNum) {
            displayRow(rowNum);
            if (rowNum != BOARD_SIZE - 1) {
                outStream.println("  -+-+-");
            }
        }
        outStream.println();
    }

    private void displayRow(int rowNum) {
        outStream.print(Integer.toString(rowNum + 1));
        outStream.print(" ");
        for (int i = 0; i < BOARD_SIZE; ++i) {
            contents[rowNum][i].display();
            if (i != BOARD_SIZE - 1) {
                outStream.print("|");
            }
        }
        outStream.print("\n");
    }

    void occupySpot(int x, int y, Player player) throws SpotAlreadyOccupiedException {
        if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) {
            throw new IllegalArgumentException("x or y out of bounds.");
        }
        contents[y][x].createOccupyAction(player).run();
    }

    Action getRandomAvailableActions(Player activePlayer) {
        List<Action> actions = Arrays.stream(contents)
            .flatMap(row -> Arrays.stream(row))
            .flatMap(cell -> cell.getAvailableActions(activePlayer).stream())
            .collect(Collectors.toList());
        
        if (actions.size() == 0) {
            throw new RuntimeException("There are no available actions to pick from.");
        }
        return actions.get(random.nextInt(actions.size()));
    }

    Optional<EndGameState> checkForEndOfGame() {
        return checkForCatScratch()
            .or(() -> checkForWin());
    }

    private Optional<EndGameState> checkForWin() {
        Cell[][] threeInARows = {
            {contents[0][0], contents[0][1], contents[0][2]},
            {contents[1][0], contents[1][1], contents[1][2]},
            {contents[2][0], contents[2][1], contents[2][2]},
            {contents[0][0], contents[1][0], contents[2][0]},
            {contents[0][1], contents[1][1], contents[2][1]},
            {contents[0][2], contents[1][2], contents[2][2]},
            {contents[0][0], contents[1][1], contents[2][2]},
            {contents[0][2], contents[1][1], contents[2][0]},
        };

        return Arrays.stream(threeInARows)
            .flatMap(threeInARow -> multipleCellActions.checkOccupiedBySamePlayer(Arrays.asList(threeInARow)).stream())
            .findAny();
    }

    private Optional<EndGameState> checkForCatScratch() {
        return multipleCellActions.checkIfAllAreOccupied(
            Arrays.stream(contents)
                .flatMap(row -> Arrays.stream(row))
                .collect(Collectors.toList())
        );
    }
}
