package com.zork;

import java.util.HashMap;
import java.util.Map;

public class Player {
    /**stores the value whether pre details of player are given or not*/
    private Boolean isPlayerDetailsTaken = false;
    /**Stores the Name of player*/
    String playerName = null;
    /**Stores the Gender of player*/
    String playerGender = null;
    /**Stores the Age of player*/
    Integer playerAge = null;
    /**Stores the Birthdate of the player*/
    String playerBirthDate = null;
    /**Stores the inventory collected by player*/
    Map<String, Integer> ownedAssets = new HashMap<>();
    /**Stores the value whether game is finished or not*/
    Boolean isFinished = false;
    /**Stores the count of choices choosen by player while gaming*/
    Integer playerChoicesCount = 0;
    /**Stores the current position of player in Map*/
    ZorkPosition currentPosition = null;
    /**Stores the value whether the player is in obstacle*/
    Boolean isPlayerInObstacle = false;
    /**Stores the value of obstacle object of specific obstacle class*/
    ZorkObstacle obstacleObject = null;
    /**Stores the value of whether the player won the game or died in between*/
    Boolean isVictory = false;
    /**Stores the value whether the game is interrupted*/
    Boolean isInterrupted = false;

    /**
     * Starts taking input for player Info
     * @return
     */
    public boolean startTakingPlayerInfo() {
        TakePlayerInfoAndValidate(0);

        ZorkUtil.print("*****PLAYER DETAILS ARE*****");
        ZorkUtil.print("NAME:      "+ this.playerName);
        ZorkUtil.print("GENDER:      "+ this.playerGender);
        ZorkUtil.print("AGE:      "+ this.playerAge);
        ZorkUtil.print("DOB:      "+ this.playerBirthDate);
        ZorkUtil.print("*****************************");

        return isPlayerDetailsTaken;
    }

    /**
     * Starts taking the player info in console and validates
     * @param levelOfInfo
     *          this will be helpful for recursing to the same level when player enters some info wrong.
     */
    private void TakePlayerInfoAndValidate(int levelOfInfo) {
        switch (levelOfInfo) {
            case 0:
                ZorkUtil.print("Enter your good name:");
                String playerName = ZorkUtil.receiveValue().trim();
                if(playerName.isEmpty()){
                    ZorkUtil.print("Player name should not be empty...");
                    levelOfInfo = 0;
                    break;
                }
                this.playerName = playerName;
            case 1:
                ZorkUtil.print("Your gender: Enter 'M' for Male and 'F' for female:");
                String playerGender = ZorkUtil.receiveValue().trim();
                if(playerGender.isEmpty() || (!playerGender.equalsIgnoreCase("M") && !playerGender.equalsIgnoreCase("F"))) {
                    ZorkUtil.print("Gender is invalid...");
                    levelOfInfo = 1;
                    break;
                }
                this.playerGender = playerGender.equalsIgnoreCase("M") ? "Male" : "Female";
            case 2:
                ZorkUtil.print("Enter your age:");
                String playerAge = ZorkUtil.receiveValue().trim();
                try{
                    this.playerAge = Integer.parseInt(playerAge);
                }catch (Exception e){
                    ZorkUtil.print("Player age is not valid...");
                    levelOfInfo = 2;
                    break;
                }
            case 3:
                ZorkUtil.print("Enter your date of birth in format ddMMyy");
                String dateOfBirth = ZorkUtil.receiveValue().trim();
                if(dateOfBirth.isEmpty()){
                    ZorkUtil.print("Player date of birth should not be empty...");
                    levelOfInfo = 3;
                    break;
                }
                try{
                    Double.parseDouble(dateOfBirth);
                }catch (NumberFormatException e) {
                    ZorkUtil.print("Date entered is not in an expected format...");
                    levelOfInfo = 3;
                    break;
                }
                this.playerBirthDate = dateOfBirth;
            default:
                isPlayerDetailsTaken = true;
                break;
        }
        if(!isPlayerDetailsTaken) {
            TakePlayerInfoAndValidate(levelOfInfo);
        }
    }
}
