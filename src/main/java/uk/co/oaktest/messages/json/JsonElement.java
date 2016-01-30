package uk.co.oaktest.messages.json;

import org.json.JSONObject;

import uk.co.oaktest.messages.MessageException;
import uk.co.oaktest.messages.interfaces.ElementInterface;

public class JsonElement extends JsonCommon implements ElementInterface {

    public JsonElement(JSONObject elementDefinition) throws MessageException {
        super(elementDefinition);

        try {
            setIdentifier(getStringProperty("identifier"));
            setIdentifierType(getStringProperty("identifierType"));
            setType(getStringProperty("type"));
            setInteraction(getStringProperty("interaction"));
            setName(getStringProperty("name"));
            setText(getStringProperty("text"));
            setValue(getStringProperty("value"));
            setTimeout(getIntegerProperty("timeout"));
        }
        catch (MessageException msgEx) {
            throw msgEx;
        }

        if (this.name == null) {
            throw new MessageException("Element has no name");
        }
        if (this.identifier == null) {
            throw new MessageException("Element has no identifier");
        }
        if (this.identifierType == null) {
            //throw new MessageException("Element has no identifier type");
            // IdentifierType can be null because an element might be some sort of browser operation - i.e. 'open' or 'back'
        }
        if (this.type == null) {
            throw new MessageException("Element has no type");
        }
        if (this.interaction == null) {
            throw new MessageException("Element has no interaction");
        }
    }


}