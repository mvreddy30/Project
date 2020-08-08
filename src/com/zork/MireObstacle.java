package com.zork;

import java.util.HashMap;
import java.util.List;

public class MireObstacle extends GlobalObstacle implements ZorkObstacle {

    /**
     * initilizing constructor
     * @param currentPlayer
     *          info of player
     *
     * @param positionHolder
     *          info of all positions
     */
    public MireObstacle(Player currentPlayer, HashMap<Integer, ZorkPosition> positionHolder) {
        super(currentPlayer, positionHolder);
    }

    /**
     * method used to crack the given obstacle..
     * Every action when the player in the obstacle will be processed here
     * @param inputList
     *          given input by player while he is in obstacle
     * @throws Exception
     */
    public void CrackObstacle(List<String> inputList) throws Exception {
        String verb = inputList.get(0);
        if(verb.contains("TIE")) {
            currentPlayer.playerChoicesCount++;
            if(!currentPlayer.ownedAssets.containsKey("ROPE")) {
                throw new Exception("You dont have ROPE to tie.. You missed to take it... LOOSE LIFE or LOOSE POINTS ");
            }
            if(this.obstacleTrials == 0){
                ZorkUtil.print("Your aim is far away from tree. Try this time with little good aim...");
            }else if(this.obstacleTrials == this.hintTrialNumber){
                ZorkUtil.print("Nice throw... It is tied to tree.. Now pull yourself to shore...");
            } else if(this.obstacleTrials > this.hintTrialNumber){
                ZorkUtil.print("It is already tied.. Just pull yourself...");
            }else{
                ZorkUtil.print("Your aim is getting better. Keep concentrating and try...");
            }
            this.obstacleTrials++;
        }
        if(verb.contains("LOOSE")) {
            currentPlayer.playerChoicesCount++;
            String object = inputList.get(1);
            if(object.contains("LIFE")) {
                ZorkUtil.print("You are dead...");
                currentPlayer.isFinished = true;
            }else if(object.contains("POINTS")) {
                ZorkUtil.print("You have got the rope ..Try now tying... and choices count of 10 added to your profile...");
                currentPlayer.ownedAssets.put("ROPE", 0);
                currentPlayer.playerChoicesCount = currentPlayer.playerChoicesCount + 10;
            }
        }
        if(verb.contains("PULL")) {
            currentPlayer.playerChoicesCount++;
            if(this.obstacleTrials < this.hintTrialNumber) {
                throw new Exception("You cant pull yourself by not tying your rope to tree...");
            }
            ZorkUtil.print("YEEEEESSSS... You are out of the obstacle.. Achievment will be appreciated if you go WEST...");
            currentPlayer.isPlayerInObstacle = false;
            this.obstacleTrials = 0;
        }
    }
}
