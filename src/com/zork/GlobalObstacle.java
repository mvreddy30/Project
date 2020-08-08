package com.zork;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class GlobalObstacle {
    /**Stores the info of player*/
    public static Player currentPlayer;
    /**Stores the info of all positions*/
    public static HashMap<Integer, ZorkPosition> positionHolder;
    /**Stores the number of trials that player has put on specific obstacle*/
    public static Integer obstacleTrials = 0;
    /**Stores random value between 2 to 5 indicates number of trials to be given with hint*/
    public static Integer hintTrialNumber = null;

    /**
     * Constructor initialization
     *
     * @param currentPlayer
     *          info of player
     *
     * @param positionHolder
     *          info of all positions
     */
    public GlobalObstacle(Player currentPlayer, HashMap<Integer, ZorkPosition> positionHolder) {
        this.currentPlayer = currentPlayer;
        this.positionHolder = positionHolder;
        hintTrialNumber = ThreadLocalRandom.current().nextInt(2, 5);
    }
}
