package uk.co.oaktest.drivers;

import org.apache.log4j.Logger;
import uk.co.oaktest.constants.Browser;
import uk.co.oaktest.database.Database;
import uk.co.oaktest.drivers.resources.Firefox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DriverDatabase {

    Database database;

    final static Logger logger = Logger.getLogger(DriverDatabase.class);

    public DriverDatabase() {
        this.database = new Database();
        Firefox firefox = new Firefox();
        if (!this.database.checkTableExists("driver")) {
            if (this.database.createTable("create table driver (id INTEGER PRIMARY KEY, name string, version string, path string, current INTEGER)")) {
                this.database.executeUpdate("insert into driver values(null, 'firefox', '" + firefox.getCurrentVersion() + "', '', 1)");
            }
        }
    }

    public HashMap getDriverVersions() {
        HashMap driverVersions = new HashMap();
        try {
            if (this.database.checkTableExists("driver")) {
                ResultSet resultSet = this.database.query("select * from driver");
                if (resultSet == null) {
                    return null;
                }
                if (!resultSet.isBeforeFirst() ) {
                    return null;
                }
                else {
                    while (resultSet.next()) {
                        // read the result set
                        logger.info("Name: " + resultSet.getString("name"));
                        logger.info("Version: " + resultSet.getString("version"));
                        driverVersions.put(resultSet.getString("name"), resultSet.getString("version"));
                    }
                }
            } else {
                return null;
            }
        }
        catch(SQLException sqlException) {
            logger.error("Could not identify driver: " + sqlException.getMessage());
        }
        return driverVersions;
    }

    public Boolean setDriverVersion(String driverName, String driverVersion) {
        if (this.database.checkTableExists("driver") && isValidBrowserName(driverName)) {
            if (this.database.executeUpdate("update driver set current=1 where name='" + driverName + "' and version='" + driverVersion + "'")) {
                if (this.database.executeUpdate("update driver set current=0 where name='" + driverName + "' and version!='" + driverVersion + "'")) {
                    return true;
                }
                else {
                    logger.error("Could not reset other versions of '" + driverName + "'");
                    return false;
                }
            }
            else {
                logger.error("Could not set '" + driverName + "' to version '" + driverVersion + "'");
                return false;
            }
        }
        else {
            return false;
        }
    }

    public Boolean addInstalledDriver(String driverName, String driverVersion, String path) {
        if (this.database.checkTableExists("driver") && isValidBrowserName(driverName)) {
            this.database.executeUpdate("insert into driver values(null, '" + driverName + "', '" + driverVersion + "', '" + path + "', 0)");
            return true;
        }
        else {
            return false;
        }
    }

    public String getDriverVersion(String driverName) {
        return getDriverProperty(driverName, "version");
    }

    public String getDriverPath(String driverName) {
        return getDriverProperty(driverName, "path");
    }

    public String getDriverProperty(String driverName, String propertyName) {
        String propertyValue = null;
        if (isValidBrowserName(driverName)) {
            try {
                if (this.database.checkTableExists("driver")) {
                    ResultSet resultSet = this.database.query("select * from driver where name='" + driverName + "' and current=1");
                    if (resultSet == null) {
                        return null;
                    }
                    else {
                        while (resultSet.next()) {
                            // read the result set
                            if (propertyValue != null) {
                                logger.error("More than one driver version for " + driverName + " browser");
                                return null;
                            }
                            propertyValue = resultSet.getString(propertyName);
                        }
                    }
                } else {
                    return null;
                }
            }
            catch(SQLException sqlException) {
                logger.error("Could not identify driver: " + sqlException.getMessage());
            }
        }
        return propertyValue;
    }

    public ArrayList<String> getInstalledVersions(String driverName) {
        ArrayList<String> driverVersions = new ArrayList<>();
        if (isValidBrowserName(driverName)) {
            try {
                if (this.database.checkTableExists("driver")) {
                    ResultSet resultSet = this.database.query("select version from driver where name='" + driverName + "'");
                    if (resultSet == null) {
                        return null;
                    }
                    else {
                        while (resultSet.next()) {
                            driverVersions.add(resultSet.getString("version"));
                        }
                    }
                } else {
                    return null;
                }
            }
            catch(SQLException sqlException) {
                logger.error("Could not identify driver: " + sqlException.getMessage());
            }
        }
        return driverVersions;
    }

    private Boolean isValidBrowserName(String browserName) {
        Browser[] possibleValues = Browser.class.getEnumConstants();
        for (Browser possibleBrowser: possibleValues) {
            if (possibleBrowser.getValue().equals(browserName)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> validBrowsers() {
        ArrayList<String> validBrowsers = new ArrayList<>();
        Browser[] possibleValues = Browser.class.getEnumConstants();
        for (Browser possibleBrowser: possibleValues) {
            validBrowsers.add(possibleBrowser.getValue());
        }
        return validBrowsers;
    }
}
