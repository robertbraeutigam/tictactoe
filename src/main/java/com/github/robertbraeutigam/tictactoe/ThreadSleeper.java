package com.github.robertbraeutigam.tictactoe;

import java.util.concurrent.TimeUnit;

class ThreadSleeper {
    void sleepInSeconds(int seconds) throws InterruptedException {
        TimeUnit.SECONDS.sleep(seconds);
    }
}
