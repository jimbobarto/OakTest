package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Assert;
import org.junit.Test;
import uk.co.oaktest.constants.Queues;
import uk.co.oaktest.fileContents.GetFileContents;
import uk.co.oaktest.rabbit.OakConsumer;
import uk.co.oaktest.rabbit.RabbitMessage;
import uk.co.oaktest.rabbit.SimpleProducer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RabbitRunnable {

    @Test
    public void messageShouldPublishAndBeConsumedAsRunnable() {
        for (int i = 0; i < 3; i++) {
            GetFileContents fileGetter = new GetFileContents();
            String jsonMessage = fileGetter.getTestMessage("src/test/resources/testMessage.json");

            RabbitMessage rabbitProducerMessage = new RabbitMessage("amqp://localhost", "", Queues.TESTS.getValue());
            rabbitProducerMessage.setMessage(jsonMessage);

            SimpleProducer producer = new SimpleProducer(rabbitProducerMessage);
        }

        ExecutorService executor = Executors.newFixedThreadPool(5);
        try {
            ConnectionFactory cfconn = new ConnectionFactory();
            cfconn.setUri("amqp://localhost");
            Connection conn = cfconn.newConnection();

            Channel ch = conn.createChannel();

            OakConsumer newConsumer = new OakConsumer(executor, ch, Queues.TESTS.getValue());
        }
        catch (Exception e) {
            Assert.fail("Consumer error: " + e.getMessage());
            return;
        }

        try {
            Thread.sleep(10000);
        }
        catch (Exception e) {
            Assert.fail("Sleep error: " + e.getMessage());
            return;
        }

        executor.shutdown();
        // Wait until all threads are finished
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            Assert.fail("Termination error: " + e.getMessage());
            return;
        }

        Assert.assertTrue(true);

    }

}
