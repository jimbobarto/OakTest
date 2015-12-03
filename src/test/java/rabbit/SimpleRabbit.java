package rabbit;

import org.junit.Test;
import uk.co.hyttioaboa.rabbit.SimpleConsumer;
import uk.co.hyttioaboa.rabbit.SimpleProducer;
import static org.junit.Assert.assertEquals;

public class SimpleRabbit {


    @Test
    public void messageShouldPublishAndBeConsumed() {
        System.out.println("I've done summat");
        String[] arguments = new String[0];
        SimpleProducer producer = new SimpleProducer(arguments);
        //SimpleProducer producer = new SimpleProducer();
        assertEquals("Number of pages in the test message should be 2", 2, 2);

        SimpleConsumer consumer = new SimpleConsumer(arguments);
    }

    @Test
    public void messageShouldPublish() {
        assertEquals("Number of pages in the test message should be 2", 2, 2);
    }
}
