package uk.co.oaktest.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import uk.co.oaktest.constants.Queues;

public class SimpleConsumer {
    String uri;
    String queue;
    QueueingConsumer consumer;
    Channel channel;
    String consumedMessage;

    public SimpleConsumer(String consumerUri, String consumerQueue) {
        this.uri = consumerUri;
        this.queue = consumerQueue;
    }

    public SimpleConsumer(String[] args) {
        try {
            this.uri = (args.length > 0) ? args[0] : "amqp://localhost";
            this.queue = (args.length > 1) ? args[1] : Queues.TESTS.getValue();

            setUp();

            consumeSingleMessage();
        } catch (Exception ex) {
            System.err.println("Main thread caught exception: " + ex);
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public SimpleConsumer(RabbitMessage rabbitMessage) {
        try {
            this.uri = rabbitMessage.getUri();
            this.queue = rabbitMessage.getRoutingKey();
        } catch (Exception ex) {
            System.err.println("Main thread caught exception: " + ex);
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public String consumeMessage() {
        try {
            setUp();
            consumeSingleMessage();
        } catch (Exception ex) {
            System.err.println("Main thread caught exception: " + ex);
            ex.printStackTrace();
            System.exit(1);
        }

        return this.consumedMessage;
    }

    public void setUp() {
        try {
            ConnectionFactory connFactory = new ConnectionFactory();
            connFactory.setUri(this.uri);
            Connection conn = connFactory.newConnection();

            this.channel = conn.createChannel();

            this.channel.queueDeclare(this.queue, false, false, false, null);

            this.consumer = new QueueingConsumer(this.channel);
            this.channel.basicConsume(this.queue, this.consumer);
        } catch (Exception ex) {
            throw new Error(ex);
        }

    }

    public void consumeSingleMessage() {
        try {
            QueueingConsumer.Delivery delivery = this.consumer.nextDelivery();
            this.consumedMessage = new String(delivery.getBody());
            this.channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }

    public String getConsumedMessage() {
        return this.consumedMessage;
    }
}
