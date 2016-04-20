package uk.co.oaktest.messages.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class Element {
    @NotEmpty
    @JsonProperty
    private String type;

    private String name;

    public Element() {

    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String setName(String name) {
        this.name = name;
        return this.name;
    }
}
