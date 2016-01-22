package uk.co.oaktest.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pete on 03/12/2015.
 */
public class Config {

    public static final Map interactionTypes(){

        Map <String, String> interactionMap = new HashMap<String, String>();
        interactionMap.put("click", "click");
        interactionMap.put("checktext", "checkElementText");
        interactionMap.put("checkText", "checkElementText");
        interactionMap.put("getattribute", "getAttribute");
        interactionMap.put("checkElementText", "checkElementText");
        interactionMap.put("typevalue", "typeValue");

        return interactionMap;
    }




    public static final Map elementTypes(){

        Map <String, String> elementMap = new HashMap<String, String>();
        elementMap.put("link", "Link");
        elementMap.put("textbox", "TextBox");
        elementMap.put("paragraph", "Paragraph");
        elementMap.put("header", "Header");
        return elementMap;
    }

    //TODO Need to add in a map for different object identification types such as ID etc
    public static final Map findByTypes(){

        Map <String, String> findByMap = new HashMap<String, String>();
        findByMap.put("css", "CSS");
        findByMap.put("id", "ID");
        findByMap.put("Id", "ID");
        findByMap.put("ID", "ID");
        findByMap.put("linktext", "LINKTEXT");
        findByMap.put("xpath", "XPATH");
        findByMap.put("class", "CLASS");

        return findByMap;
    }
}
