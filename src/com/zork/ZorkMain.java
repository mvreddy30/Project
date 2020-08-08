package com.zork;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class ZorkMain{
    /**stores the info of player*/
    public Player currentPlayer = null;
    /**Stores the path of Route map file to be loaded*/
    public static String routeMapFile = ZorkConstants.postitionXMLFile;
    /**Stores info of all positions*/
    public HashMap<Integer, ZorkPosition> positionHolder = new HashMap<Integer, ZorkPosition>();
    /**Stores the input given by player*/
    public static List<String> inputList = null;

    /**
     * Constructor initialization
     * @param player
     *      info of player
     */
    public ZorkMain(Player player) {
        this.currentPlayer = player;
        this.init();
    }

    /**
     * used to load the initial positions from XML and locate the player at entrance
     */
    private void init() {
        File routeFile = new File(routeMapFile);
        if (!routeFile.canRead())
        {
            ZorkUtil.print("Error loading Route Map...TRY AGAIN....");
            return;
        }
        try{
            loadRoute(routeFile);
            currentPlayer.currentPosition = positionHolder.get(1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * used to start game after loading of map
     */
    public void startGame() {
        ZorkActionImpl.showPositionContent(currentPlayer.currentPosition);
        while (!this.currentPlayer.isFinished){
            String playerInput = ZorkUtil.receiveGameInputValue();
            playerInput = playerInput.toUpperCase();
            if(playerInput != null && !playerInput.isEmpty()) {
                try{
                    evaluateAndPerform(playerInput);
                }catch (Exception e){
                    ZorkUtil.print(e.getMessage());
                }
            }
        }
        if(this.currentPlayer.isFinished) {
            if(this.currentPlayer.isVictory) {
                ZorkUtil.print("You have completed Game in "+ currentPlayer.playerChoicesCount + " choices");
            }else {
                ZorkUtil.print("You lost the Game...");
            }
            ZorkUtil.print("*******GOOD BYE********");
            return;
        }
    }

    /**
     * Used to load the given MAP
     *
     * @param routeFile
     *          file object of map file
     * @throws Exception
     */
    private void loadRoute(File routeFile) throws Exception {
        DocumentBuilder builderObject = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builderObject.parse(routeFile);

        document.getDocumentElement().normalize();
        NodeList positions = document.getElementsByTagName(ZorkConstants.POSITION);
        for(int positionIteratorIndex = 0; positionIteratorIndex < positions.getLength(); positionIteratorIndex++) {
            Element positionElement = (Element)positions.item(positionIteratorIndex);
            ZorkPosition  zorkPositionObject = new ZorkPosition();

            zorkPositionObject.positionId = Integer.parseInt(positionElement.getElementsByTagName(ZorkConstants.POSITION_PRIMARY_ID).item(0).getTextContent());
            zorkPositionObject.positionName = positionElement.getElementsByTagName(ZorkConstants.POSITION_PRIMARY_NAME).item(0).getTextContent();
            if(positionElement.hasAttribute(ZorkConstants.POSITION_IS_OBSTACLE)) {
                zorkPositionObject.isObstacle = true;
            }

            Element availableInfosTag = (Element) positionElement.getElementsByTagName(ZorkConstants.AVAILABLE_INFOS).item(0);
            zorkPositionObject.positionInfos = (List<ZorkPositionInfo>) new ZorkPositionInfo(availableInfosTag).parseElement();

            Element availableDirectionsTag = (Element) positionElement.getElementsByTagName(ZorkConstants.AVAILABLE_DIRECTIONS).item(0);
            zorkPositionObject.positionDirections = (Map<String, ZorkPositionDirection>)new ZorkPositionDirection(availableDirectionsTag).parseElement();

            Element availableActionsTag = (Element) positionElement.getElementsByTagName(ZorkConstants.AVAILABLE_ACTIONS).item(0);
            zorkPositionObject.positionActions = (Map<String, ZorkPositionAction>)new ZorkPositionAction(availableActionsTag).parseElement();

            NodeList availableAssetsTag = positionElement.getElementsByTagName(ZorkConstants.AVAILABLE_ASSETS);
            if(availableAssetsTag.getLength() > 0) {
                Element availableAssetsElements = (Element) availableAssetsTag.item(0);
                zorkPositionObject.positionAssets = (Map<String, ZorkPositionAsset>)new ZorkPositionAsset(availableAssetsElements).parseElement();
            }
            positionHolder.put(zorkPositionObject.positionId, zorkPositionObject);
        }
    }

    /**
     * used to evaluate and start performing the input given by player
     *
     * @param actionInput
     *          input value given by player
     * @throws Exception
     */
    private void evaluateAndPerform(String actionInput) throws Exception {
        String[] inputParts = actionInput.split(" ");
        inputList = new ArrayList<String>(Arrays.asList(inputParts));
        String verb = inputList.get(0);
        if(verb.contains("EXIT")) {
            currentPlayer.isInterrupted = true;
            currentPlayer.isFinished = true;
            return;
        }
        if(verb.contains("INVENTORY")){
            throw new Exception("try SHOW INVENTORY to see assets you have...");
        }
        if(verb.contains("SHOW")) {
            String object = inputList.get(1);
            if(object.equalsIgnoreCase("INVENTORY")) {
                ZorkUtil.print("You have:");
                for (Map.Entry<String,Integer> eachAsset : currentPlayer.ownedAssets.entrySet()) {
                    ZorkUtil.print(eachAsset.getKey());
                }
                return;
            }
            throw new Exception("try SHOW INVENTORY to see assets you have...");
        }
        if(!currentPlayer.currentPosition.positionActions.containsKey(verb)) {
            throw new Exception("Action " + verb + " not allowed");
        }
        (new ZorkActionImpl(inputList, currentPlayer, positionHolder)).start();
    }
}
