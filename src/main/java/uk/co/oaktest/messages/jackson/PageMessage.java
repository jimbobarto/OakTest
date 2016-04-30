package uk.co.oaktest.messages.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.ArrayList;

/*
We have a single PageMessage class for both true Pages and for Requests, because a test can have both pages and requests
*/

public class PageMessage {
    @NotEmpty
    @JsonProperty
    private String name;

    @NotEmpty
    @JsonProperty
    private String type;

    @JsonProperty
    private String url;

    @JsonProperty
    private String verb;

    @JsonProperty
    private String headers;

    @JsonProperty
    private String payload;

    @JsonProperty
    private String expectedResults;

    @JsonProperty
    private long expectedStatusCode;

    @Valid
    @NotEmpty
    @JsonProperty
    private ArrayList<ElementMessage> elements;

    public PageMessage() {

    }

    public PageMessage(String name, String type, ArrayList<ElementMessage> elementMessages) {
        this.name = name;
        this.type = type;
        this.elements = elementMessages;
    }

    public PageMessage(String name, String type, String verb, String headers, String payload, String expectedResults, long expectedStatusCode) {
        this.name = name;
        this.type = type;
        this.verb = verb;
        this.headers = headers;
        this.payload = payload;
        this.expectedResults = expectedResults;
        this.expectedStatusCode = expectedStatusCode;
    }

    public String getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<ElementMessage> getElements() {
        return this.elements;
    }

    public String getVerb() {
        return this.verb;
    }

    public String getHeaders() {return this.headers; }

    public String getPayload() {return this.payload; }

    public String getExpectedResults() {return this.expectedResults; }

    public long getExpectedStatusCode() {return this.expectedStatusCode; }

    public String setName(String name) {
        this.name = name;
        return this.name;
    }

    public String setUrl(String url) {
        this.url = url;
        return this.url;
    }
}
