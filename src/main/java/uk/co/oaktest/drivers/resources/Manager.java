package uk.co.oaktest.drivers.resources;

import org.apache.log4j.Logger;
import uk.co.oaktest.constants.Browser;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    final static Logger logger = Logger.getLogger(Manager.class);

    GenericDriver managedDriver;

    public Manager() {
    }

    public Manager(String driverName) {
        if (isValidBrowserName(driverName)) {
            managedDriver = getDriver(driverName);
        }
    }

    public ArrayList<String> getAvailableVersions() {
        return managedDriver.getAllAvailableVersions();
    }

    public String getLatestAvailableVersion() {
        return managedDriver.getLatestAvailableVersion();
    }

    public Boolean setVersion(String version) {
        return managedDriver.setCurrentVersion(version);
    }

    public HashMap downloadVersion(String version) {
        return managedDriver.downloadVersion(version);
    }

    public ArrayList<String> getAvailableVersions(String driverName) {
        GenericDriver driver = getDriver(driverName);
        return driver.getAllAvailableVersions();
    }

    public String getLatestAvailableVersion(String driverName) {
        GenericDriver driver = getDriver(driverName);
        return driver.getLatestAvailableVersion();
    }

    public GenericDriver getDriver(String driverName) {
        GenericDriver driver;
        if (isValidBrowserName(driverName)) {
            try {
                switch (driverName) {
                    case "chrome":
                        driver = new Chrome();
                        break;
                    case "internet_explorer":
                        driver = new InternetExplorer();
                        break;
                    case "firefox":
                    default:
                        driver = new Firefox();
                        break;
                }

                return driver;
            } catch (Exception exception) {
                logger.error(exception.getMessage());
                return null;
            }

        }

        return null;
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

    public HashMap downloadVersion(String driverName, String version) {
        //downloadVersion
        GenericDriver driver = getDriver(driverName);
        return driver.downloadVersion(version);
    }
}
