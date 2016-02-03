package uk.co.oaktest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.log4j.Logger;
import uk.co.oaktest.constants.Queues;
import uk.co.oaktest.rabbit.OakConsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final int NTHREDS = 5;
    final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        //TODO: feed in max threads via other methods - environment variable?
        ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
        try {
            ConnectionFactory cfconn = new ConnectionFactory();
            cfconn.setUri("amqp://localhost");
            Connection conn = cfconn.newConnection();

            Channel ch = conn.createChannel();

            OakConsumer newConsumer = new OakConsumer(executor, ch, Queues.TESTS.getValue());
        }
        catch (Exception e) {
            logger.error("Fatal error: " + e.getMessage());
        }

    }
}
