package uk.co.oaktest.messages.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Driver {

    @NotEmpty
    @JsonProperty
    private String url;

    @JsonProperty
    private String browser;

    @JsonProperty
    private int status;

    @JsonProperty
    private String uploadMessage;

    public Driver() {
    }

    public Driver(String browser) {
        this.browser = browser;
    }

    public String getBrowser() {
        return this.browser;
    }

    public String getUrl() {
        return this.url;
    }

    public int getStatus() {
        return this.status;
    }

    public String getUploadMessage() {
        return this.uploadMessage;
    }

    public String setBrowser(String browser) {
        this.browser = browser;
        return this.browser;
    }

    public String setUrl(String url) {
        this.url = url;
        return this.url;
    }

    public int setStatus(int status) {
        this.status = status;
        return this.status;
    }

    public String setUploadMessage(String uploadMessage) {
        this.uploadMessage = uploadMessage;
        return this.uploadMessage;
    }
}
