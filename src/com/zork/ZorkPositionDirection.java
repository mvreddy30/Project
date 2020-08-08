package com.zork;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

public class ZorkPositionDirection extends ZorkPosition implements ZorkXMLParser {
    /**org.w3c.dom.Element object of directions at a particular position while parsing map*/
    public Element directionsElement;
    /**Stores ID of position*/
    public Integer postionId = null;
    /**Stores NAME of Direction at a particular position*/
    public String name = null;
    /**Stores Condition of Direction at a particular position*/
    public ZorkCondition condition = null;
    /**Stores the message which will be used when player doesn't satisfy given condition*/
    public String errorMsg = null;

    /**
     * Constructor initialization
     * @param element
     */
    public ZorkPositionDirection(Element element) {
        this.directionsElement = element;
    }

    /**
     * Constructor initialization
     * @param postionId
     *          ID of position
     * @param name
     *          NAME of Direction
     * @param condition
     *          Condition of Direction
     * @param errorMsg
     *          message which will be used when player doesn't satisfy given condition
     */
    public ZorkPositionDirection(Integer postionId, String name, ZorkCondition condition, String errorMsg) {
        this.postionId = postionId;
        this.name = name;
        this.condition = condition;
        this.errorMsg = errorMsg;
    }

    /**
     * used to parse the given element into Map of available directions at a particular position
     * @return
     */
    public Object parseElement() {
        Map<String, ZorkPositionDirection> returnList = new HashMap<>();
        NodeList directionsNodeList = directionsElement.getElementsByTagName(ZorkConstants.DIRECTION);
        for(int index=0;index<directionsNodeList.getLength();index++) {
            Element eachDirectionNode = (Element)directionsNodeList.item(index);
            Integer positionId = Integer.parseInt(eachDirectionNode.getElementsByTagName(ZorkConstants.POSITION_ID).item(0).getTextContent());
            String name = eachDirectionNode.getElementsByTagName(ZorkConstants.DIRECTION_NAME).item(0).getTextContent();
            NodeList conditionsNodeList = eachDirectionNode.getElementsByTagName(ZorkConstants.CONDITION);
            ZorkCondition condition = null;
            String errorMsg = null;
            if(conditionsNodeList.getLength() > 0) {
                Element conditionElement = (Element) conditionsNodeList.item(0);
                condition = (ZorkCondition) new ZorkCondition(conditionElement).parseElement();
                errorMsg = eachDirectionNode.getElementsByTagName(ZorkConstants.DIRECTION_ERROR_MSG).item(0).getTextContent();
            }
            ZorkPositionDirection zorkPositionDirectionObject = new ZorkPositionDirection(positionId, name, condition, errorMsg);
            returnList.put(name, zorkPositionDirectionObject);
        }
        return returnList;
    }
}
