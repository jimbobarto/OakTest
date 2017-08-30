package uk.co.oaktest.drivers.resources;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import uk.co.oaktest.drivers.DriverDatabase;
import uk.co.oaktest.messages.xml.XmlNode;
import uk.co.oaktest.requests.Request;
import uk.co.oaktest.requests.RequestException;
import uk.co.oaktest.utils.Zip;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class InternetExplorer extends GenericDriver {

    final static Logger logger = Logger.getLogger(InternetExplorer.class);

    private String latestVersionUrl = "";
    private String allVersionsUrl = "http://selenium-release.storage.googleapis.com/";

    public String getLatestAvailableVersion() {
        return super.getLatestAvailableVersion(this.latestVersionUrl);
    }

    public String getCurrentVersion() {
        return getCurrentVersionFromDatabase("internet_explorer");
    }

    public Boolean setCurrentVersion(String driverVersion) {
        return setCurrentVersion("internet_explorer", driverVersion);
    }

    public ArrayList<String> getAllAvailableVersions() {
        ArrayList<String> allVersions = new ArrayList<>();
        try {
            Request request = new Request();
            Integer responseStatus = request.get(allVersionsUrl);
            if (responseStatus == 200) {
                XmlNode allVersionNodes = new XmlNode(request.getBody());
                ArrayList<Node> nodes = allVersionNodes.getChildren(allVersionNodes.getTopNode(), "Contents");
                for (Node contentNode: nodes) {
                    String key = allVersionNodes.getChild(contentNode, "Key").getTextContent();
                    //2.39/IEDriverServer_Win32_2.39.0.zip
                    String[] versionParts = key.split("/");

                    if (versionParts.length > 1 && versionParts[1].contains("IEDriverServer_Win32_")) {
                        String version = versionParts[1].replaceAll("IEDriverServer_Win32_", "");
                        version = version.replaceAll(".zip", "");
                        if (!allVersions.contains(version)) {
                            allVersions.add(version);
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

    public HashMap downloadVersion(String version) {
        HashMap<String, String> results = new HashMap<>();
        results.put("status", Integer.toString(Response.Status.BAD_REQUEST.getStatusCode()));

        String parentVersion = version.replaceAll("\\.\\d+$", "");
        String downloadLocation = allVersionsUrl + parentVersion + "/IEDriverServer_Win32_" + version + ".zip";
        URL downloadLocationUrl;
        try {
            downloadLocationUrl = new URL(downloadLocation);
            String downloadDirectory = "downloaded_drivers/internet_explorer/" + version + "/";
            String downloadDestination = downloadDirectory + "internet_explorer.zip";
            File saveFile = new File(downloadDestination);
            FileUtils.copyURLToFile(downloadLocationUrl, saveFile, 60000, 60000);
            Zip unzipper = new Zip(downloadDestination, downloadDirectory);
            if (unzipper.unzip()) {
                File unzippedDriver = new File(downloadDirectory + "IEDriverServer.exe");
                if (unzippedDriver.exists()) {
                    if (saveFile.delete()) {
                        DriverDatabase driverDb = new DriverDatabase();
                        driverDb.addInstalledDriver("internet_explorer", version, unzippedDriver.getAbsolutePath());
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
