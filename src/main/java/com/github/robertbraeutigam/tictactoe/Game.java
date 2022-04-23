package com.github.robertbraeutigam.tictactoe;

import java.io.PrintStream;
import java.util.Optional;

final class Game {
    private final PrintStream outStream;
    private final TurnTracker turnTracker;
    private final Board board;
    Game(PrintStream outStream, TurnTracker turnTracker, Board board) {
        this.outStream = outStream;
        this.turnTracker = turnTracker;
        this.board = board;
    }

    void start() {
        while (true) {
            displayCurrentGameState();
            turnTracker.doCurrentPlayerTurn();

            Optional<EndGameState> maybeEndGameState = board.checkForEndOfGame();
            if (maybeEndGameState.isPresent()) {
                EndGameState endGameState = maybeEndGameState.orElseThrow();
                board.display();
                endGameState.display(outStream);
                break;
            }

            turnTracker.advanceToNextTurn();
        }
    }

    private void displayCurrentGameState() {
        outStream.println();
        turnTracker.displayCurrentPlayerSymbol();
        outStream.print("'s turn.\n");

        board.display();
    }
}
