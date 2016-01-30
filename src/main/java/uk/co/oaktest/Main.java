package uk.co.oaktest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import uk.co.oaktest.constants.Queues;
import uk.co.oaktest.rabbit.OakConsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final int NTHREDS = 10;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        try {
            ConnectionFactory cfconn = new ConnectionFactory();
            cfconn.setUri("amqp://localhost");
            Connection conn = cfconn.newConnection();

            Channel ch = conn.createChannel();

            OakConsumer newConsumer = new OakConsumer(executor, ch, Queues.TESTS.getValue());
        }
        catch (Exception e) {
            throw new Error(e);
        }

    }
}