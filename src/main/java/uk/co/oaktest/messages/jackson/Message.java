package uk.co.oaktest.messages.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;

public class Message {

    @NotEmpty
    @JsonProperty
    private String type;

    @NotEmpty
    @JsonProperty
    private String url;

    @JsonProperty
    private ArrayList<Page> pages;

    private String name;

    public Message() {

    }

    public Message(String type, String url, ArrayList<Page> pages) {
        this.type = type;
        this.url = url;
        this.pages = pages;
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

    public ArrayList<Page> getPages() {
        return this.pages;
    }

    public String setName(String name) {
        this.name = name;
        return this.name;
    }
}
