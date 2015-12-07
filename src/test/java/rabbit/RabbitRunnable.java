package rabbit;

import org.junit.Test;
import uk.co.hyttioaboa.fileContents.GetFileContents;
import uk.co.hyttioaboa.rabbit.OakRunnable;
import uk.co.hyttioaboa.rabbit.RabbitMessage;
import uk.co.hyttioaboa.rabbit.SimpleProducer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RabbitRunnable {

    @Test
    public void messageShouldPublishAndBeConsumedAsRunnable() {
        GetFileContents fileGetter = new GetFileContents();
        String jsonMessage = fileGetter.getTestMessage("src/test/resources/testMessage.json");

        RabbitMessage rabbitProducerMessage = new RabbitMessage("amqp://localhost", "", "SimpleQueue");
        rabbitProducerMessage.setMessage(jsonMessage);

        SimpleProducer producer = new SimpleProducer(rabbitProducerMessage );

        ExecutorService executor = Executors.newFixedThreadPool(10);
        RabbitMessage rabbitConsumerMessage = new RabbitMessage("amqp://localhost", "", "SimpleQueue");
        Runnable worker = new OakRunnable(rabbitConsumerMessage);
        executor.execute(worker);

        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        executor.shutdown();
        // Wait until all threads are finish
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            throw new Error(e);
        }
        System.out.println("Finished all threads");

    }

}
