package uk.co.oaktest.messages.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class Option {
    @JsonProperty
    private String text;

    @JsonProperty
    private String value;

    @JsonProperty
    private Boolean selected;

    public Option() {

    }

    public Option(String text, String value, Boolean selected) {
        this.text = text;
        this.value = value;
        this.selected = selected;
    }

    public String getText() {
        return this.text;
    }

    public String getValue() {
        return this.value;
    }

    public Boolean getSelected() {
        return this.selected;
    }

    public String setText(String text) {
        this.text = text;
        return this.text;
    }

    public String setValue(String value) {
        this.value = value;
        return this.value;
    }

    public Boolean setSelected(Boolean selected) {
        this.selected = selected;
        return this.selected;
    }

}
