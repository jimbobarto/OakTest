package uk.co.hyttioaboa.messages.json;

import org.json.JSONException;
import org.json.JSONObject;
import uk.co.hyttioaboa.messages.GenericProperties;
import uk.co.hyttioaboa.messages.MessageException;

public class JsonCommon extends GenericProperties {
    JSONObject message;
    String stringMessage;

    public JsonCommon(String messageDefinition) {
    }

    public JsonCommon(JSONObject messageDefinition) {
        setDefinition(messageDefinition);
    }

    public JSONObject setDefinition(JSONObject newDefinition) {
        this.message = newDefinition;
        return this.message;
    }

    public String getStringProperty(String propertyName) throws MessageException {
        if (this.message.has(propertyName)) {
            try {
                return this.message.getString(propertyName);
            }
            catch (JSONException ex) {
                throw new MessageException("Could not get '" + propertyName + "' property from message", ex);
            }
        }
        else {
            //throw new MessageException("The json element definition for '" + propertyName + "' was invalid: " + this.message.toString());
            return "";
        }
    }

    public Integer getIntegerProperty(String propertyName) throws MessageException {
        if (this.message.has(propertyName)) {
            try {
                return this.message.getInt(propertyName);
            }
            catch (JSONException ex) {
                throw new MessageException("Could not get '" + propertyName + "' property from message", ex);
            }
        }
        else {
            //throw new MessageException("The json element definition for '" + propertyName + "' was invalid: " + this.message.toString());
            return null;
        }
    }
}
