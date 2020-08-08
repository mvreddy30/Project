package com.zork;

import java.util.HashMap;

public enum ZorkActions {
    /**describes action MOVE - eg: MOVE + direction(NORTH, SOUTH, EAST, WEST)*/
    MOVE(1, "MOVE", new MOVE()),
    /**describes action TAKE - eg: TAKE + asset(KEY, SWORD etc.)*/
    TAKE(2, "TAKE", new TAKE()),
    /**describes action TURNON - eg: TURNON TORCH*/
    TURNON(3, "TURNON", new TURNON()),
    /**describes action TURNOFF - eg: TURNOFF TORCH*/
    TURNOFF(4, "TURNOFF", new TURNOFF()),
    /**describes READ - eg: READ CONTENT*/
    READ(5, "READ", new READ()),
    /**describes action FEED - eg: FEED DOG*/
    FEED(6, "FEED", new FEED()),
    /**describes action OPEN - eg: OPEN ENVELOPE*/
    OPEN(7, "OPEN", new OPEN()),
    /**describes action UNLOCK - eg: UNLOCK DOOR*/
    UNLOCK(8, "UNLOCK", new UNLOCK()),
    /**describes action BUY - eg: BUY SWORD*/
    BUY(9, "BUY", new BUY());

    /**stores the ID of specified action*/
    private final int actionId;
    /**stores the name of specified action*/
    private final String actionName;
    /**stores the executor of specified action*/
    private final ZorkActionImpl executor;
    /**stores the mapping between action ID and action object*/
    private static HashMap<Integer, ZorkActions> actionIdToAction = new HashMap<>();
    /**Stores the mapping between action NAME and action object*/
    private static HashMap<String, ZorkActions> actionNameToAction = new HashMap<>();

    static {
        for(ZorkActions actions : ZorkActions.values()) {
            actionIdToAction.put(actions.actionId, actions);
            actionNameToAction.put(actions.actionName, actions);
        }
    }

    /**
     * Constructor initialization
     * @param actionId
     *          ID of action
     *
     * @param actionName
     *          NAME of action
     *
     * @param executor
     *          EXECUTOR of action
     */
    ZorkActions(int actionId, String actionName, ZorkActionImpl executor) {
        this.actionId = actionId;
        this.actionName = actionName;
        this.executor = executor;
    }

    /**
     * used to get the action ID
     * @return
     */
    public int getActionId(){
        return this.actionId;
    }

    /**
     * Used to get action NAME
     * @return
     */
    public String getActionName() {
        return this.actionName;
    }

    /**
     * Used to get the action EXECUTOR
     * @return
     */
    public ZorkActionImpl getExecutor() {
        return this.executor;
    }

    /**
     * Used to get action name from given action ID
     * @param actionId
     *          ID of action
     * @return
     */
    public static String getActionNameFromId(int actionId) {
        return actionIdToAction.get(actionId).actionName;
    }

    /**
     * Used to get action ID from give action NAME
     * @param actionName
     *          NAME of action
     * @return
     */
    public static int getActionIdFromName(String actionName) {
        return actionNameToAction.get(actionName).actionId;
    }

    /**
     * Used to get action EXECUTOR from given action NAME
     * @param actionName
     *          NAME of action
     * @return
     */
    public static ZorkActionImpl getExecutorFromName(String actionName) {
        return actionNameToAction.containsKey(actionName) ? actionNameToAction.get(actionName).executor : null;
    }
}
