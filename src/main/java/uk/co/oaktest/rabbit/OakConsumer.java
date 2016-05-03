package uk.co.oaktest.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class OakConsumer extends DefaultConsumer {
    Channel channel;
    String queue;
    ExecutorService executorService;

    public OakConsumer(ExecutorService threadExecutor, Channel newChannel, String newQueue) {
        super(newChannel);
        channel = newChannel;
        queue = newQueue;

        try {
            channel.queueDeclare(queue, true, false, false, null);
            channel.basicConsume(queue, false, this);
        }
        catch (IOException e) {
            throw new Error(e);
        }

        executorService = threadExecutor;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        long deliveryTag = envelope.getDeliveryTag();

        Runnable task = new OakRunnable(body);
        executorService.submit(task);

        channel.basicAck(deliveryTag, false);
    }
}
