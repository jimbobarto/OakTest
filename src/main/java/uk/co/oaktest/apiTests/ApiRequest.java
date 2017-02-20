package uk.co.oaktest.apiTests;

import uk.co.oaktest.constants.Status;
import uk.co.oaktest.containers.Container;
import uk.co.oaktest.messages.interfaces.PageInterface;
import uk.co.oaktest.messages.jackson.PageMessage;
import uk.co.oaktest.requests.Request;
import uk.co.oaktest.requests.RequestException;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.variables.Translator;

public class ApiRequest {
    PageMessage message;
    ResponseNode requestNode;
    Request requestContainer;
    Container container;
    Translator translator;

    String requestName;
    String uri;
    String verb;
    String headers;
    String payload;
    String contentType;
    //String expectedBody;
    Integer expectedStatus;

    String actualBody;
    Integer actualStatus;

    public ApiRequest(Request request, PageMessage pageMessage, ResponseNode requestResponseNode, Container container) {
        this.requestContainer = request;
        this.message = pageMessage;
        this.requestNode = requestResponseNode;
        this.container = container;

        this.translator = container.getTranslator();

        this.requestName = this.message.getName();
        this.uri = this.message.getUrl();
        this.verb = this.message.getHttpVerb();
        this.contentType = this.message.getContentType();
        this.headers = this.message.getHeaders();
        this.payload = this.message.getPayload();
        //this.expectedBody = this.message.getExpectedResult();
        this.expectedStatus = this.message.getExpectedStatus();

        this.uri = this.translator.translate(this.uri);
        this.payload = this.translator.translate(this.payload);
    }

    public int test() {
        try {
            this.actualStatus = this.requestContainer.request(this.verb, this.uri, this.requestNode);
        }
        catch (RequestException reqEx) {
            this.actualStatus = Status.BASIC_ERROR.getValue();
            requestNode.addMessage(Status.BASIC_ERROR.getValue(), reqEx.getMessage());
        }

        try {
            Translator translator = this.container.getTranslator();
            this.actualBody = this.requestContainer.getBody();
            translator.addVariable(this.requestName + "_body", this.contentType, this.actualBody);
            translator.addVariable(this.requestName, this.contentType, this.actualBody);
            //TODO: save headers
        }
        catch (Exception exception) {
            exception.printStackTrace();
            requestNode.addMessage(Status.BASIC_ERROR.value(), exception.getMessage());
        }

        check();

        return this.actualStatus;
    }

    private void check() {
        checkStatus(this.actualStatus);
    }

    private void checkStatus(int actualStatus) {
        if (this.expectedStatus != null) {
            if (actualStatus == this.expectedStatus) {
                this.requestNode.addMessage(Status.BASIC_SUCCESS.getValue(), "Status was " + this.expectedStatus + " as expected");
            } else {
                this.requestNode.addMessage(Status.BASIC_FAILURE.getValue(), "Status was " + actualStatus + " but expected " + this.expectedStatus);
            }
        }
    }

    private ResponseNode createChildNode(String nodeName, int childStatus, String childMessage) {
        ResponseNode childNode = this.requestNode.createChildNode(nodeName);
        childNode.addMessage(childStatus, childMessage);

        return childNode;
    }
}
