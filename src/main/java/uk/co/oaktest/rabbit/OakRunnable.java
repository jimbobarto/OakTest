package uk.co.oaktest.rabbit;

import uk.co.oaktest.browser.BrowserTest;
import uk.co.oaktest.container.Container;
import uk.co.oaktest.messages.interfaces.MessageInterface;
import uk.co.oaktest.messages.MessageException;
import uk.co.oaktest.messages.json.JsonMessage;

import java.io.UnsupportedEncodingException;

public class OakRunnable implements Runnable {
    String rabbitMessage;

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
        MessageInterface publishedTestMessage;
        try {
            publishedTestMessage = new JsonMessage(this.rabbitMessage);
        }
        catch (MessageException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }

        Container container = new Container(publishedTestMessage);

        BrowserTest browser = new BrowserTest(container);
        browser.test();
    }
}
