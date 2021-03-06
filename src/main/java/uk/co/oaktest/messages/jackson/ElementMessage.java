package uk.co.oaktest.messages.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
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
    private String behaviour;

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

    @Valid
    @JsonProperty
    private ArrayList<Option> options;

    @JsonProperty
    private Boolean selectByText;

    @JsonProperty
    private Boolean screenshotBefore;

    @JsonProperty
    private Boolean screenshotAfter;

    @JsonProperty
    private Map metaData;

    public ElementMessage() {

    }

    public ElementMessage(String name, String identifier, String identifierType, String interaction, String behaviour, String type) {
        this.name = name;
        this.identifier = identifier;
        this.identifierType = identifierType;
        this.interaction = interaction;
        this.type = type;
        this.behaviour = behaviour;
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

    public String getBehaviour() {
        return this.behaviour;
    }

    public ArrayList<Option> getOptions() {
        return this.options;
    }

    public Boolean getSelectByText() {
        return this.selectByText;
    }

    public Map getMetaData() {
        return this.metaData;
    }

    public Boolean getScreenshotBefore() {
        if (this.screenshotBefore == null) {
            return false;
        }
        return this.screenshotBefore;
    }

    public Boolean getScreenshotAfter() {
        if (this.screenshotAfter == null) {
            return false;
        }
        return this.screenshotAfter;
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

    public String setBehaviour(String behaviour) {
        this.behaviour = behaviour;
        return this.behaviour;
    }

    public boolean hasOptions() {
        if (this.options == null) {
            return false;
        }
        return this.options.size() > 0;
    }
}
