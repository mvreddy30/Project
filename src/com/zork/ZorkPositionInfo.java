package com.zork;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class ZorkPositionInfo extends ZorkPosition implements ZorkXMLParser {
    /**org.w3c.dom.Element object of info messages at a particular position while parsing map*/
    public Element infoElement;
    /**Stores Priority of an info message of a particular position*/
    public Integer priority = null;
    /**Stores message */
    public String message = null;
    /**Stores Condition to show particular message at a particular position*/
    public ZorkCondition condition = null;

    /**
     * Constructor initialization
     * @param element
     */
    public ZorkPositionInfo(Element element) {
        this.infoElement = element;
    }

    /**
     * Constructor initialization
     * @param priority
     *          Priority of an info message of a particular position
     * @param message
     *          Stores message
     * @param condition
     *          Stores Condition to show particular message at a particular position
     */
    public ZorkPositionInfo(Integer priority, String message, ZorkCondition condition) {
        this.priority = priority;
        this.message = message;
        this.condition = condition;
    }

    /**
     * used to parse the given element into list of available info messages at a particular postions
     * @return
     */
    public Object parseElement(){
        List<ZorkPositionInfo> returnList = new ArrayList<>();
        NodeList infosNodeList = infoElement.getElementsByTagName(ZorkConstants.INFO);
        for(int index=0;index<infosNodeList.getLength();index++) {
            Element eachInfoNode = (Element)infosNodeList.item(index);
            Integer priority = Integer.parseInt(eachInfoNode.getElementsByTagName(ZorkConstants.PRIORITY).item(0).getTextContent());
            String message = eachInfoNode.getElementsByTagName(ZorkConstants.MESSAGE).item(0).getTextContent();
            NodeList conditionsNodeList = eachInfoNode.getElementsByTagName(ZorkConstants.CONDITION);
            ZorkCondition condition = null;
            if(conditionsNodeList.getLength() > 0) {
                Element conditionElement = (Element) conditionsNodeList.item(0);
                condition = (ZorkCondition) new ZorkCondition(conditionElement).parseElement();
            }
            ZorkPositionInfo zorkPositionInfoObject = new ZorkPositionInfo(priority, message, condition);
            returnList.add(zorkPositionInfoObject);
        }
        return returnList;
    }
}
