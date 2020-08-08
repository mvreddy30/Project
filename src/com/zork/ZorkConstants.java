package com.zork;

public class ZorkConstants {
    public static final String postitionXMLFile = "RouteMap.xml";

    /*Parent Tag related*/
    public static final String POSITION = "Position";

    /*Position related*/
    public static final String POSITION_PRIMARY_ID = "id";
    public static final String POSITION_PRIMARY_NAME = "name";
    public static final String POSITION_IS_OBSTACLE = "isObstacle";

    /*Position Info related*/
    public static final String AVAILABLE_INFOS = "AvailableInfos";
    public static final String INFO = "info";
    public static final String PRIORITY = "priority";
    public static final String MESSAGE = "message";
    public static final String CONDITION = "condition";
    public static final String CONDITION_SUBJECT = "subject";
    public static final String CONDITION_OPERATOR = "operator";
    public static final String CONDITION_OBJECT = "object";

    /*Directions related*/
    public static final String AVAILABLE_DIRECTIONS ="AvailableDirections";
    public static final String DIRECTION ="direction";
    public static final String POSITION_ID ="positionId";
    public static final String DIRECTION_NAME ="name";
    public static final String DIRECTION_ERROR_MSG ="errorMsg";

    /*Actions related*/
    public static final String AVAILABLE_ACTIONS = "AvailableActions";
    public static final String ACTION = "action";
    public static final String ACTION_NAME = "name";
    public static final String ACTION_MESSAGE = "message";

    /*Assets related*/
    public static final String AVAILABLE_ASSETS = "AvailableAssets";
    public static final String ASSET = "asset";
    public static final String ASSET_NAME = "name";
    public static final String ASSET_PRICE = "price";
}
