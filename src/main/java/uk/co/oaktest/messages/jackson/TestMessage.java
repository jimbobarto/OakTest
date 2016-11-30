package uk.co.oaktest.messages.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestMessage {

    @NotEmpty
    @JsonProperty
    private String url;

    @Valid
    @JsonProperty
    private ArrayList<Variable> variables;

    @Valid
    @JsonProperty
    private ArrayList<PageMessage> pages;

    private String name;
    private String implementation;
    private String resultUrl = "";

    public TestMessage() {
        this.variables = new ArrayList<>();
    }

    public TestMessage(String name, String url, ArrayList<PageMessage> pageMessages) {
        this.name = name;
        this.url = url;
        this.pages = pageMessages;
        this.variables = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }

    public ArrayList<PageMessage> getPages() {
        return this.pages;
    }

    public ArrayList<Variable> getVariables() {
        return this.variables;
    }

    public String getImplementation() {
        return this.implementation;
    }

    public String getResultUrl() {
        return this.resultUrl;
    }

    public String setName(String name) {
        this.name = name;
        return this.name;
    }

    public ArrayList<Variable> setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
        return this.variables;
    }

    public String setImplementation(String implementation) {
        this.implementation = implementation;
        return this.implementation;
    }

    public String setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
        return this.resultUrl;
    }

    public boolean hasVariables() {
        return this.variables.size() > 0;
    }
}
