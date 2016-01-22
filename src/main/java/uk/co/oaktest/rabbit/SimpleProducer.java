package uk.co.oaktest.rabbit;

import com.rabbitmq.client.*;
import uk.co.oaktest.constants.Queues;

public class SimpleProducer {
    String uri;
    String exchange;
    String routingKey;

    public SimpleProducer(String[] args) {
        try {
            this.uri = (args.length > 0) ? args[0] : "amqp://localhost";
            String message = (args.length > 1) ? args[1] :
                    "the time is " + new java.util.Date().toString();
            this.exchange = (args.length > 2) ? args[2] : "";
            this.routingKey = (args.length > 3) ? args[3] : Queues.TESTS.getValue();

            System.out.println("I've done summat");

            ConnectionFactory cfconn = new ConnectionFactory();
            cfconn.setUri(this.uri);
            Connection conn = cfconn.newConnection();

            Channel ch = conn.createChannel();

            ch.queueDeclare(routingKey, false, false, false, null);

            ch.basicPublish(exchange, routingKey, null, message.getBytes());
            ch.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("Main thread caught exception: " + e);
            System.out.println("I've done summat wrong");
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public SimpleProducer(RabbitMessage rabbitMessage) {
        this.uri = rabbitMessage.getUri();
        this.exchange = rabbitMessage.getExchange();
        this.routingKey = rabbitMessage.getRoutingKey();

        if (rabbitMessage.hasMessage()) {
            connectAndSend(rabbitMessage.getMessage());
        }
    }

    private void connectAndSend(String connectMessage) {
        try {
            ConnectionFactory cfconn = new ConnectionFactory();
            cfconn.setUri(this.uri);
            Connection conn = cfconn.newConnection();

            Channel ch = conn.createChannel();

            ch.queueDeclare(this.routingKey, true, false, false, null);

            ch.basicPublish(this.exchange, this.routingKey, null, connectMessage.getBytes());
            ch.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("Main thread caught exception: " + e);
            System.out.println("I've done summat wrong");
            e.printStackTrace();
            throw new Error(e);
        }

    }
}
