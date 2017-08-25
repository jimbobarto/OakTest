package uk.co.oaktest.drivers;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import uk.co.oaktest.database.Database;

import java.io.InputStream;
import java.util.Properties;

public class BrowserSpecificDriver {

    private String browser;

    final static Logger logger = Logger.getLogger(BrowserSpecificDriver.class);

    public BrowserSpecificDriver(String browser) {
        this.browser = browser;
        //new Database();
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
            Package objPackage = driver.getClass().getPackage();
            System.out.println("Implementation version: " + readVersionInfoInManifest(driver.getClass(), ""));
        }

        //readVersionInfoInManifest(driver);
        return driver;
    }

    public String readVersionInfoInManifest(Class driverClass, String propertiesPath){
        // Taken from http://stackoverflow.com/a/12571330

        String version = null;
        try {
            Properties p = new Properties();
            //InputStream is = driverClass.getResourceAsStream("/META-INF/maven/org.seleniumhq.selenium/selenium-firefox-driver/pom.properties");
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

    public String getDefaultIEVersion() {
        return readVersionInfoInManifest(org.openqa.selenium.ie.InternetExplorerDriver.class, "");
    }

    public String getDefaultFirefoxVersion() {
        return readVersionInfoInManifest(org.openqa.selenium.firefox.FirefoxDriver.class, "/META-INF/maven/org.seleniumhq.selenium/selenium-firefox-driver/pom.properties");
    }

    public String getDefaultChromeVersion() {
        return readVersionInfoInManifest(org.openqa.selenium.chrome.ChromeDriver.class, "");
    }
}
