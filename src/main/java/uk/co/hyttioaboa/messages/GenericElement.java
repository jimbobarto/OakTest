package uk.co.hyttioaboa.messages;

public class GenericElement {
    Integer index;
    String identifier;
    String identifierType;
    String type;
    String interaction;
    String name;
    String value;
    Integer timeout;

    public GenericElement() {
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

    public String setInteraction(String newInteraction) {
        this.interaction = newInteraction;
        return this.interaction;
    }

    public Integer setTimeout(Integer newTimeout) {
        this.timeout = newTimeout;
        return this.timeout;
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

    public String getValue() {
        return this.value;
    }

    public String getInteraction() {
        return this.interaction;
    }

    public Integer getTimeout() {
        return this.timeout;
    }

    //TODO: screenshot, wait (pause), save value
}
