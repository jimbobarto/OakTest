package uk.co.oaktest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import uk.co.oaktest.api.*;
import uk.co.oaktest.constants.Queues;
import uk.co.oaktest.rabbit.OakConsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application<TestConfiguration> {

    private static final int NTHREDS = 10;

    public static void main(String[] args) throws Exception {
        new Main().run(args);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        try {
            ConnectionFactory cfconn = new ConnectionFactory();
            cfconn.setUri("amqp://localhost");
            Connection conn = cfconn.newConnection();

            Channel ch = conn.createChannel();

            OakConsumer newConsumer = new OakConsumer(executor, ch, Queues.TESTS.getValue());
        }
        catch (Exception e) {
            throw new Error(e);
        }

    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<TestConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(TestConfiguration configuration,
                    Environment environment) {
        final TestResource testResource = new TestResource(
        );
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());

        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(testResource);
    }

}
