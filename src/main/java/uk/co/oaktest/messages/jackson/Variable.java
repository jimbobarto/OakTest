package uk.co.oaktest.messages.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class Variable {
    @NotEmpty
    @JsonProperty
    private String name;

    @NotEmpty
    @JsonProperty
    private String type;

    @NotEmpty
    @JsonProperty
    private String value;

    public Variable() {

    }

    public Variable(String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
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

    public String setName(String name) {
        this.name = name;
        return this.name;
    }

    public String setType(String type) {
        this.type = type;
        return this.type;
    }

    public String setValue(String value) {
        this.value = value;
        return this.value;
    }
}
