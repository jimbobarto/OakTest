package uk.co.oaktest.drivers;

import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import uk.co.oaktest.constants.Browser;
import uk.co.oaktest.database.Database;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class DriverDatabase {

    Database database;

    final static Logger logger = Logger.getLogger(DriverDatabase.class);

    public DriverDatabase() {
        this.database = new Database();
        Firefox firefox = new Firefox();
        if (!this.database.checkTableExists("driver")) {
            if (this.database.createTable("create table driver (id integer, name string, version string, path string)")) {
                this.database.executeUpdate("insert into driver values(1, 'firefox', '" + firefox.getCurrentVersion() + "', '')");
            }
        }
        if (!this.database.checkTableExists("current_versions")) {
            if (this.database.createTable("create table current_versions (id integer, name string, version string, path string)")) {
                this.database.executeUpdate("insert into current_versions values(1, 'firefox', '" + firefox.getCurrentVersion() + "', '')");
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
        return false;
    }

    public String getDriverVersion(String driverName) {
        String driverVersion = null;
        if (isValidBrowserName(driverName)) {
            try {
                if (this.database.checkTableExists("driver")) {
                    ResultSet resultSet = this.database.query("select version from driver where name='" + driverName + "'");
                    if (resultSet == null) {
                        return null;
                    }
                    else {
                        while (resultSet.next()) {
                            // read the result set
                            if (driverVersion != null) {
                                logger.error("More than one driver version for " + driverName + " browser");
                            }
                            driverVersion = resultSet.getString("version");
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
        return driverVersion;
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
