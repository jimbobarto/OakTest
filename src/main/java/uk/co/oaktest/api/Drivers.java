package uk.co.oaktest.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Configuration;
import org.apache.log4j.Logger;
import uk.co.oaktest.constants.MessageSource;
import uk.co.oaktest.drivers.DriverDatabase;
import uk.co.oaktest.messages.jackson.Driver;
import uk.co.oaktest.messages.jackson.TestMessage;
import uk.co.oaktest.rabbit.OakRunnable;

import javax.validation.Valid;
import javax.ws.rs.*;
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

    @GET
    @Path("/{browser}")
    public String getBrowser(@PathParam("browser") String browser) {
        DriverDatabase driverDatabase = new DriverDatabase();

        HashMap versionMap = new HashMap<>();
        versionMap.put("version", driverDatabase.getDriverVersion(browser));

        return convertToJsonString(versionMap);
    }

    @POST
    @Path("/{browser}")
    public String setBrowser(@Valid Driver driver, @PathParam("browser") String browser) {
        HashMap urlMap = new HashMap<>();
        urlMap.put("url", driver.getUrl());

        return convertToJsonString(urlMap);
    }

    private String convertToJsonString(HashMap data) {
        String dataJson = null;
        try {
            dataJson = new ObjectMapper().writeValueAsString(data);
        }
        catch(JsonProcessingException jsonException) {
            logger.error("Could not convert data to JSON string");
        }

        return dataJson;
    }
}
