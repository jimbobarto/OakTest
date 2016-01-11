package browsers;

import org.json.JSONException;
import org.json.JSONObject;
import uk.co.hyttioaboa.fileContents.GetFileContents;
import org.junit.Test;
import uk.co.hyttioaboa.browser.BrowserTest;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.MessageException;
import uk.co.hyttioaboa.messages.json.JsonMessage;
import uk.co.hyttioaboa.messages.xml.XmlMessage;
import uk.co.hyttioaboa.results.ResponseNode;

public class BrowserTests {
    @Test
    public void browserTestFromJson() {
        GetFileContents fileGetter = new GetFileContents();
        String jsonDefinition = fileGetter.getTestMessage("src/test/resources/testMessage.json");

        MessageInterface testMessage;
        try {
            testMessage = new JsonMessage(jsonDefinition);
        }
        catch (MessageException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }

        BrowserTest browser = new BrowserTest(testMessage);
        browser.test();
    }

    @Test
    public void browserTestFromXml() {
        GetFileContents fileGetter = new GetFileContents();
        String xmlMessage = fileGetter.getTestMessage("src/test/resources/testMessage.xml");

        MessageInterface testMessage = new XmlMessage(xmlMessage);

        BrowserTest browser = new BrowserTest(testMessage);
        browser.test();
    }

    @Test
    public void linkIDShouldClickLink() {
        GetFileContents fileGetter = new GetFileContents();
        String jsonDefinition = fileGetter.getTestMessage("src/test/resources/testMessage2.json");

        MessageInterface testMessage;
        try {
            testMessage = new JsonMessage(jsonDefinition);
        }
        catch (MessageException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }

        BrowserTest browser = new BrowserTest(testMessage);
        browser.test();

        ResponseNode reporterNode = browser.getResponseNode();
        JSONObject report = reporterNode.createReport();
        try {
            System.out.println(report.toString(3));
        }
        catch (JSONException ex) {
            System.out.println("Badness");
        }
    }

    @Test
    public void enterSearchValue() {
        GetFileContents fileGetter = new GetFileContents();
        String jsonDefinition = fileGetter.getTestMessage("src/test/resources/testMessage3.json");

        MessageInterface testMessage;
        try {
            testMessage = new JsonMessage(jsonDefinition);
        }
        catch (MessageException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }

        BrowserTest browser = new BrowserTest(testMessage);
        browser.test();
    }

}
