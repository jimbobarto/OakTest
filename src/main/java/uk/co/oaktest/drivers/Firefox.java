package uk.co.oaktest.drivers;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.w3c.dom.Node;
import uk.co.oaktest.constants.OperatingSystemType;
import uk.co.oaktest.messages.xml.XmlNode;
import uk.co.oaktest.requests.Request;
import uk.co.oaktest.requests.RequestException;
import uk.co.oaktest.utils.OperatingSystem;

import java.util.ArrayList;

public class Firefox extends GenericDriver {
    private String firefoxPropertiesPath = "/META-INF/maven/org.seleniumhq.selenium/selenium-firefox-driver/pom.properties";

    public String getLatestAvailableVersion() {
        return getCurrentVersion();
    }

    public ArrayList<String> getAllAvailableVersions() {
        ArrayList<String> allVersions = new ArrayList<>();
        allVersions.add(getCurrentVersion());
        return allVersions;
    }

    public String getCurrentVersion() {
        return readVersionInfoInManifest(FirefoxDriver.class, firefoxPropertiesPath);
    }
}
