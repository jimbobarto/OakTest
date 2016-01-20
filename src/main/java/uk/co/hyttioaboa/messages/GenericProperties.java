package uk.co.hyttioaboa.messages;

public class GenericProperties {
    public Integer index;
    public String identifier;
    public String identifierType;
    public String type;
    public String interaction;
    public String name;
    public String value;
    public Integer timeout;
    public String verb;
    public String text;
    public String uri;
    public String headers;
    public String payload;
    public String expectedResults;

    public GenericProperties() {
        //TODO: screenshot, wait (pause), save value
    }

    public Integer getIndex() {
        return this.index;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getIdentifierType() {
        return this.identifierType;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getText() {return this.text;}

    public String getValue() {return this.value;}

    public String getInteraction() {
        return this.interaction;
    }

    public Integer getTimeout() {
        return this.timeout;
    }

    public String getVerb() {
        return this.verb;
    }

    public String getUri() {
        return this.uri;
    }

    public String getHeaders() {
        return this.headers;
    }

    public String getPayload() {
        return this.payload;
    }

    public String getExpectedResults() {
        return this.expectedResults;
    }

    public Integer setIndex(Integer newIndex) {
        this.index = newIndex;
        return this.index;
    }

    public String setIdentifier(String newIdentifier) {
        this.identifier = newIdentifier;
        return this.identifier;
    }

    public String setIdentifierType(String newIdentifierType) {
        this.identifierType = newIdentifierType;
        return this.identifierType;
    }
    public String setName(String newName) {
        this.name = newName;
        return this.name;
    }

    public String setType(String newType) {
        this.type = newType;
        return this.type;
    }

    public String setValue(String newValue) {
        this.value = newValue;
        return this.value;
    }

    public String setText(String newValue) {
        this.text = newValue;
        return this.text;
    }
    public String setInteraction(String newInteraction) {
        this.interaction = newInteraction;
        return this.interaction;
    }

    public Integer setTimeout(Integer newTimeout) {
        this.timeout = newTimeout;
        return this.timeout;
    }

    public String setVerb(String newVerb) {
        this.verb = newVerb;
        return this.verb;
    }

    public String setUri(String newUri) {
        this.uri = newUri;
        return this.uri;
    }

    public String setHeaders(String newHeaders) {
        this.headers = newHeaders;
        return this.headers;
    }

    public String setPayload(String newPayload) {
        this.payload = newPayload;
        return this.payload;
    }

    public String getExpectedResults(String newExpectedResults) {
        this.expectedResults = newExpectedResults;
        return this.expectedResults;
    }
}
