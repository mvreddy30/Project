package com.zork;

import org.w3c.dom.Element;

public class ZorkCondition extends ZorkPosition implements ZorkXMLParser{
    /**org.w3c.dom.Element object of Condition while parsing MAP*/
    public Element conditionElement;

    /**stores the value on which the compare should happen*/
    String subject = null;
    /**stores comparator*/
    String operator = null;
    /**Stores the value with which the subject should be compared*/
    String object = null;

    /**
     * Constructor initialization
     * @param element
     */
    public ZorkCondition(Element element) {
        this.conditionElement = element;
    }

    /**
     * Constructor initialization
     * @param subject
     *      stores the value on which the compare should happen
     *
     * @param operator
     *      stores comparator
     *
     * @param object
     *      Stores the value with which the subject should be compared
     */
    public ZorkCondition(String subject, String operator, String object){
        this.subject = subject;
        this.operator = operator;
        this.object = object;
    }

    /**
     * used to parse the given element
     * @return
     */
    public Object parseElement() {
        String subject = conditionElement.getElementsByTagName(ZorkConstants.CONDITION_SUBJECT).item(0).getTextContent();
        String operator = conditionElement.getElementsByTagName(ZorkConstants.CONDITION_OPERATOR).item(0).getTextContent();
        String object = conditionElement.getElementsByTagName(ZorkConstants.CONDITION_OBJECT).item(0).getTextContent();
        return new ZorkCondition(subject, operator, object);
    }
}
