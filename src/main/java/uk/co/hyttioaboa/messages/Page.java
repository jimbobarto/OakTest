package uk.co.hyttioaboa.messages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.hyttioaboa.messages.json.*;

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

        //TODO: add in functionality for API testing
    }

}
