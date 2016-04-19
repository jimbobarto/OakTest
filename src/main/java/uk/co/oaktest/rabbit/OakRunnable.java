package uk.co.oaktest.rabbit;

import org.apache.log4j.Logger;
import uk.co.oaktest.browserTests.BrowserTest;
import uk.co.oaktest.container.Container;
import uk.co.oaktest.messages.MessageFactory;
import uk.co.oaktest.messages.interfaces.MessageInterface;
import uk.co.oaktest.messages.MessageException;
import uk.co.oaktest.messages.json.JsonMessage;

import java.io.UnsupportedEncodingException;

public class OakRunnable implements Runnable {
    String rabbitMessage;
    final static Logger logger = Logger.getLogger(OakRunnable.class);

    public OakRunnable(byte[] byteMessage) {
        String message;
        try {
            message = new String(byteMessage, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }
        this.rabbitMessage = message;
    }

    public OakRunnable(String newMessage) {
        this.rabbitMessage = newMessage;
    }

    public void run() {
        MessageInterface publishedTestMessage = MessageFactory.createMessage(this.rabbitMessage);
        if (publishedTestMessage == null) {
            logger.error("Rabbit message could not be converted into a test: "+ this.rabbitMessage);
            return;
        }

        Container container = new Container(publishedTestMessage);

        BrowserTest browser = new BrowserTest(container);
        browser.test();
    }
}
