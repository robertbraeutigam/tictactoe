package com.github.robertbraeutigam.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import com.github.robertbraeutigam.tictactoe.Position.PositionFactory;

final class HumanPlayer extends Player {
    private final PrintStream outStream;
    private final BufferedReader bufferedInput;
    private final PositionFactory positionFactory;
    private final Board board;
    HumanPlayer(
        PrintStream outStream,
        BufferedReader bufferedInput,
        PositionFactory positionFactory,
        Board board,
        char characterSymbol
    ) {
        super(outStream, characterSymbol);
        this.bufferedInput = bufferedInput;
        this.outStream = outStream;
        this.positionFactory = positionFactory;
        this.board = board;
    }

    public void doTurn() {
        while (true) {
            outStream.print("Choose a location (e.g. b2): ");
            String input;
            try {
                input = bufferedInput.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            Position position;
            try {
                position = parseUserInput(input);
            } catch (InvalidInputException ex) {
                outStream.println("Please supply a single letter (a, b, or c) followed by a single number (1, 2, or 3).");
                continue;
            }

            try {
                position.setOccupiedBy(this);
            } catch (SpotAlreadyOccupiedException ex) {
                outStream.println("The provided position is already occupied.");
                continue;
            }

            break;
        }
    }

    private Position parseUserInput(String input) throws InvalidInputException {
        if (input.length() != 2) throw new InvalidInputException();
        char letter = input.toLowerCase().charAt(0);
        char number = input.charAt(1);
        
        int x;
        if (letter == 'a') x = 0;
        else if (letter == 'b') x = 1;
        else if (letter == 'c') x = 2;
        else throw new InvalidInputException();
        
        int y;
        if (number == '1') y = 0;
        else if (number == '2') y = 1;
        else if (number == '3') y = 2;
        else throw new InvalidInputException();

        return positionFactory.createPosition(board, x, y);
    }

    private class InvalidInputException extends Exception {}
}
