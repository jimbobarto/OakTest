package uk.co.oaktest.messages.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.ArrayList;

public class Page {
    @NotEmpty
    @JsonProperty
    private String type;

    @NotEmpty
    @JsonProperty
    private String url;

    private String name;

    @Valid
    @JsonProperty
    private ArrayList<Element> elements;

    public Page() {

    }

    public Page(String type, String url, ArrayList<Element> elements) {
        this.type = type;
        this.elements = elements;
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

    public ArrayList<Element> getElements() {
        return this.elements;
    }

    public String setName(String name) {
        this.name = name;
        return this.name;
    }
}
