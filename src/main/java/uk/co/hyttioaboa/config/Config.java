package uk.co.hyttioaboa.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pete on 03/12/2015.
 */
public class Config {

    public static final Map interactionTypes(){

        Map <String, String> interactionMap = new HashMap<String, String>();
        interactionMap.put("click", "click");
        interactionMap.put("checkText", "checkElementText");
        interactionMap.put("typeValue", "typeValue");

        return interactionMap;
    }




    public static final Map elementTypes(){

        Map <String, String> elementMap = new HashMap<String, String>();
        elementMap.put("link", "Link");
        elementMap.put("textbox", "TextBox");
        elementMap.put("paragraph", "Paragraph");

        return elementMap;
    }

    //TODO Need to add in a map for different object identification types such as ID etc

}
