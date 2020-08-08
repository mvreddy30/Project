package com.zork;

import java.util.List;

public interface ZorkObstacle {
    /**
     * Used to go through the obstacle which will be implemented by specific obstacles
     * @param inputList
     *          input value given by player while he is in obstacle
     * @throws Exception
     */
    void CrackObstacle(List<String> inputList) throws Exception;
}
