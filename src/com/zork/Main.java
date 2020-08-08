package com.zork;
public class Main {

    /**
     * Main method
     * @param args
     *      input args while starting game
     */
    public static void main(String[] args) {
	// write your code here
        com.zork.ZorkUtil.print("******WELCOME TO ZORK******");
        com.zork.ZorkUtil.print("Lets start with your details...");

        Player playerObject = new Player();
        Boolean playerInputTaken = playerObject.startTakingPlayerInfo();

        if(playerInputTaken) {
            ZorkUtil.print("Lets begin Zorking....");
            ZorkMain zorkMainObject = new ZorkMain(playerObject);
            zorkMainObject.startGame();
        }
    }
}
