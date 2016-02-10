package uk.co.oaktest.messages.json;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.oaktest.containers.MessageContainer;
import uk.co.oaktest.messages.MessageException;
import uk.co.oaktest.messages.interfaces.ElementInterface;

public class JsonElement extends JsonCommon implements ElementInterface {
    final static Logger logger = Logger.getLogger(JsonElement.class);

    public JsonElement(JSONObject elementDefinition) throws MessageException {
        super(elementDefinition);

        setElementProperties();
        validateElement();
    }

    public JsonElement(JSONObject elementDefinition, MessageContainer messageContainer) throws MessageException {
        this(elementDefinition);

        setElementProperties();
        validateElement();

        setScreenshotSetting(calculateScreenshotSetting(messageContainer.getParentShotSetting()));
    }

    private void setElementProperties() throws MessageException {
        setIdentifier(getStringProperty("identifier"));
        setIdentifierType(getStringProperty("identifierType"));
        setType(getStringProperty("type"));
        setInteraction(getStringProperty("interaction"));
        setName(getStringProperty("name"));
        setText(getStringProperty("text"));
        setValue(getStringProperty("value"));
        setTimeout(getIntegerProperty("timeout"));
        setSelectBy(getStringProperty("selectBy"));
        setScreenshotSetting(getIntegerProperty("screenshotSetting"));
    }

    private void validateElement() throws MessageException {
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