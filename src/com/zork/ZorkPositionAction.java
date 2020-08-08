package com.zork;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

public class ZorkPositionAction extends ZorkPosition implements ZorkXMLParser {
    /**org.w3c.dom.Element object of actions at a particular position while parsing map*/
    public Element actionElement;
    /**Stores NAME of action*/
    public String name = null;
    /**Stores MESSAGE of action*/
    public String message = null;

    /**
     * Constructor initialization
     * @param element
     */
    public ZorkPositionAction(Element element) {
        this.actionElement = element;
    }

    /**
     * Constructor initialization
     *
     * @param name
     *          NAME of action
     *
     * @param message
     *          MESSAGE of action
     */
    public ZorkPositionAction(String name, String message) {
        this.name = name;
        this.message = message;
    }

    /**
     * used to parse the given element into list Map of available actions at a particular postions
     * @return
     */
    public Object parseElement() {
        Map<String, ZorkPositionAction> returnList = new HashMap<>();
        NodeList actionsNodeList = actionElement.getElementsByTagName(ZorkConstants.ACTION);
        for(int index=0;index<actionsNodeList.getLength();index++) {
            Element eachActionNode = (Element)actionsNodeList.item(index);
            String name = eachActionNode.getElementsByTagName(ZorkConstants.ACTION_NAME).item(0).getTextContent();
            String message = eachActionNode.getElementsByTagName(ZorkConstants.ACTION_MESSAGE).item(0).getTextContent();
            ZorkPositionAction zorkPositionActionObject = new ZorkPositionAction(name, message);
            returnList.put(name, zorkPositionActionObject);
        }
        return returnList;
    }
}
