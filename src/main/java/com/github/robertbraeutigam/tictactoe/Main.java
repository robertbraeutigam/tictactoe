package com.github.robertbraeutigam.tictactoe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

import com.github.robertbraeutigam.tictactoe.Cell.CellFactory;
import com.github.robertbraeutigam.tictactoe.Cell.MultipleCellActions;
import com.github.robertbraeutigam.tictactoe.Position.PositionFactory;

public final class Main {
    public static void main(String[] args) {
        BufferedReader bufferedInput = new BufferedReader(new InputStreamReader(System.in));
        ThreadSleeper threadSleeper = new ThreadSleeper();
        Random random = new Random();
        MultipleCellActions multipleCellActions = new MultipleCellActions();
        PositionFactory positionFactory = new PositionFactory();
        CellFactory cellFactory = new CellFactory();
        Board board = new Board(System.out, random, multipleCellActions, cellFactory);
        Player player1 = new HumanPlayer(System.out, bufferedInput, positionFactory, board, 'X');
        Player player2 = new ComputerPlayer(System.out, threadSleeper, board, 'O');
        TurnTracker turnTracker = new TurnTracker(player1, player2);
        new Game(System.out, turnTracker, board).start();
    }
}
