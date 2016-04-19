package uk.co.oaktest.api;

import org.apache.log4j.Logger;
import uk.co.oaktest.messages.jackson.Message;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/acceptTest")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {
    final static Logger logger = Logger.getLogger(OakTestResource.class);

    @POST
    public Message acceptTest(@Valid Message message) {
        message.setName("Name has been set!");
        return message;
    }
}
