package uk.co.hyttioaboa.messages.json;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.hyttioaboa.messages.GenericElement;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;

public class JsonElement extends GenericElement implements ElementInterface {

    JSONObject elementJson;
    Integer index;
    String identifier;
    String name;
    String type;
    String interaction;
    String value;
    Long timeout;

    public JsonElement(JSONObject elementDefinition) {
        setDefinition(elementDefinition);

        setIdentifier(getStringProperty("identifier"));
        setType(getStringProperty("type"));
        setInteraction(getStringProperty("interaction"));
        setName(getStringProperty("name"));
        setValue(getStringProperty("value"));
        setTimeout(getIntegerProperty("timeout"));
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
