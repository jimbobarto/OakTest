package uk.co.oaktest.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import uk.co.oaktest.browserTests.BrowserTest;
import uk.co.oaktest.container.Container;
import uk.co.oaktest.messages.jackson.TestMessage;

import java.io.UnsupportedEncodingException;

public class OakRunnable implements Runnable {
    TestMessage rabbitMessage;
    final static Logger logger = Logger.getLogger(OakRunnable.class);

    public OakRunnable(byte[] byteMessage) {
        String message;
        try {
            message = new String(byteMessage, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }
        this.rabbitMessage = getMessages(message);
    }

    public OakRunnable(String newMessage) {
        this.rabbitMessage = getMessages(newMessage);
    }

    public OakRunnable(TestMessage newMessage) {
        this.rabbitMessage = newMessage;
    }

    private TestMessage getMessages(String message) {
        ObjectMapper mapper = new ObjectMapper();
        TestMessage testMessage;
        try {
            testMessage = mapper.readValue(message, TestMessage.class);
        }
        catch (Exception messageException) {
            logger.error("Message could not be converted into a test: "+ messageException.getMessage());
            return null;
        }

        return testMessage;
    }

    public void run() {
        Container container = new Container(this.rabbitMessage);

        BrowserTest browser = new BrowserTest(container);
        browser.test();
    }
}
