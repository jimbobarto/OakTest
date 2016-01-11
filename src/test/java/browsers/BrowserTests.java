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

import static org.junit.Assert.assertEquals;


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


        ResponseNode node = browser.getResponseNode();
        //ResponseNode targetNode = node.getNodeByPath("stuff/stuff/stuff");

        assertEquals(200,node.getStatus(),0);


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


        ResponseNode node = browser.getResponseNode();

        assertEquals(200,node.getNodeByPath("Example test/Do Stuff/Amazon search field").getStatus(),0);

        assertEquals(200,node.getNodeByPath("Example test[0]/Do Stuff[0]/Amazon search field[0]").getStatus(),0);
    }

}
