package com.github.robertbraeutigam.tictactoe;

class Position {
    private final Board board;
    private final int x;
    private final int y;
    Position(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
    }

    void setOccupiedBy(Player player) throws SpotAlreadyOccupiedException {
        board.occupySpot(x, y, player);
    }

    static class PositionFactory {
        Position createPosition(Board board, int x, int y) {
            return new Position(board, x, y);
        }
    }
}
