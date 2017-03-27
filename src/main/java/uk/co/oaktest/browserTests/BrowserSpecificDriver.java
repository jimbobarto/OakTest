package uk.co.oaktest.browserTests;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.database.Database;
import uk.co.oaktest.results.ResponseMessage;

public class BrowserSpecificDriver {

    private String browser;

    final static Logger logger = Logger.getLogger(BrowserSpecificDriver.class);

    BrowserSpecificDriver(String browser) {
        this.browser = browser;
        new Database();
    }

    public WebDriver getDriver() {
        WebDriver driver;

        if (this.browser != null) {
            try {
                switch (this.browser) {
                    case "Internet Explorer":
                        driver = new InternetExplorerDriver();
                        break;
                    case "Chrome":
                        driver = new ChromeDriver();
                        break;
                    case "Firefox":
                    default:
                        driver = new FirefoxDriver();
                        break;
                }
            } catch (Exception exception) {
                logger.error(exception.getMessage());
                return null;
            }
        }
        else {
            driver = new FirefoxDriver();
        }

        return driver;
    }

}
