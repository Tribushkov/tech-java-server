package utils;

import base.GameMechanics;

/**
 * Created by dmitri on 14.11.15.
 */
public class GameMechanicsRunnable implements Runnable {
    private GameMechanics gameMechanics;

    public GameMechanicsRunnable(GameMechanics gameMechanics){
        this.gameMechanics = gameMechanics;
    }

    @Override
    public void run() {
        gameMechanics.run();
    }
}
