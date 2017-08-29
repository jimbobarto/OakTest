package uk.co.oaktest.drivers.resources;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import uk.co.oaktest.constants.OperatingSystemType;
import uk.co.oaktest.drivers.DriverDatabase;
import uk.co.oaktest.messages.xml.XmlNode;
import uk.co.oaktest.requests.Request;
import uk.co.oaktest.requests.RequestException;
import uk.co.oaktest.utils.OperatingSystem;
import uk.co.oaktest.utils.Zip;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Chrome extends GenericDriver {

    final static Logger logger = Logger.getLogger(Chrome.class);

    private String latestVersionUrl = "https://chromedriver.storage.googleapis.com/LATEST_RELEASE";
    private String allVersionsUrl = "https://chromedriver.storage.googleapis.com/";

    public String getLatestAvailableVersion() {
        return super.getLatestAvailableVersion(this.latestVersionUrl);
    }

    public ArrayList<String> getAllAvailableVersions() {
        ArrayList<String> allVersions = new ArrayList<>();
        OperatingSystemType os = OperatingSystem.getOS();
        try {
            Request request = new Request();
            Integer responseStatus = request.get(allVersionsUrl);
            if (responseStatus == 200) {
                XmlNode allVersionNodes = new XmlNode(request.getBody());
                ArrayList<Node> nodes = allVersionNodes.getChildren(allVersionNodes.getTopNode(), "Contents");
                for (Node contentNode: nodes) {
                    String key = allVersionNodes.getChild(contentNode, "Key").getTextContent();
                    String[] versionParts = key.split("/");

                    if (versionParts.length > 1) {
                        OperatingSystemType nodeOperatingSystem = OperatingSystem.getOS(versionParts[1]);
                        if (nodeOperatingSystem != null && nodeOperatingSystem == os) {
                            if (!allVersions.contains(versionParts[0])) {
                                allVersions.add(versionParts[0]);
                            }
                        }
                    }
                }
                return allVersions;
            }
        }
        catch (RequestException requestException) {
            return null;
        }
        return null;
    }

    public String getCurrentVersion() {
        return getCurrentVersionFromDatabase();
    }

    public HashMap downloadVersion(String version) {
        HashMap<String, String> results = new HashMap<>();
        results.put("status", Integer.toString(Response.Status.BAD_REQUEST.getStatusCode()));

        String downloadLocation = allVersionsUrl + version + "/chromedriver_win32.zip";
        URL downloadLocationUrl;
        try {
            downloadLocationUrl = new URL(downloadLocation);
            String downloadDirectory = "downloaded_drivers/chrome/" + version + "/";
            String downloadDestination = downloadDirectory + "chrome.zip";
            File saveFile = new File(downloadDestination);
            FileUtils.copyURLToFile(downloadLocationUrl, saveFile, 60000, 60000);

            Zip unzipper = new Zip(downloadDestination, downloadDirectory);
            if (unzipper.unzip()) {
                File unzippedDriver = new File(downloadDirectory + "chromedriver.exe");
                if (unzippedDriver.exists()) {
                    if (saveFile.delete()) {
                        DriverDatabase driverDb = new DriverDatabase();
                        driverDb.addInstalledDriver("chrome", version, unzippedDriver.getAbsolutePath());
                    }
                    else {
                        results.put("message", "zipped file could not be deleted");
                    }
                }
                else {
                    results.put("message", "driver hasn't unzipped as expected");
                }
            }
        }
        catch (MalformedURLException urlException) {
            results.put("message", "URL was bad");
        }
        catch (IOException ioException) {
            results.put("message", "Malformed URL: " + ioException.getMessage());
        }

        if (!results.containsKey("message")) {
            results.put("status", Integer.toString(Response.Status.OK.getStatusCode()));
            results.put("message", "Downloaded OK");
        }

        return results;
    }
}
