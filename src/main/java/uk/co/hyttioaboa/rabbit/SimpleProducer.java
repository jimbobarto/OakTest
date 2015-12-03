package uk.co.hyttioaboa.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SimpleProducer {

    public SimpleProducer(String[] args) {
        System.out.println("I'm in SimpleProducer");
        try {
            String uri = (args.length > 0) ? args[0] : "amqp://localhost";
            String message = (args.length > 1) ? args[1] :
                    "the time is " + new java.util.Date().toString();
            String exchange = (args.length > 2) ? args[2] : "";
            String routingKey = (args.length > 3) ? args[3] : "SimpleQueue";

            System.out.println("I've done summat");

            ConnectionFactory cfconn = new ConnectionFactory();
            cfconn.setUri(uri);
            Connection conn = cfconn.newConnection();

            Channel ch = conn.createChannel();

            if (exchange.equals("")) {
                ch.queueDeclare(routingKey, false, false, false, null);
            }
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
}
