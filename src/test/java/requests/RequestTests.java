package requests;

import org.apache.log4j.Logger;
import org.junit.Test;
import uk.co.hyttioaboa.api.ApiTest;
import uk.co.hyttioaboa.browser.BrowserTest;
import uk.co.hyttioaboa.browser.Page;
import uk.co.hyttioaboa.fileContents.GetFileContents;
import uk.co.hyttioaboa.messages.MessageException;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;
import uk.co.hyttioaboa.messages.json.JsonMessage;
import uk.co.hyttioaboa.messages.json.JsonPage;
import uk.co.hyttioaboa.requests.Request;
import uk.co.hyttioaboa.requests.RequestException;
import uk.co.hyttioaboa.results.ResponseNode;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class RequestTests {
    final static Logger logger = Logger.getLogger(RequestTests.class);

    @Test
    public void simpleGet() {
        Request newRequest = new Request();
        try {
            newRequest.get("http://jsonplaceholder.typicode.com/posts/1");
            int statusCode = newRequest.getStatus();
            assertEquals("Status code was as expected", 200, statusCode);
            logger.info("Body: " + newRequest.getBody());
        }
        catch(RequestException reqEx) {
            logger.error("Got a problem... " + reqEx.getMessage());
        }
    }

    @Test
    public void simpleGetFromMessage() {
        GetFileContents fileGetter = new GetFileContents();
        String jsonDefinition = fileGetter.getTestMessage("src/test/resources/testRequestMessage.json");

        MessageInterface testMessage;
        try {
            testMessage = new JsonMessage(jsonDefinition);
        }
        catch (MessageException jsonException) {
            logger.error(jsonException.getMessage());
            return;
        }

        try {
            Request newRequest = new Request(testMessage.getUrl());
            ArrayList<PageInterface> pages = testMessage.getPages();
            for (Iterator<PageInterface> pageIterator = pages.iterator(); pageIterator.hasNext(); ) {
                PageInterface pageMessage = pageIterator.next();
                int statusCode = newRequest.request(pageMessage.getVerb(), pageMessage.getUri());
                //assertEquals("Status code was as expected", pageMessage.getExpectedStatusCode(), statusCode);
                logger.info("Body: " + newRequest.getBody());
            }
        }
        catch(RequestException reqEx) {
            logger.error("Got a problem... " + reqEx.getMessage());
        }
    }

    @Test
    public void simpleApiTestFromMessage() {
        GetFileContents fileGetter = new GetFileContents();
        String jsonDefinition = fileGetter.getTestMessage("src/test/resources/testRequestMessage.json");

        MessageInterface testMessage;
        try {
            testMessage = new JsonMessage(jsonDefinition);
        }
        catch (MessageException jsonException) {
            logger.error(jsonException.getMessage());
            return;
        }

        try {
            ApiTest api = new ApiTest(testMessage);
            api.test();
        }
        catch(Exception reqEx) {
            logger.error("Got a problem... " + reqEx.getMessage());
        }
    }
}
