package uk.co.oaktest.drivers.resources;

import org.apache.log4j.Logger;
import uk.co.oaktest.database.Database;
import uk.co.oaktest.requests.Request;
import uk.co.oaktest.requests.RequestException;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public abstract class GenericDriver {

    final static Logger logger = Logger.getLogger(GenericDriver.class);

    public abstract String getLatestAvailableVersion();

    public String getLatestAvailableVersion(String latestVersionUrl) {
        try {
            Request request = new Request();
            Integer responseStatus = request.get(latestVersionUrl);
            if (responseStatus == 200) {
                return request.getBody().replaceAll("\\s", "");
            }
        }
        catch (RequestException requestException) {
            return null;
        }
        return null;
    }

    public abstract ArrayList<String> getAllAvailableVersions();

    public abstract String getCurrentVersion();

    public abstract HashMap downloadVersion(String version);

    public String getCurrentVersionFromDatabase() {
        Database database = new Database();
        try {
            if (database.checkTableExists("current_versions")) {
                ResultSet resultSet = database.query("select * from current_versions where name=chrome");
                if (resultSet == null) {
                    return null;
                }
                if (!resultSet.isBeforeFirst() ) {
                    return null;
                }
                else {
                    while (resultSet.next()) {
                        return resultSet.getString("version");
                    }
                }
            } else {
                return null;
            }
        }
        catch(SQLException sqlException) {
            logger.error("Could not identify driver: " + sqlException.getMessage());
        }
        return null;
    }

    public String readVersionInfoInManifest(Class driverClass, String propertiesPath){
        // Taken from http://stackoverflow.com/a/12571330

        String version = null;
        try {
            Properties p = new Properties();
            InputStream is = driverClass.getResourceAsStream(propertiesPath);
            if (is != null) {
                p.load(is);
                version = p.getProperty("version", "");
                if (version != null) {
                    return version;
                }
            }
        } catch (Exception e) {
            // ignore
        }

        Package driverPackage = driverClass.getPackage();
        //examine the package object

        version = driverPackage.getSpecificationVersion();
        if (version != null) {
            return version;
        }

        return driverPackage.getImplementationVersion();
    }

}
