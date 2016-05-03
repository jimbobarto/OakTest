package uk.co.oaktest.api;

import io.dropwizard.Configuration;
import org.apache.log4j.Logger;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
public class ThreadStatus extends Configuration {
    final static Logger logger = Logger.getLogger(ThreadStatus.class);
    ThreadPoolExecutor executor;

    public ThreadStatus(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    @GET
    public Status status() {
        return new Status(executor.getActiveCount(), executor.getCorePoolSize() - executor.getActiveCount());
    }
}
