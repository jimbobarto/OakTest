package uk.co.oaktest.api;

import io.dropwizard.Configuration;
import org.apache.log4j.Logger;
import uk.co.oaktest.browserTests.BrowserTest;
import uk.co.oaktest.container.Container;
import uk.co.oaktest.messages.jackson.TestMessage;
import uk.co.oaktest.rabbit.OakRunnable;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ThreadPoolExecutor;

@Path("/runTest")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RunTest extends Configuration {
    final static Logger logger = Logger.getLogger(RunTest.class);
    ThreadPoolExecutor executor;

    public RunTest(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    @POST
    public TestMessage acceptTest(@Valid TestMessage testMessage) {
        Runnable task = new OakRunnable(testMessage);
        this.executor.submit(task);

        return testMessage;
    }
}
