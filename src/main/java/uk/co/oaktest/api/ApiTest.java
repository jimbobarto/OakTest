package uk.co.oaktest.api;

import uk.co.oaktest.messages.interfaces.MessageInterface;
import uk.co.oaktest.messages.interfaces.PageInterface;
import uk.co.oaktest.requests.Request;
import uk.co.oaktest.results.ResponseNode;

import java.util.ArrayList;
import java.util.Iterator;

public class ApiTest {
    MessageInterface message;
    ResponseNode rootResponseNode;

    public ApiTest(MessageInterface setUpMessage) {
        this.message = setUpMessage;

        this.rootResponseNode = new ResponseNode(message.getName());
    }

    public String test() {
        Request requestContainer = new Request(this.message.getUrl());

        ArrayList<PageInterface> pages = this.message.getPages();

        for (Iterator<PageInterface> pageIterator = pages.iterator(); pageIterator.hasNext(); ) {
            PageInterface pageMessage = pageIterator.next();
            //assertEquals("Status code was as expected", pageMessage.getExpectedStatusCode(), statusCode);

            ResponseNode requestResponseNode = this.rootResponseNode.createChildNode(pageMessage.getName());

            ApiRequest request = new ApiRequest(requestContainer, pageMessage, requestResponseNode);
            request.test();

            requestResponseNode.end();
        }


        System.out.println("Final status: " + this.rootResponseNode.getStatus());

        return "Hello";
    }

}
