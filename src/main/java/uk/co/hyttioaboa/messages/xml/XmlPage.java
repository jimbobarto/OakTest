package uk.co.hyttioaboa.messages.xml;

import org.w3c.dom.Node;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;

/**
 * Created by jamesbartlett on 20/09/15.
 */
public class XmlPage extends XmlParent implements PageInterface {

    String type;
    String verb;
    String uri;
    String headers;
    String payload;
    String expectedResults;

    public XmlPage(Node pageDefinition) {
        super(pageDefinition);
    }

    public String getType() {
        return this.type;
    }

    public String setType(String newType) {
        this.type = newType;
        return this.type;
    }

    public String getVerb() {
        return this.verb;
    }

    public String setVerb(String newVerb) {
        this.verb = newVerb;
        return this.verb;
    }

    public String getUri() {
        return this.uri;
    }

    public String setUri(String newUri) {
        this.uri = newUri;
        return this.uri;
    }

    public String getHeaders() {
        return this.headers;
    }

    public String setHeaders(String newHeaders) {
        this.headers = newHeaders;
        return this.headers;
    }

    public String getPayload() {
        return this.payload;
    }

    public String setPayload(String newPayload) {
        this.payload = newPayload;
        return this.payload;
    }

    public String getExpectedResult() {
        return this.expectedResults;
    }

    public String setExpectedResult(String newExpectedResult) {
        this.expectedResults = newExpectedResult;
        return this.expectedResults;
    }

}
