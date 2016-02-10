package uk.co.oaktest.messages.json;

import org.json.JSONException;
import org.json.JSONObject;
import uk.co.oaktest.containers.MessageContainer;
import uk.co.oaktest.messages.MessageException;
import uk.co.oaktest.messages.interfaces.PageInterface;

public class JsonPage extends JsonParent implements PageInterface {

    String type;
    String verb;
    String uri;
    String headers;
    String payload;
    String expectedResults;
    Integer expectedStatusCode;

    public JsonPage(JSONObject pageDefinition) throws MessageException {
        super(pageDefinition);

        setPage();
    }

    public JsonPage(JSONObject pageDefinition, MessageContainer messageContainer) throws MessageException {
        super(pageDefinition, messageContainer);

        setPage();
        if (!this.type.equals("api")) {
            setScreenshotSetting(calculateScreenshotSetting(messageContainer.getParentShotSetting()));
        }
    }

    private void setPage() throws MessageException {
        setType(evaluateType());

        if (this.type.equals("api")) {
            setVerb(getStringProperty("verb"));
            setUri(getStringProperty("uri"));
            setHeaders(getStringProperty("headers"));
            setPayload(getStringProperty("payload"));
            setExpectedResult(getStringProperty("expectedResults"));
            setExpectedStatusCode(getIntegerProperty("expectedStatusCode"));
        }
        else {
            if (this.message.has("uri")) {
                setUri(getStringProperty("uri"));
            }
            setScreenshotSetting(getIntegerProperty("screenshotSetting"));
        }
    }

    private String evaluateType() throws MessageException {
        try {
            if (this.message.has("type")) {
                String type = this.message.getString("type");
                if (validTypes.contains(type)) {
                    return type;
                }
                else {
                    throw new MessageException("Invalid page type '" + type + "'");
                }
            }
            else {
                if (!this.message.has("verb") && this.message.has("elements")) {
                    return "browser";
                }
                if (this.message.has("verb") && !this.message.has("elements")) {
                    return "api";
                }
                if (this.message.has("uri")) {
                    // The message doesn't have a 'verb' or any 'elements' which each can be used fairly reliably to identify page type
                    // If we have a URI, we can probably assume a browser page because it's then only useful for a browser test (i.e. the test will navigate to the URI).
                    return "browser";
                }
                else {
                    throw new MessageException("Could not identify page type from message (message does not even have a URI)");
                }
            }
        }
        catch (JSONException ex) {
            throw new MessageException("Could not identify page type from message", ex);
        }
    }

    //TODO: screenshot, if/else (?)
    public String getType() {
        return this.type;
    }

    public String setType(String newType) {
        this.type = newType;
        return this.type;
    }

    public String getVerb() {
        return this.verb;
    }

    public String setVerb(String newVerb) {
        this.verb = newVerb;
        return this.verb;
    }

    public String getUri() {
        return this.uri;
    }

    public String setUri(String newUri) {
        this.uri = newUri;
        return this.uri;
    }

    public String getHeaders() {
        return this.headers;
    }

    public String setHeaders(String newHeaders) {
        this.headers = newHeaders;
        return this.headers;
    }

    public String getPayload() {
        return this.payload;
    }

    public String setPayload(String newPayload) {
        this.payload = newPayload;
        return this.payload;
    }

    public String getExpectedResult() {
        return this.expectedResults;
    }

    public String setExpectedResult(String newExpectedResult) {
        this.expectedResults = newExpectedResult;
        return this.expectedResults;
    }

    public Integer getExpectedStatusCode() {
        return this.expectedStatusCode;
    }

    public Integer setExpectedStatusCode(int newExpectedStatusCode) {
        this.expectedStatusCode = newExpectedStatusCode;
        return this.expectedStatusCode;
    }
}
