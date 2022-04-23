package com.github.robertbraeutigam.tictactoe;

import java.io.PrintStream;

final class ComputerPlayer extends Player {
    private final int COMPUTER_THINK_TIME = 1;
    private final PrintStream outStream;
    private final ThreadSleeper threadSleeper;
    private final Board board;
    ComputerPlayer(PrintStream outStream, ThreadSleeper threadSleeper, Board board, char characterSymbol) {
        super(outStream, characterSymbol);
        this.outStream = outStream;
        this.threadSleeper = threadSleeper;
        this.board = board;
    }

    public void doTurn() {
        outStream.print("Computer's turn...");
        try {
            threadSleeper.sleepInSeconds(COMPUTER_THINK_TIME);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        outStream.print("\n");

        board.getRandomAvailableActions(this).run();
    }
}
