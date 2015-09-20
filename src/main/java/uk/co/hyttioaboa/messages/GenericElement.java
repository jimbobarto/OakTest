package uk.co.hyttioaboa.messages;

public class GenericElement {
    Integer index;
    String instruction;
    String type;
    String interaction;
    String value;
    String timeout;

    public GenericElement() {
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
