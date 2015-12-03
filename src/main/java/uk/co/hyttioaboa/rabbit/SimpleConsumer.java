package uk.co.hyttioaboa.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * Created by jamesbartlett on 03/12/15.
 */
public class SimpleConsumer {
    String uri;
    String queue;
    QueueingConsumer consumer;
    Channel channel;

    public SimpleConsumer(String consumerUri, String consumerQueue) {
        this.uri = consumerUri;
        this.queue = consumerQueue;
    }

    public SimpleConsumer(String[] args) {
        try {
            String uri = (args.length > 0) ? args[0] : "amqp://localhost";
            String queueName = (args.length > 1) ? args[1] : "SimpleQueue";

            ConnectionFactory connFactory = new ConnectionFactory();
            connFactory.setUri(uri);
            Connection conn = connFactory.newConnection();

            this.channel = conn.createChannel();

            channel.queueDeclare(queueName, false, false, false, null);

            QueueingConsumer consumer = new QueueingConsumer(this.channel);
            channel.basicConsume(queueName, consumer);

            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.out.println("Message: " + new String(delivery.getBody()));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        } catch (Exception ex) {
            System.err.println("Main thread caught exception: " + ex);
            ex.printStackTrace();
            System.exit(1);
        }
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
            System.out.println("Message: " + new String(delivery.getBody()));
            this.channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }

}
