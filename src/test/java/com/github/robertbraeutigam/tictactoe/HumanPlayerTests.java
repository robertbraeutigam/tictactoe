package com.github.robertbraeutigam.tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.github.robertbraeutigam.tictactoe.Position.PositionFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

final class HumanPlayerTests {
    ByteArrayOutputStream outStreamContents;
    PrintStream outStream;
    BufferedReader inputStream;
    PositionFactory positionFactory;
    Board board;
    Player player;

    @BeforeEach
    void init() {
        outStreamContents = new ByteArrayOutputStream();
        outStream = new PrintStream(outStreamContents);
        inputStream = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("".getBytes())));
        inputStream = mock(BufferedReader.class);
        positionFactory = mock(PositionFactory.class);
        board = mock(Board.class);
        player = new HumanPlayer(outStream, inputStream, positionFactory, board, 'Z');
    }

    @Test
    void handlesProperUserInput() throws IOException, SpotAlreadyOccupiedException {
        when(inputStream.readLine()).thenReturn("a3");
        Position position = mock(Position.class);
        when(positionFactory.createPosition(any(), anyInt(), anyInt())).thenReturn(position);

        player.doTurn();

        verify(positionFactory).createPosition(board, 0, 2);
        verify(position).setOccupiedBy(player);
        assertEquals(outStreamContents.toString(), "Choose a location (e.g. b2): ");
    }

    @Test
    void doesCaseInsensitiveComparisons() throws IOException, SpotAlreadyOccupiedException {
        when(inputStream.readLine()).thenReturn("B1");
        Position position = mock(Position.class);
        when(positionFactory.createPosition(any(), anyInt(), anyInt())).thenReturn(position);

        player.doTurn();

        verify(positionFactory).createPosition(board, 1, 0);
        verify(position).setOccupiedBy(player);
        assertEquals(outStreamContents.toString(), "Choose a location (e.g. b2): ");
    }

    @Test
    void rethrowsIOException() throws IOException, SpotAlreadyOccupiedException {
        doThrow(IOException.class)
            .when(inputStream)
            .readLine();

        RuntimeException ex = assertThrows(RuntimeException.class, player::doTurn);

        assertTrue(ex.getCause() instanceof IOException);
    }

    @Test
    void handlesInvalidInput() throws IOException, SpotAlreadyOccupiedException {
        when(inputStream.readLine())
            .thenReturn("a111") // Too many characters
            .thenReturn("a") // Too few characters
            .thenReturn("d1") // Out of bounds
            .thenReturn("a4") // Out of bounds
            .thenReturn("a0") // Out of bounds
            .thenReturn("c2"); // Valid

        Position position = mock(Position.class);
        when(positionFactory.createPosition(any(), anyInt(), anyInt())).thenReturn(position);

        player.doTurn();

        String expectedOutput = String.join("\n",
            "Choose a location (e.g. b2): "
            + "Please supply a single letter (a, b, or c) followed by a single number (1, 2, or 3).",
            "Choose a location (e.g. b2): "
            + "Please supply a single letter (a, b, or c) followed by a single number (1, 2, or 3).",
            "Choose a location (e.g. b2): "
            + "Please supply a single letter (a, b, or c) followed by a single number (1, 2, or 3).",
            "Choose a location (e.g. b2): "
            + "Please supply a single letter (a, b, or c) followed by a single number (1, 2, or 3).",
            "Choose a location (e.g. b2): "
            + "Please supply a single letter (a, b, or c) followed by a single number (1, 2, or 3).",
            "Choose a location (e.g. b2): "
        );
        verify(positionFactory).createPosition(board, 2, 1);
        verify(position).setOccupiedBy(player);
        assertEquals(outStreamContents.toString(), expectedOutput);
    }

    @Test
    void handlesOccupiedSpots() throws IOException, SpotAlreadyOccupiedException {
        when(inputStream.readLine())
            .thenReturn("a2")
            .thenReturn("b3");
        Position position = mock(Position.class);
        when(positionFactory.createPosition(any(), anyInt(), anyInt())).thenReturn(position);
        doThrow(SpotAlreadyOccupiedException.class)
            .doNothing()
            .when(position).setOccupiedBy(player);

        player.doTurn();

        String expectedOutput = String.join("\n",
            "Choose a location (e.g. b2): "
            + "The provided position is already occupied.",
            "Choose a location (e.g. b2): "
        );
        InOrder ordered = inOrder(positionFactory, position);
        ordered.verify(positionFactory).createPosition(board, 0, 1);
        ordered.verify(position).setOccupiedBy(player);
        ordered.verify(positionFactory).createPosition(board, 1, 2);
        ordered.verify(position).setOccupiedBy(player);
        assertEquals(outStreamContents.toString(), expectedOutput);
    }
}
