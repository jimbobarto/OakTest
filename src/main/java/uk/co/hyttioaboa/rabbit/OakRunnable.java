package uk.co.hyttioaboa.rabbit;

import uk.co.hyttioaboa.browser.BrowserTest;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.json.JsonException;
import uk.co.hyttioaboa.messages.json.JsonMessage;

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
        catch (JsonException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }

        BrowserTest browser = new BrowserTest(publishedTestMessage);
        browser.test();
    }
}
