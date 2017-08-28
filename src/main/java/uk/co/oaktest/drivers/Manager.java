package uk.co.oaktest.drivers;

import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import uk.co.oaktest.constants.Browser;

import java.util.ArrayList;

public class Manager {

    final static Logger logger = Logger.getLogger(Manager.class);

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

}
