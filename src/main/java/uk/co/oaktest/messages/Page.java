package uk.co.oaktest.messages;

import uk.co.oaktest.messages.json.*;

import java.util.ArrayList;

public class Page {
    ArrayList<Element> elements;

    public Page(JsonPage pageDefinition) {
        if (pageDefinition.hasElements()) {
            ArrayList<JsonElement> pageElements = pageDefinition.getElements();

            for (JsonElement pageElement : pageElements) {
                Element currentElement = new Element(pageElement);
                elements.add(currentElement);
            }
        }

        if (pageDefinition.getVerb() != null) {
            //Ugly way to identify if page is of type 'request'
        }
    }

}
