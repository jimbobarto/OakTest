package browsers;

import org.junit.Test;
import uk.co.hyttioaboa.browser.BrowserTest;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.json.JsonMessage;

public class BrowserTests {
    @Test
    public void browserShouldStart() {
        String jsonDefinition = "{\"url\":\"http://www.bbc.co.uk\", \"pages\":[{\"name\":\"1\", \"elements\":[{\"index\": 1, \"instruction\": \"elementId\", \"type\": \"link\", \"interaction\": \"click\", \"value\": \"\", \"timeout\": 10}, {\"index\": 2, \"instruction\": \"elementName\", \"type\": \"link\", \"interaction\": \"click\", \"value\": \"\", \"timeout\": 15}]},{\"name\":\"2\"}]}";
        MessageInterface testMessage = new JsonMessage(jsonDefinition);

        BrowserTest test = new BrowserTest(testMessage);
    }
}
