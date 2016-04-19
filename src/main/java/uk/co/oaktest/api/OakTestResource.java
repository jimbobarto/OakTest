package uk.co.oaktest.api;

import org.apache.log4j.Logger;
import uk.co.oaktest.browserTests.BrowserTest;
import uk.co.oaktest.container.Container;
import uk.co.oaktest.messages.MessageFactory;
import uk.co.oaktest.messages.interfaces.MessageInterface;
import uk.co.oaktest.messages.jackson.Message;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/runTest")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OakTestResource {
    final static Logger logger = Logger.getLogger(OakTestResource.class);

    @POST
    public void runTest(String body) {
        MessageInterface publishedTestMessage = MessageFactory.createMessage(body);
        if (publishedTestMessage == null) {
            logger.error("HTTP message could not be converted into a test: " + body);
            return;
        }

        Container container = new Container(publishedTestMessage);

        BrowserTest browser = new BrowserTest(container);
        browser.test();
    }
}
