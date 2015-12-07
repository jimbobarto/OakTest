package messages;

import uk.co.hyttioaboa.fileContents.GetFileContents;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.json.JsonMessage;
import uk.co.hyttioaboa.messages.xml.XmlMessage;

public class MessageTests {
    Integer index;
    String instruction;
    String type;
    String interaction;
    String value;
    String timeout;

    @Test
    public void validJsonShouldCreateMessage() {
        GetFileContents fileGetter = new GetFileContents();
        String jsonDefinition = fileGetter.getTestMessage("src/test/resources/testMessage.json");

        MessageInterface testMessage = new JsonMessage(jsonDefinition);
        // assert statements
        assertEquals("Number of pages in the test message should be 2", 2, testMessage.getPages().size());
    }

    @Test
    public void validXmlShouldCreateMessage() {
        GetFileContents fileGetter = new GetFileContents();
        String xmlMessage = fileGetter.getTestMessage("src/test/resources/testMessage.xml");

        MessageInterface testMessage = new XmlMessage(xmlMessage);
        // assert statements
        assertEquals("Number of pages in the test message should be 2", 2, testMessage.getPages().size());
    }
}
