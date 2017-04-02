package uk.co.oaktest.drivers;

import org.apache.log4j.Logger;
import uk.co.oaktest.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DriverDatabase {

    Database database;

    final static Logger logger = Logger.getLogger(DriverDatabase.class);

    public DriverDatabase() {
        this.database = new Database();
        if (!this.database.checkTableExists("driver")) {
            if (this.database.createTable("create table driver (id integer, name string, version string)")) {
                this.database.executeUpdate("insert into driver values(1, 'chrome', '33')");
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

        return driverVersion;
    }
}
