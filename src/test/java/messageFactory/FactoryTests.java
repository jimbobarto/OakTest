package messageFactory;

import org.junit.Assert;
import org.junit.Test;
import uk.co.oaktest.browser.BrowserTest;
import uk.co.oaktest.containers.TestContainer;
import uk.co.oaktest.fileContents.GetFileContents;
import uk.co.oaktest.messages.MessageFactory;
import uk.co.oaktest.messages.interfaces.MessageInterface;

public class FactoryTests {
    @Test
    public void browserTestFromJson() {
        GetFileContents fileGetter = new GetFileContents();
        String jsonMessage = fileGetter.getTestMessage("src/test/resources/factory/testMessage.json");

        MessageInterface testMessage = MessageFactory.createMessage(jsonMessage);
        if (testMessage == null) {
            Assert.fail("JSON message could not be constructed from source");
            return;
        }

        BrowserTest browser = new BrowserTest(new TestContainer(testMessage));
        browser.test();
    }

    @Test
    public void browserTestFromXml() {
        GetFileContents fileGetter = new GetFileContents();
        String xmlMessage = fileGetter.getTestMessage("src/test/resources/factory/testMessage.xml");

        MessageInterface testMessage = MessageFactory.createMessage(xmlMessage);
        if (testMessage == null) {
            Assert.fail("XML message could not be constructed from source");
            return;
        }

        BrowserTest browser = new BrowserTest(new TestContainer(testMessage));
        browser.test();
    }


}
