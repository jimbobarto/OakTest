package browsers;

import uk.co.hyttioaboa.fileContents.GetFileContents;
import org.junit.Test;
import uk.co.hyttioaboa.browser.BrowserTest;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.json.JsonMessage;
import uk.co.hyttioaboa.messages.xml.XmlMessage;

public class BrowserTests {
    @Test
    public void browserTestFromJson() {
        GetFileContents fileGetter = new GetFileContents();
        String jsonDefinition = fileGetter.getTestMessage("src/test/resources/testMessage.json");

        MessageInterface testMessage = new JsonMessage(jsonDefinition);

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
    public void validXMLSHouldClickLink() {
        GetFileContents fileGetter = new GetFileContents();
        String xmlMessage = fileGetter.getTestMessage("src/test/resources/testMessage2.json");

        MessageInterface testMessage = new XmlMessage(xmlMessage);
        // assert statements
        //TODO add in check that it correctly reports link was clicked
        //assertEquals("Number of pages in the test message should be 2", 2, testMessage.getPages().size());
    }
}
