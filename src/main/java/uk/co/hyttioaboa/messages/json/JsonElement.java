package uk.co.hyttioaboa.messages.json;

import org.json.JSONObject;

import uk.co.hyttioaboa.messages.MessageException;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;

public class JsonElement extends JsonCommon implements ElementInterface {


    Integer index;
    String identifier;
    String identifierType;
    String name;
    String type;
    String interaction;
    String value;
    Long timeout;

    public JsonElement(JSONObject elementDefinition) throws MessageException {
        super(elementDefinition);

        try {
            setIdentifier(getStringProperty("identifier"));
            setIdentifierType(getStringProperty("identifierType"));
            setType(getStringProperty("type"));
            setInteraction(getStringProperty("interaction"));
            setName(getStringProperty("name"));
            setValue(getStringProperty("value"));
            setTimeout(getIntegerProperty("timeout"));
        }
        catch (MessageException msgEx) {
            throw msgEx;
        }
    }


}
