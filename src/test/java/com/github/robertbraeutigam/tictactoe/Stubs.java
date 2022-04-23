package com.github.robertbraeutigam.tictactoe;

import java.io.PrintStream;

final class Stubs {
    static class PlayerStub extends Player {
        PlayerStub() {
            super(null, '\0');
        }

        @Override
        void doTurn() {}
    }

    static class ActionStub implements Action {
        public void run() {}
    }

    static class EndGameStateStub implements EndGameState {
        public void display(PrintStream outStream) {}
    }
}
