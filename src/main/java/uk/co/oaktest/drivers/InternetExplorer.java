package uk.co.oaktest.drivers;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import uk.co.oaktest.constants.OperatingSystemType;
import uk.co.oaktest.database.Database;
import uk.co.oaktest.messages.xml.XmlNode;
import uk.co.oaktest.requests.Request;
import uk.co.oaktest.requests.RequestException;
import uk.co.oaktest.utils.OperatingSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InternetExplorer extends GenericDriver {

    final static Logger logger = Logger.getLogger(InternetExplorer.class);

    private String latestVersionUrl = "";
    private String allVersionsUrl = "http://selenium-release.storage.googleapis.com/";

    public String getLatestAvailableVersion() {
        return super.getLatestAvailableVersion(this.latestVersionUrl);
    }

    public String getCurrentVersion() {
        return getCurrentVersionFromDatabase();
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

}
