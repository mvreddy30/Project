package com.zork;

import java.util.HashMap;
import java.util.List;

public class WolfObstacle extends GlobalObstacle implements ZorkObstacle{

    /**
     * Constructor initialization
     * @param currentPlayer
     *          info of player
     *
     * @param positionHolder
     *          info of all postions in the map
     */
    public WolfObstacle(Player currentPlayer, HashMap<Integer, ZorkPosition> positionHolder) {
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
        if(verb.contains("ATTACK")) {
            currentPlayer.playerChoicesCount++;
            if(currentPlayer.ownedAssets.get("SWORD") == 0) {
                throw new Exception("First TAKEOUT the sword and attack...");
            }
            if(currentPlayer.ownedAssets.get("SWORD") == 2) {
                throw new Exception("First TAKEUP the sword and attack...");
            }
            if(this.obstacleTrials == 0 || this.obstacleTrials == 1){
                ZorkUtil.print("The wolf bashed you on your face.. The sword is on the ground.");
                currentPlayer.ownedAssets.put("SWORD", 2);
            }else if(this.obstacleTrials == this.hintTrialNumber){
                ZorkUtil.print("Now the wolf has jumped on you... Just kill it with Sword");
            } else if(this.obstacleTrials > this.hintTrialNumber) {
                ZorkUtil.print("The wolf is on you.. Stop attacking and just kill...");
            } else{
                ZorkUtil.print("You are almost killed by Wolf.. you are getting injured.. Try attacking...");
            }
            this.obstacleTrials++;
        }
        if(verb.contains("TAKEOUT") || verb.contains("TAKEUP")) {
            currentPlayer.playerChoicesCount++;
            if(currentPlayer.ownedAssets.get("SWORD") == 1) {
                throw new Exception("Sword is already with you...");
            }
            currentPlayer.ownedAssets.put("SWORD", 1);
            ZorkUtil.print("Taken sword.. Now attack...");
        }
        if(verb.contains("KILL")) {
            currentPlayer.playerChoicesCount++;
            if(this.obstacleTrials < this.hintTrialNumber) {
                throw new Exception("You can't kill a wolf directly without attacking...");
            }
            ZorkUtil.print("YEEEEESSSS... You have killed the wolf.. Move West.. It is a happy ending...");
            currentPlayer.isPlayerInObstacle = false;
            currentPlayer.isVictory = true;
        }
    }
}
