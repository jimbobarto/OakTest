package uk.co.oaktest.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Configuration;
import org.apache.log4j.Logger;
import uk.co.oaktest.drivers.DriverDatabase;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

@Path("/drivers")
@Produces(MediaType.APPLICATION_JSON)
public class Drivers extends Configuration {

    final static Logger logger = Logger.getLogger(Drivers.class);

    public Drivers() {}

    @GET
    public String getDrivers() {
        DriverDatabase driverDatabase = new DriverDatabase();
        HashMap drivers = driverDatabase.getDriverVersions();

        String driverJson = null;
        try {
            driverJson = new ObjectMapper().writeValueAsString(drivers);
        }
        catch(JsonProcessingException jsonException) {
            logger.error("Could not convert drivers output to JSON string");
        }

        return driverJson;
    }
}
