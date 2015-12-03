package browsers;

import json.GetJson;
import org.junit.Test;
import uk.co.hyttioaboa.browser.BrowserTest;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;
import uk.co.hyttioaboa.messages.json.JsonMessage;

import java.util.ArrayList;
import java.util.Iterator;

public class BrowserTests {
    @Test
    public void browserShouldStart() {
        GetJson jsonGetter = new GetJson();
        String jsonDefinition = jsonGetter.getTestMessage();

        MessageInterface testMessage = new JsonMessage(jsonDefinition);

        BrowserTest browser = new BrowserTest(testMessage);
        browser.test();
    }
}
