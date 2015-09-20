package uk.co.hyttioaboa.messages.json;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.hyttioaboa.messages.GenericElement;

public class JsonElement extends GenericElement {

    JSONObject elementJson;
    Integer index;
    String instruction;
    String type;
    String interaction;
    String value;
    String timeout;

    public JsonElement(JSONObject elementDefinition) {
        setDefinition(elementDefinition);

        setIndex(getIntegerProperty("index"));
        setInstruction(getStringProperty("instruction"));
        setType(getStringProperty("type"));
        setInteraction(getStringProperty("interaction"));
        setValue(getStringProperty("value"));
        setTimeout(getStringProperty("timeout"));
    }

    private String getStringProperty(String propertyName) {
        if (this.elementJson.has(propertyName)) {
            try {
                return this.elementJson.getString(propertyName);
            }
            catch (JSONException ex) {
                throw new Error(ex);
            }
        }
        else {
            // TODO: throw a new 'json element invalid' error...
            throw new Error("The json element definition for '" + propertyName + "' was invalid: " + this.elementJson.toString());
        }
    }

    private Integer getIntegerProperty(String propertyName) {
        if (this.elementJson.has(propertyName)) {
            try {
                return this.elementJson.getInt(propertyName);
            }
            catch (JSONException ex) {
                throw new Error(ex);
            }
        }
        else {
            // TODO: throw a new 'json element invalid' error...
            throw new Error("The json element definition for '" + propertyName + "' was invalid: " + this.elementJson.toString());
        }
    }

    public JSONObject setDefinition(JSONObject newDefinition) {
        this.elementJson = newDefinition;
        return this.elementJson;
    }

}
