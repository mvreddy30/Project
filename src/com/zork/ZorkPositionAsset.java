package com.zork;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

public class ZorkPositionAsset extends ZorkPosition implements ZorkXMLParser {
    /**org.w3c.dom.Element object of assets at a particular position while parsing map*/
    public Element assetElement;
    /**Stores Name of an asset*/
    public String assetName;
    /**Stoes Price of an asset*/
    public Integer assetPrice;

    /**
     * Consturctor initialization
     * @param element
     */
    public ZorkPositionAsset(Element element) {
        this.assetElement = element;
    }

    /**
     * Constructor initialization
     * @param name
     *          Name of an asset
     *
     * @param price
     *          Price of an asset
     */
    public ZorkPositionAsset(String name, Integer price) {
        this.assetName = name;
        this.assetPrice = price;
    }

    /**
     * used to parse the given element into Map of available assets at a particular position
     * @return
     */
    public Object parseElement() {
        Map<String, ZorkPositionAsset> returnList = new HashMap<>();
        NodeList assetsNodeList = assetElement.getElementsByTagName(ZorkConstants.ASSET);
        for(int index=0;index<assetsNodeList.getLength();index++) {
            Element eachAssetNode = (Element)assetsNodeList.item(index);
            String assetName = eachAssetNode.getElementsByTagName(ZorkConstants.ASSET_NAME).item(0).getTextContent();
            Integer assetPrice = Integer.parseInt(eachAssetNode.getElementsByTagName(ZorkConstants.ASSET_PRICE).item(0).getTextContent());
            ZorkPositionAsset positionAssetObject = new ZorkPositionAsset(assetName, assetPrice);
            returnList.put(assetName, positionAssetObject);
        }
        return returnList;
    }
}
