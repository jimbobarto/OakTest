package uk.co.oaktest.api;

import io.dropwizard.Configuration;
import org.apache.log4j.Logger;
import uk.co.oaktest.browserTests.BrowserTest;
import uk.co.oaktest.container.Container;
import uk.co.oaktest.messages.jackson.TestMessage;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/acceptTest")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestResource extends Configuration {
    final static Logger logger = Logger.getLogger(TestResource.class);

    @POST
    public TestMessage acceptTest(@Valid TestMessage testMessage) {
//        testMessage.setName("Name has been set!");
//        return testMessage;
        Container container = new Container(testMessage);

        BrowserTest browser = new BrowserTest(container);
        browser.test();

        return testMessage;
    }
}
