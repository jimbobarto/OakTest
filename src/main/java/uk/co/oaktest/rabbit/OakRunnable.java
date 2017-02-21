package uk.co.oaktest.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import uk.co.oaktest.apiTests.ApiTest;
import uk.co.oaktest.browserTests.BrowserTest;
import uk.co.oaktest.constants.MessageSource;
import uk.co.oaktest.containers.Container;
import uk.co.oaktest.messages.jackson.TestMessage;

import java.io.UnsupportedEncodingException;

public class OakRunnable implements Runnable {
    TestMessage testMessage;
    MessageSource messageSource;
    final static Logger logger = Logger.getLogger(OakRunnable.class);

    public OakRunnable(byte[] byteMessage) {
        String message;
        try {
            message = new String(byteMessage, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }
        this.testMessage = getMessages(message);
    }

    public OakRunnable(byte[] byteMessage, MessageSource messageSource) {
        String message;
        try {
            message = new String(byteMessage, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }
        this.testMessage = getMessages(message);
    }

    public OakRunnable(String newMessage) {
        this.testMessage = getMessages(newMessage);
    }

    public OakRunnable(TestMessage newMessage) {
        this.testMessage = newMessage;
    }

    public OakRunnable(TestMessage newMessage, MessageSource messageSource) {
        this.testMessage = newMessage;
        this.messageSource = messageSource;
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
        Container container = new Container(this.testMessage);

        if ( this.testMessage.getType().equals("api") ) {
            ApiTest api = new ApiTest(container, messageSource);
            api.test();
        }
        else {
            BrowserTest browser = new BrowserTest(container, messageSource);
            browser.test();
        }
    }
}
