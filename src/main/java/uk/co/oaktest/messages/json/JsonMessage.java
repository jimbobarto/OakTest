package uk.co.oaktest.messages.json;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.oaktest.containers.MessageContainer;
import uk.co.oaktest.messages.MessageException;
import uk.co.oaktest.messages.interfaces.MessageInterface;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonMessage extends JsonParent implements MessageInterface {
    ArrayList<JsonPage> pages;
    ArrayList<JsonPage> requests;
    Map<String, String> variables;
    String url;
    String name;
    String type;
    String implementation;
    MessageContainer messageContainer;
    final static Logger logger = Logger.getLogger(JsonMessage.class);

    public JsonMessage(String givenTestDefinition) throws MessageException {
        super(givenTestDefinition);

        if ( isValid() ) {
            // TODO: construct message
            this.messageContainer = new MessageContainer();
            convertDefinitionToMessage();
        }
    }

    public String setStringField(String fieldName, String fieldValue) throws MessageException {
        try {
            Field field = getClass().getDeclaredField(fieldName);
            field.set(this, fieldValue);
            return fieldValue;
        }
        catch (IllegalAccessException illegalAccess) {
            throw new MessageException("You are not allowed to access the '" + fieldName + "' field", illegalAccess);
        }
        catch (NoSuchFieldException noField) {
            throw new MessageException("'" + fieldName + "' field does not exist", noField);
        }
    }

    public String setStringFieldFromMessage(String fieldName, boolean requiredField) throws MessageException {
        try {
            if (this.message.has(fieldName)) {
                return setStringField(fieldName, this.message.getString(fieldName));
            } else if (requiredField) {
                throw new MessageException("Message has no " + fieldName);
            }
        }
        catch (JSONException json) {
            throw new MessageException("Error getting '" + fieldName + "' from message", json);
        }

        return null;
    }

    public String getUrl() {
        return this.url;
    }

    public String setUrl(String newUrl) {
        this.url = newUrl;
        return this.url;
    }

    public String getType() {
        return this.type;
    }

    public String setType(String newType) {
        this.type = newType;
        return this.type;
    }

    public String getImplementation() {
        return this.implementation;
    }

    public String setImplementation(String newImplementation) {
        this.implementation = newImplementation;
        return this.implementation;
    }

    public ArrayList getPages() {
        return pages;
    }

    public ArrayList getElements() {
        return elements;
    }

    public Map getVariables() {
        return this.variables;
    }

    public boolean hasPages() {
        return arrayHasElements(this.pages);
    }

    public boolean hasElements() {
        return arrayHasElements(this.elements);
    }

    public boolean hasVariables() {
        return mapHasElements(this.variables);
    }

    public JSONObject convertDefinitionToMessage()  throws MessageException {
        try {
            message = new JSONObject(testDefinition);
        }
        catch (JSONException ex) {
            throw new MessageException("Invalid JSON message", ex);
        }

        if (message.has("screenshotSetting")) {
            try {
                setScreenshotSetting(message.getInt("screenshotSetting"));
            }
            catch (JSONException ex) {
                throw new MessageException("Could not set message screenshot setting", ex);
            }
        }
        else {
            setScreenshotSetting(0);
        }
        this.messageContainer.setParentShotSetting(getScreenshotSetting());

        if (message.has("pages") && !message.has("elements")) {
            //JSONArray pages = message.get("pages");
            pages = getPages(message);
        }
        else if (message.has("elements") && !message.has("pages")) {
            elements = getElements(message);
        }
        else if (message.has("elements") && message.has("pages")) {
            throw new MessageException("Message has both pages and elements at the top level");
        }

        setStringFieldFromMessage("url", true);
        //setStringFieldFromMessage("name", true);
        setStringFieldFromMessage("implementation", false);
        if (message.has("name")) {
            try {
                setName(message.getString("name"));
            } catch (JSONException ex) {
                throw new MessageException("Could not set message name", ex);
            }
        }


        this.variables = getVariables(message);

        return message;
    }

    public ArrayList<JsonPage> getPages(JSONObject parentElement) throws MessageException {
        ArrayList<JsonPage> newArray = new ArrayList<JsonPage>();
        JSONArray pageDefinitions;

        try {
            if (parentElement.has("pages")) {
                pageDefinitions = parentElement.getJSONArray("pages");

                for (int i = 0; i < pageDefinitions.length(); i++) {
                    JSONObject currentPageDefinition = pageDefinitions.getJSONObject(i);

                    this.messageContainer.setParentShotSetting(getScreenshotSetting());
                    JsonPage currentPage = new JsonPage(currentPageDefinition, this.messageContainer);
                    newArray.add(currentPage);
                }

            }
        }
        catch (JSONException ex) {
            throw new MessageException("Invalid page/s", ex);
        }

        return newArray;
    }

    public Map<String, String> getVariables(JSONObject parentElement) throws MessageException {
        Map<String, String> newMap = new HashMap<String, String>();
        JSONObject variableDefinitions;

        try {
            if (parentElement.has("variables")) {
                variableDefinitions = parentElement.getJSONObject("variables");
                Iterator<?> variableKeys = variableDefinitions.keys();

                while( variableKeys.hasNext() ) {
                    String key = (String)variableKeys.next();
                    newMap.put(key, (String)variableDefinitions.get(key));
                }
            }
        }
        catch (JSONException ex) {
            throw new MessageException("Invalid variable/s", ex);
        }

        return newMap;
    }

}
