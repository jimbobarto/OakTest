package uk.co.hyttioaboa.messages.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.hyttioaboa.messages.Element;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;

import java.util.ArrayList;

public class JsonPage extends JsonParent implements PageInterface {

    String type;
    String verb;
    String uri;
    String headers;
    String payload;
    String expectedResults;

    public JsonPage(JSONObject pageDefinition) throws JsonException {
        super(pageDefinition);
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

}
