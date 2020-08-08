package com.zork;

import java.util.List;
import java.util.Map;

public class ZorkPosition {
    /**Stores the count of visits happened to a particular position by the player*/
    public Integer visitCount = 0;
    /**Stores the NAME of position*/
    public String positionName = null;
    /**Stores the ID of postion*/
    public Integer positionId = null;
    /**Stores whether the particular position is obstacle or not*/
    public Boolean isObstacle = false;
    /**Stores messages to be printed about a particular position*/
    public List<ZorkPositionInfo>  positionInfos= null;
    /**Stores available directions Info that player can choose from a particular position*/
    public Map<String, ZorkPositionDirection> positionDirections = null;
    /**Stores available assets Info that player can choose to take at a particular position*/
    public Map<String, ZorkPositionAsset> positionAssets = null;
    /**Stores available actions Info that player can choose to perform at a particular position*/
    public Map<String, ZorkPositionAction> positionActions = null;
}
