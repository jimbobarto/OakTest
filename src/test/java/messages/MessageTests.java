package messages;

import org.json.JSONObject;
import uk.co.hyttioaboa.fileContents.GetFileContents;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.MessageException;
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
        String jsonDefinition = new GetFileContents().getTestMessage("src/test/resources/testMessage.json");

        MessageInterface testMessage;
        try {
            testMessage = new JsonMessage(jsonDefinition);
        }
        catch (MessageException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }
        // assert statements
        assertEquals("Number of pages in the test message should be 1", 1, testMessage.getPages().size());
    }

    @Test
    public void validXmlShouldCreateMessage() {
        String xmlMessage = new GetFileContents().getTestMessage("src/test/resources/testMessage.xml");

        MessageInterface testMessage = new XmlMessage(xmlMessage);
        // assert statements
        assertEquals("Number of pages in the test message should be 2", 2, testMessage.getPages().size());
    }

    @Test
    public void jsonWithNoNameShouldError() {
        String messageWithNoName = new GetFileContents().getTestMessage("src/test/resources/testMessageNoName.json");

        assertEquals("Missing name should be highlighted", "Message has no Name", getExpectedExceptionMessageFromMessage(messageWithNoName));
    }

    @Test
    public void jsonWithNoUrlShouldError() {
        String messageWithNoUrl = new GetFileContents().getTestMessage("src/test/resources/testMessageNoUrl.json");

        assertEquals("Missing URL should be highlighted", "Message has no URL", getExpectedExceptionMessageFromMessage(messageWithNoUrl));
    }

    @Test
    public void jsonWithNoPageNameShouldError() {
        String messageWithPageNoName = new GetFileContents().getTestMessage("src/test/resources/testMessageNoPageName.json");

        assertEquals("Missing page name should be highlighted", "JSON object has no name defined", getExpectedExceptionMessageFromMessage(messageWithPageNoName));
    }

    @Test
    public void jsonWithNoElementsOnAPageShouldError() {
        String messageWithNoPageElements = new GetFileContents().getTestMessage("src/test/resources/testMessageNoPageElements.json");

        assertEquals("Missing page elements should be highlighted", "Could not identify page type from message (message does not even have a URI)", getExpectedExceptionMessageFromMessage(messageWithNoPageElements));
    }

    @Test
    public void jsonWithNoElementName() {
        String messageWithNoElementName = new GetFileContents().getTestMessage("src/test/resources/testMessageNoElementName.json");

        assertEquals("Missing element name should be highlighted", "Element has no name", getExpectedExceptionMessageFromMessage(messageWithNoElementName));
    }

    @Test
    public void jsonWithNoElementIdentifier() {
        String messageWithNoElementIdentifier = new GetFileContents().getTestMessage("src/test/resources/testMessageNoElementIdentifier.json");

        assertEquals("Missing element identifier should be highlighted", "Element has no identifier", getExpectedExceptionMessageFromMessage(messageWithNoElementIdentifier));
    }

    @Test
    public void jsonWithNoElementIdentifierType() {
        String messageWithNoElementIdentifierType = new GetFileContents().getTestMessage("src/test/resources/testMessageNoElementIdentifierType.json");

        assertEquals("Missing element identifier type should be highlighted", "Element has no identifier type", getExpectedExceptionMessageFromMessage(messageWithNoElementIdentifierType));
    }

    @Test
    public void jsonWithNoElementType() {
        String messageWithNoElementType = new GetFileContents().getTestMessage("src/test/resources/testMessageNoElementType.json");

        assertEquals("Missing element type should be highlighted", "Element has no type", getExpectedExceptionMessageFromMessage(messageWithNoElementType));
    }

    @Test
    public void jsonWithNoElementInteraction() {
        String messageWithNoElementInteraction = new GetFileContents().getTestMessage("src/test/resources/testMessageNoElementInteraction.json");

        assertEquals("Missing element interaction should be highlighted", "Element has no interaction", getExpectedExceptionMessageFromMessage(messageWithNoElementInteraction));
    }

    private String getExpectedExceptionMessageFromMessage(String message) {
        try {
            MessageInterface testMessage = new JsonMessage(message);
        }
        catch(MessageException jsonException) {
            return jsonException.getMessage();
        }
        return "";
    }
}
