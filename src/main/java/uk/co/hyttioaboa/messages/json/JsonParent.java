package uk.co.hyttioaboa.messages.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParent {
    JSONObject message;
    String testDefinition;
    ArrayList<JsonElement> elements = new ArrayList<JsonElement>();

    public JsonParent(String givenTestDefinition) {
        testDefinition = givenTestDefinition;
    }

    public JsonParent(JSONObject givenTestDefinition) {
        message = givenTestDefinition;

        if (message.has("elements")) {
            try {
                JSONArray pageElements = message.getJSONArray("elements");

                for (int i = 0; i < pageElements.length(); i++) {
                    JSONObject currentElementDefinition = pageElements.getJSONObject(i);
                    JsonElement currentElement = new JsonElement(currentElementDefinition);

                    elements.add(currentElement);
                }
            }
            catch (JSONException ex) {
                throw new Error(ex);
            }
        }
    }

    public boolean isValid() {
        try {
            new JSONObject(testDefinition);
        }
        catch (JSONException ex) {
            return false;
        }
        return true;
    }


    public ArrayList getElements() {
        return elements;
    }

    public boolean hasElements() {
        return arrayHasElements(elements);
    }

    private boolean arrayHasElements(ArrayList array) {
        if (array.size() == 0) {
            return false;
        }
        return true;
    }

    protected ArrayList getElements(JSONObject parentElement) {
        ArrayList<JsonElement> newArray = new ArrayList<JsonElement>();
        JSONArray elementDefinitions;

        try {
            if (parentElement.has("elements")) {
                elementDefinitions = parentElement.getJSONArray("elements");

                for (int i = 0; i < elementDefinitions.length(); i++) {
                    JSONObject currentElementDefinition = elementDefinitions.getJSONObject(i);
                    JsonElement currentElement = new JsonElement(currentElementDefinition);

                    newArray.add(currentElement);
                }

            }
        }
        catch (JSONException ex) {
            throw new Error(ex);
        }

        return newArray;
    }

}
