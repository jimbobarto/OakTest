package uk.co.oaktest.messages.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class ElementMessage {
    @NotEmpty
    @JsonProperty
    private String type;

    @NotEmpty
    @JsonProperty
    private String identifier;

    @NotEmpty
    @JsonProperty
    private String identifierType;

    @NotEmpty
    @JsonProperty
    private String interaction;

    @NotEmpty
    @JsonProperty
    private String name;

    @JsonProperty
    private String text;

    @JsonProperty
    private String value;

    @JsonProperty
    private Integer timeout;

    @JsonProperty
    private String selectBy;

    public ElementMessage() {

    }

    public ElementMessage(String name, String identifier, String identifierType, String interaction, String type) {
        this.name = name;
        this.identifier = identifier;
        this.identifierType = identifierType;
        this.interaction = interaction;
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getIdentifierType() {
        return this.identifierType;
    }

    public String getInteraction() {
        return this.interaction;
    }

    public Integer getTimeout() {
        return this.timeout;
    }

    public String getSelectBy() {
        return this.selectBy;
    }

    public String getText() {
        return this.text;
    }

    public String getValue() {
        return this.value;
    }

    public String setName(String name) {
        this.name = name;
        return this.name;
    }

    public Integer setTimeout(Integer timeout) {
        this.timeout = timeout;
        return this.timeout;
    }

    public String setSelectBy(String selectBy) {
        this.selectBy = selectBy;
        return this.selectBy;
    }

    public String setText(String text) {
        this.text = text;
        return this.text;
    }

    public String setValue(String value) {
        this.value = value;
        return this.value;
    }
}
