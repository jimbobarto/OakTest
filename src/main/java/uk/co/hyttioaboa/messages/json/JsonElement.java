package uk.co.hyttioaboa.messages.json;

import org.json.JSONException;
import org.json.JSONObject;


public class JsonElement {

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

    public Integer setIndex(Integer newIndex) {
        this.index = newIndex;
        return this.index;
    }

    public String setInstruction(String newInstruction) {
        this.instruction = newInstruction;
        return this.instruction;
    }

    public String setType(String newType) {
        this.type = newType;
        return this.type;
    }

    public String setValue(String newValue) {
        this.value = newValue;
        return this.value;
    }

    public String setInteraction(String newInteraction) {
        this.interaction = newInteraction;
        return this.interaction;
    }

    public String setTimeout(String newTimeout) {
        this.timeout = newTimeout;
        return this.timeout;
    }

    public JSONObject setDefinition(JSONObject newDefinition) {
        this.elementJson = newDefinition;
        return this.elementJson;
    }

    public Integer getIndex() {
        return this.index;
    }

    public String getInstruction() {
        return this.instruction;
    }

    public String getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public String getInteraction() {
        return this.interaction;
    }

    public String getTimeout() {
        return this.timeout;
    }

    //TODO: screenshot, wait (pause), save value
}
