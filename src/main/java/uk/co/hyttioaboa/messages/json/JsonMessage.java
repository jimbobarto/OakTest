package uk.co.hyttioaboa.messages.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.hyttioaboa.messages.Element;

import java.util.ArrayList;

public class JsonMessage extends JsonParent {
    ArrayList<JsonPage> pages;

    public JsonMessage(String givenTestDefinition) {
        super(givenTestDefinition);

        if (isJsonMessage()) {
            // TODO: construct message
            convertDefinitionToMessage();
        }
    }

    public ArrayList getPages() {
        return pages;
    }

    public ArrayList getElements() {
        return elements;
    }

    public boolean hasPages() {
        return arrayHasElements(pages);
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

    private JSONObject convertDefinitionToMessage() throws Error {
        try {
            message = new JSONObject(testDefinition);
        }
        catch (JSONException ex) {
            throw new Error(ex);
        }

        if (message.has("pages") && !message.has("elements")) {
            //JSONArray pages = message.get("pages");
            pages = getPages(message);
        }
        else if (message.has("elements") && !message.has("pages")) {
            elements = getElements(message);
        }
        else if (message.has("elements") && message.has("pages")) {
            throw new Error("Message has both pages and elements at the top level");
        }

        return message;
    }

    private ArrayList getPages(JSONObject parentElement) {
        ArrayList<JsonPage> newArray = new ArrayList<JsonPage>();
        JSONArray pageDefinitions;

        try {
            if (parentElement.has("pages")) {
                pageDefinitions = parentElement.getJSONArray("pages");

                for (int i = 0; i < pageDefinitions.length(); i++) {
                    JSONObject currentPageDefinition = pageDefinitions.getJSONObject(i);
                    JsonPage currentPage = new JsonPage(currentPageDefinition);

                    newArray.add(currentPage);
                }

            }
        }
        catch (JSONException ex) {
            throw new Error(ex);
        }

        return newArray;
    }

}
