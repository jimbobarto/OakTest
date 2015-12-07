package uk.co.hyttioaboa.rabbit;

import uk.co.hyttioaboa.browser.BrowserTest;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.json.JsonMessage;

public class OakRunnable implements Runnable {
    RabbitMessage rabbitMessage;

    public OakRunnable(RabbitMessage rabbitMessage) {
        this.rabbitMessage = rabbitMessage;
    }

    public void run() {
        SimpleConsumer consumer = new SimpleConsumer(this.rabbitMessage);
        String consumedMessage = consumer.consumeMessage();

        MessageInterface publishedTestMessage = new JsonMessage(consumedMessage);
        //System.out.println("Number of pages: " + publishedTestMessage.getPages().size());

        BrowserTest browser = new BrowserTest(publishedTestMessage);
        browser.test();
    }
}
