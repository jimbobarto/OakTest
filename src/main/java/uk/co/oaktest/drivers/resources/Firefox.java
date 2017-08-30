package uk.co.oaktest.drivers.resources;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.firefox.FirefoxDriver;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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

    public Boolean setCurrentVersion(String driverVersion) {
        return setCurrentVersion("firefox", driverVersion);
    }

    public HashMap downloadVersion(String version) {
        HashMap<String, String> results = new HashMap<>();
        results.put("status", Integer.toString(Response.Status.BAD_REQUEST.getStatusCode()));
        results.put("message", "Not available for Firefox");
        return results;
    }
}
