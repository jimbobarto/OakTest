package uk.co.hyttioaboa.messages.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParent {
    JSONObject message;
    String testDefinition;
    String name;
    ArrayList<JsonElement> elements = new ArrayList<JsonElement>();

    public JsonParent(String givenTestDefinition) throws JsonException {
        testDefinition = givenTestDefinition;
    }

    public JsonParent(JSONObject givenTestDefinition) throws JsonException {
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
                throw new JsonException("An element or elements were invalid at construction", ex);
            }
        }

        if (message.has("name")) {
            try {
                setName(message.getString("name"));
            }
            catch (JSONException ex) {
                //throw new Error(ex);
                throw new JsonException("Could not get the name to set it!", ex);
            }
        }
        else {
            throw new JsonException("JSON object has no name defined");
        }
    }

    public String getName() {
        return this.name;
    }

    public String setName(String newName) {
        this.name = newName;
        return this.name;
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

    protected ArrayList getElements(JSONObject parentElement) throws JsonException {
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
            //throw new Error(ex);
            throw new JsonException("An element or elements were invalid", ex);
        }

        return newArray;
    }

}
