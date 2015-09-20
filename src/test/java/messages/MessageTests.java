package messages;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import uk.co.hyttioaboa.messages.json.JsonMessage;

/**
 * Created by jamesbartlett on 13/09/15.
 */
public class MessageTests {
    @Test
    public void validJsonShouldCreateMessage() {

        String definition = "{\"pages\":[{\"name\":\"1\"},{\"name\":\"2\"}]}";
        JsonMessage testMessage = new JsonMessage(definition);

        // assert statements
        assertEquals("Number of pages in the test message should be 2", 2, testMessage.getPages().size());
    }
}
