package rabbit;

import org.junit.Test;
import uk.co.hyttioaboa.fileContents.GetFileContents;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.MessageException;
import uk.co.hyttioaboa.messages.json.JsonMessage;
import uk.co.hyttioaboa.rabbit.RabbitMessage;
import uk.co.hyttioaboa.rabbit.SimpleConsumer;
import uk.co.hyttioaboa.rabbit.SimpleProducer;
import static org.junit.Assert.assertEquals;

public class SimpleRabbit {

    @Test
    public void messageShouldPublishAndBeConsumed() {
        RabbitMessage rabbitMessage = new RabbitMessage("amqp://localhost", "", "SimpleQueue");

        GetFileContents fileGetter = new GetFileContents();
        String jsonMessage = fileGetter.getTestMessage("src/test/resources/testMessage.json");

        MessageInterface testMessage;
        try {
            testMessage = new JsonMessage(jsonMessage);
        }
        catch (MessageException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }
        assertEquals("Number of pages in the test message should be 2", 2, testMessage.getPages().size());

        rabbitMessage.setMessage(jsonMessage);

        SimpleProducer producer = new SimpleProducer(rabbitMessage);

        SimpleConsumer consumer = new SimpleConsumer(rabbitMessage);
        String consumedMessage = consumer.consumeMessage();

        MessageInterface publishedTestMessage;
        try {
            publishedTestMessage = new JsonMessage(consumedMessage);
        }
        catch (MessageException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }
        assertEquals("Number of pages in the test message should be 2", 2, publishedTestMessage.getPages().size());
    }

}
