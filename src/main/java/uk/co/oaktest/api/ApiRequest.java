package uk.co.oaktest.api;

import uk.co.oaktest.constants.Status;
import uk.co.oaktest.messages.interfaces.PageInterface;
import uk.co.oaktest.requests.Request;
import uk.co.oaktest.requests.RequestException;
import uk.co.oaktest.results.ResponseNode;

public class ApiRequest {
    PageInterface message;
    ResponseNode requestNode;
    Request requestContainer;

    String uri;
    String verb;
    String headers;
    String payload;
    String expectedBody;
    Integer expectedStatusCode;

    String actualBody;
    Integer actualStatusCode;

    public ApiRequest(Request request, PageInterface setUpMessage, ResponseNode requestResponseNode) {
        this.requestContainer = request;
        this.message = setUpMessage;
        this.requestNode = requestResponseNode;

        this.uri = this.message.getUri();
        this.verb = this.message.getVerb();
        this.headers = this.message.getHeaders();
        this.payload = this.message.getPayload();
        this.expectedBody = this.message.getExpectedResult();
        this.expectedStatusCode = this.message.getExpectedStatusCode();
    }

    public int test() {
        try {
            this.actualStatusCode = this.requestContainer.request(this.verb, this.uri);
            this.actualBody = this.requestContainer.getBody();
        }
        catch (RequestException reqEx) {
            this.actualStatusCode = Status.BASIC_ERROR.getValue();

            requestNode.addMessage(Status.BASIC_ERROR.getValue(), reqEx);
        }

        check();

        return this.actualStatusCode;
    }

    private void check() {
        checkStatus(this.actualStatusCode);
    }

    private void checkStatus(int actualStatus) {
        if (this.expectedStatusCode != null) {
            if (actualStatus == this.expectedStatusCode) {
                createChildNode("Status OK", Status.BASIC_SUCCESS.getValue(), "Status was " + this.expectedStatusCode + " as expected");
            } else {
                createChildNode("Status not OK", Status.BASIC_FAILURE.getValue(), "Status was " + actualStatus + " but expected " + this.expectedStatusCode);
            }
        }
    }

    private ResponseNode createChildNode(String nodeName, int childStatus, String childMessage) {
        ResponseNode childNode = this.requestNode.createChildNode(nodeName);
        childNode.addMessage(childStatus, childMessage);

        return childNode;
    }
}