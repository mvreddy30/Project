package com.zork;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZorkActionImpl{
    /**The value is used to store the current player info */
    public static Player currentPlayer;
    /**The value is used to store all the positions(states) Info*/
    public static HashMap<Integer, ZorkPosition> positionHolder;
    /**The value is used to store input given by player*/
    public static List<String> inputList;

    /**Initializes an empty constructor */
    public ZorkActionImpl() {}

    /**
     * used to store following params for this object
     * @param inputList
     *          input given by player
     *
     * @param currentPlayer
     *          info of player
     *
     * @param positionHolder
     *          info of all positions
     */
    public ZorkActionImpl(List<String> inputList, Player currentPlayer, HashMap<Integer, ZorkPosition> positionHolder) {
        this.inputList = inputList;
        this.currentPlayer = currentPlayer;
        this.positionHolder = positionHolder;
    }

    /**
     *
     * @throws Exception
     */
    public void start() throws Exception{
        ZorkActionImpl implObject = ZorkActions.getExecutorFromName(this.inputList.get(0)); //0 refers to verb
        if(implObject == null) {
            if(currentPlayer.isPlayerInObstacle) {
                currentPlayer.obstacleObject.CrackObstacle(inputList);
                return;
            }
        }
        implObject.executeAction(this.inputList, this.currentPlayer, this.positionHolder);
    }

    /**
     * function for executing an given action which will be overriden by specific classes
     * @param inputList
     *          input given by player
     *
     * @param currentPlayer
     *          info of player
     *
     * @param positionHolder
     *      info of all positions
     * @throws Exception
     */
    public void executeAction(List<String> inputList, Player currentPlayer, HashMap<Integer, ZorkPosition> positionHolder) throws Exception{

    }

    /**
     * Checks whether specified condition is satisfied for the specified position
     *
     * @param position
     *          info of position
     *
     * @param condition
     *          info of condition
     * @return
     */
    public static Boolean isConditionSatisfied(ZorkPosition position, ZorkCondition condition) {
        if(condition.subject.equalsIgnoreCase("visitCount")) {
            Integer subject = position.visitCount;
            if(condition.operator.equalsIgnoreCase("gt")) {
                return subject > Integer.parseInt(condition.object);
            }
        }
        if(condition.subject.equalsIgnoreCase("asset")) {
            Map<String, Integer> subject = currentPlayer.ownedAssets;
            if(condition.operator.equalsIgnoreCase("has")) {
                return subject.containsKey(condition.object);
            }
        }
        if(condition.subject.equalsIgnoreCase("TORCH")) {
            Map<String, Integer> subject = currentPlayer.ownedAssets;
            if(condition.operator.equalsIgnoreCase("is")) {
                return subject.containsKey("TORCH") && subject.get("TORCH") == 1;
            }
        }
        if(condition.subject.equalsIgnoreCase("KEY")) {
            Map<String, Integer> subject = currentPlayer.ownedAssets;
            if(condition.operator.equalsIgnoreCase("is")) {
                return subject.containsKey("KEY") && subject.get("KEY") == 1;
            }
        }
        return false;
    }

    /**
     * to show the content of a specified position on the console
     *
     * @param position
     *          info of position
     */
    public static void showPositionContent(ZorkPosition position) {
        if(position.isObstacle) {
            currentPlayer.isPlayerInObstacle = true;
            if(position.positionId == 6) {
                currentPlayer.obstacleObject = new MireObstacle(currentPlayer, positionHolder);
            }else if(position.positionId == 14) {
                currentPlayer.obstacleObject = new WolfObstacle(currentPlayer, positionHolder);
            }
        }
        for(int index = 0; index<position.positionInfos.size();index++) {
            ZorkPositionInfo currentPositionInfo = position.positionInfos.get(index);
            if(currentPositionInfo.condition == null) {
                ZorkUtil.print(currentPositionInfo.message);
                break;
            }else {
                if(isConditionSatisfied(position, currentPositionInfo.condition)) {
                    ZorkUtil.print(currentPositionInfo.message);
                    break;
                }
            }
        }
    }
}

class MOVE extends ZorkActionImpl{
    /**
     *
     * @param inputList
     *          input given by player
     *
     * @param currentPlayer
     *          info of player
     *
     * @param positionHolder
     *      info of all positions
     * @throws Exception
     */
    public void executeAction(List<String> inputList, Player currentPlayer, HashMap<Integer, ZorkPosition> positionHolder) throws Exception{
        currentPlayer.playerChoicesCount++;
        if(currentPlayer.isPlayerInObstacle) {
            throw new Exception("You cant move when you are in obstacle..The only way out is THROUGH");
        }
        if(this.inputList.size() == 1) {
            throw new Exception("Where to move ???");
        }
        String object = this.inputList.get(1);
        if(!currentPlayer.currentPosition.positionDirections.containsKey(object)) {
            throw new Exception("You can't go that way");
        }
        if(currentPlayer.currentPosition.positionDirections.get(object).condition != null && !isConditionSatisfied(currentPlayer.currentPosition, currentPlayer.currentPosition.positionDirections.get(object).condition)) {
            ZorkUtil.print(currentPlayer.currentPosition.positionDirections.get(object).errorMsg);
            return;
        }
        Integer comingPositionId = currentPlayer.currentPosition.positionDirections.get(object).postionId;
        positionHolder.get(currentPlayer.currentPosition.positionId).visitCount++;
        currentPlayer.currentPosition = positionHolder.get(comingPositionId);
        showPositionContent(currentPlayer.currentPosition);
        if(currentPlayer.currentPosition.positionId == 15) {
            currentPlayer.isFinished = true;
        }
    }
}

class TAKE extends ZorkActionImpl{
    /**
     *
     * @param inputList
     *          input given by player
     *
     * @param currentPlayer
     *          info of player
     *
     * @param positionHolder
     *      info of all positions
     * @throws Exception
     */
    public void executeAction(List<String> inputList, Player currentPlayer, HashMap<Integer, ZorkPosition> positionHolder) throws Exception{
        currentPlayer.playerChoicesCount++;
        if(this.inputList.size() == 1) {
            throw new Exception("What to take ???");
        }
        String object = this.inputList.get(1);
        if(!currentPlayer.currentPosition.positionAssets.containsKey(object)){
            throw new Exception("There is no "+object);
        }
        if(this.currentPlayer.ownedAssets.containsKey(object) && !object.contains("COIN")) {
            throw new Exception("You have already taken "+object);
        }
        if(object.contains("COIN")) {
            if(!this.currentPlayer.ownedAssets.containsKey("COINS")) {
                this.currentPlayer.ownedAssets.put("COINS", 0);
            }
            Integer currentCoins = this.currentPlayer.ownedAssets.get("COINS");
            if(currentPlayer.currentPosition.positionId == 13) {
                if(currentCoins == 50 || currentCoins == 90) {
                    throw new Exception("You have already taken "+object);
                }
                this.currentPlayer.ownedAssets.put("COINS", currentCoins + 50);

            }else if(currentPlayer.currentPosition.positionId == 5) {
                if(currentCoins == 40 || currentCoins == 90) {
                    throw new Exception("You have already taken "+object);
                }
                this.currentPlayer.ownedAssets.put("COINS", currentCoins + 40);
            }
        }else{
            this.currentPlayer.ownedAssets.put(object, 0);
        }
        ZorkUtil.print(currentPlayer.currentPosition.positionActions.get("TAKE").message);
        return;
    }
}

class TURNON extends ZorkActionImpl{
    /**
     *
     * @param inputList
     *          input given by player
     *
     * @param currentPlayer
     *          info of player
     *
     * @param positionHolder
     *      info of all positions
     * @throws Exception
     */
    public void executeAction(List<String> inputList, Player currentPlayer, HashMap<Integer, ZorkPosition> positionHolder) throws Exception{
        currentPlayer.playerChoicesCount++;
        if(this.inputList.size() == 1) {
            throw new Exception("What to be turned on ???");
        }
        String object = this.inputList.get(1);
        if(!this.currentPlayer.ownedAssets.containsKey(object)) {
            throw new Exception("You dont have "+object + " to turn on...");
        }
        if(this.currentPlayer.ownedAssets.get(object) == 1) {
            throw new Exception(object + " is already turned on...");
        }
        this.currentPlayer.ownedAssets.put(object, 1);
        showPositionContent(currentPlayer.currentPosition);
        return;
    }
}

class TURNOFF extends ZorkActionImpl{
    /**
     *
     * @param inputList
     *          input given by player
     *
     * @param currentPlayer
     *          info of player
     *
     * @param positionHolder
     *      info of all positions
     * @throws Exception
     */
    public void executeAction(List<String> inputList, Player currentPlayer, HashMap<Integer, ZorkPosition> positionHolder) throws Exception {
        currentPlayer.playerChoicesCount++;
        if(this.inputList.size() == 1) {
            throw new Exception("What to be turned off ???");
        }
        String object = this.inputList.get(1);
        if(!this.currentPlayer.ownedAssets.containsKey(object)) {
            throw new Exception("You dont have "+object + " to turn off...");
        }
        if(this.currentPlayer.ownedAssets.get(object) == 0) {
            throw new Exception(object + " is already turned off...");
        }
        this.currentPlayer.ownedAssets.put(object, 0);
        showPositionContent(currentPlayer.currentPosition);
        return;
    }
}

class READ extends ZorkActionImpl {
    /**
     *
     * @param inputList
     *          input given by player
     *
     * @param currentPlayer
     *          info of player
     *
     * @param positionHolder
     *      info of all positions
     * @throws Exception
     */
    public void executeAction(List<String> inputList, Player currentPlayer, HashMap<Integer, ZorkPosition> positionHolder) throws Exception {
        if(currentPlayer.currentPosition.positionId == 7) {
            currentPlayer.playerChoicesCount++;
            ZorkUtil.print("HERE IT READS -> \"You find the envelope.. You can get the KEY\" and turning off the torch");
            this.currentPlayer.ownedAssets.put("TORCH", 0);
            return;
        }
    }
}

class FEED extends ZorkActionImpl{
    /**
     *
     * @param inputList
     *          input given by player
     *
     * @param currentPlayer
     *          info of player
     *
     * @param positionHolder
     *      info of all positions
     * @throws Exception
     */
    public void executeAction(List<String> inputList, Player currentPlayer, HashMap<Integer, ZorkPosition> positionHolder) throws Exception{
        currentPlayer.playerChoicesCount++;
        if(!this.currentPlayer.ownedAssets.containsKey("APPLE")){
            throw new Exception("You have nothing to feed...");
        }
        this.currentPlayer.ownedAssets.remove("APPLE");
        ZorkUtil.print("Feeded.");
        positionHolder.get(currentPlayer.currentPosition.positionId).visitCount++;
        currentPlayer.currentPosition = positionHolder.get(12);
        showPositionContent(currentPlayer.currentPosition);
        return;
    }
}

class OPEN extends ZorkActionImpl {
    /**
     *
     * @param inputList
     *          input given by player
     *
     * @param currentPlayer
     *          info of player
     *
     * @param positionHolder
     *      info of all positions
     * @throws Exception
     */
    public void executeAction(List<String> inputList, Player currentPlayer, HashMap<Integer, ZorkPosition> positionHolder) throws Exception{
        currentPlayer.playerChoicesCount++;
        this.currentPlayer.ownedAssets.put("KEY", 0);
        showPositionContent(currentPlayer.currentPosition);
    }
}

class UNLOCK extends ZorkActionImpl {
    /**
     *
     * @param inputList
     *          input given by player
     *
     * @param currentPlayer
     *          info of player
     *
     * @param positionHolder
     *      info of all positions
     * @throws Exception
     */
    public void executeAction(List<String> inputList, Player currentPlayer, HashMap<Integer, ZorkPosition> positionHolder) throws Exception{
        currentPlayer.playerChoicesCount++;
        this.currentPlayer.ownedAssets.put("KEY", 1);
        showPositionContent(currentPlayer.currentPosition);
    }
}

class BUY extends ZorkActionImpl {
    /**
     *
     * @param inputList
     *          input given by player
     *
     * @param currentPlayer
     *          info of player
     *
     * @param positionHolder
     *      info of all positions
     * @throws Exception
     */
    public void executeAction(List<String> inputList, Player currentPlayer, HashMap<Integer, ZorkPosition> positionHolder) throws Exception{
        currentPlayer.playerChoicesCount++;
        if(this.inputList.size() == 1) {
            throw new Exception("What to buy ???");
        }
        String object = this.inputList.get(1);
        if(this.currentPlayer.ownedAssets.containsKey("SWORD")) {
            throw new Exception("You have already bought "+ object);
        }
        if(!this.currentPlayer.ownedAssets.containsKey("COINS") || this.currentPlayer.ownedAssets.get("COINS") < 90) {
            throw new Exception("Not enough COINS to buy...");
        }
        this.currentPlayer.ownedAssets.put("SWORD", 0);
        this.currentPlayer.ownedAssets.remove("COINS");
        ZorkUtil.print(currentPlayer.currentPosition.positionActions.get("BUY").message);
    }
}
