package uk.co.oaktest.messages.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

/*
We have a single PageMessage class for both true Pages and for Requests, because a test can have both pages and requests
*/

@JsonIgnoreProperties(ignoreUnknown = true)
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
    private String httpVerb;

    @JsonProperty
    private String contentType;

    @JsonProperty
    private String headers;

    @JsonProperty
    private String payload;

//    @JsonProperty
//    private String expectedResults;

    @JsonProperty
    private Integer expectedStatus;

    @Valid
    @JsonProperty
    private ArrayList<ElementMessage> elements;

    @Valid
    @JsonProperty
    private ArrayList<AssertionMessage> assertions;

    @JsonProperty
    private Map metaData;

    public PageMessage() {

    }

    public PageMessage(String name, String type, ArrayList<ElementMessage> elementMessages) {
        this.name = name;
        this.type = type;
        this.elements = elementMessages;
    }

    public PageMessage(String name, String type, String httpVerb, String headers, String payload, String expectedResults, Integer expectedStatus) {
        this.name = name;
        this.type = type;
        this.httpVerb = httpVerb;
        this.headers = headers;
        this.payload = payload;
        //this.expectedResults = expectedResults;
        this.expectedStatus = expectedStatus;
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

    public ArrayList<AssertionMessage> getAssertions() {
        return this.assertions;
    }

    public String getHttpVerb() {
        return this.httpVerb;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getHeaders() {return this.headers; }

    public String getPayload() {return this.payload; }

    //public String getExpectedResults() {return this.expectedResults; }

    public Integer getExpectedStatus() {return this.expectedStatus; }

    public Map getMetaData() {
        return this.metaData;
    }

    public String setName(String name) {
        this.name = name;
        return this.name;
    }

    public String setUrl(String url) {
        this.url = url;
        return this.url;
    }
}
