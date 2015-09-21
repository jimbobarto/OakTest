package messages;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import uk.co.hyttioaboa.messages.MessageInterface;
import uk.co.hyttioaboa.messages.json.JsonMessage;
import uk.co.hyttioaboa.messages.xml.XmlMessage;

/**
 * Created by jamesbartlett on 13/09/15.
 */
public class MessageTests {
    Integer index;
    String instruction;
    String type;
    String interaction;
    String value;
    String timeout;

    @Test
    public void validJsonShouldCreateMessage() {

        String jsonDefinition = "{\"url\":\"http://www.bbc.co.uk\", \"pages\":[{\"name\":\"1\", \"elements\":[{\"index\": 1, \"instruction\": \"elementId\", \"type\": \"link\", \"interaction\": \"click\", \"value\": \"\", \"timeout\": \"\"}, {\"index\": 2, \"instruction\": \"elementName\", \"type\": \"link\", \"interaction\": \"click\", \"value\": \"\", \"timeout\": \"\"}]},{\"name\":\"2\"}]}";

        MessageInterface testMessage = new JsonMessage(jsonDefinition);
        // assert statements
        assertEquals("Number of pages in the test message should be 2", 2, testMessage.getPages().size());
    }

    @Test
    public void validXmlShouldCreateMessage() {

        //String jsonDefinition = "{\"url\":\"http://www.bbc.co.uk\", \"pages\":[{\"name\":\"1\", \"elements\":[{\"index\": 1, \"instruction\": \"elementId\", \"type\": \"link\", \"interaction\": \"click\", \"value\": \"\", \"timeout\": \"\"}, {\"index\": 2, \"instruction\": \"elementName\", \"type\": \"link\", \"interaction\": \"click\", \"value\": \"\", \"timeout\": \"\"}]},{\"name\":\"2\"}]}";
        String xmlMessage = "<message><pages><page><elements><element \"instruction\"=\"\"><instruction>elementId</instruction></element></elements></page></pages></message>";

        MessageInterface testMessage = new XmlMessage(xmlMessage);
        // assert statements
        assertEquals("Number of pages in the test message should be 1", 1, testMessage.getPages().size());
    }
}
