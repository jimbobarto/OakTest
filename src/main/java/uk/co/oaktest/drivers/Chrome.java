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
}
