package com.github.robertbraeutigam.tictactoe;

class TurnTracker {
    private final Player player1;
    private final Player player2;
    private boolean isFirstPlayerTurn = true;
    TurnTracker(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    void doCurrentPlayerTurn() {
        Player currentPlayer = isFirstPlayerTurn ? player1 : player2;
        currentPlayer.doTurn();
    }

    void advanceToNextTurn() {
        isFirstPlayerTurn = !isFirstPlayerTurn;
    }

    void displayCurrentPlayerSymbol() {
        Player currentPlayer = isFirstPlayerTurn ? player1 : player2;
        currentPlayer.displaySymbol();
    }
}
