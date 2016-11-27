package uk.co.oaktest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.log4j.Logger;
import uk.co.oaktest.api.*;
import uk.co.oaktest.constants.Queues;
import uk.co.oaktest.rabbit.OakConsumer;

import java.util.concurrent.*;

public class Main extends Application<TestConfiguration> {

    final static Logger logger = Logger.getLogger(Main.class);

    private static final int NTHREDS = 2;
    private static final int MAXTHREDS = 4;
    private static ThreadPoolExecutor executor;

    public static void main(String[] args) throws Exception {
        executor = new ThreadPoolExecutor(NTHREDS, MAXTHREDS, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        new Main().run(args);
        //ExecutorService executor = Executors.newFixedThreadPool(5);
//        try {
//            ConnectionFactory cfconn = new ConnectionFactory();
//            cfconn.setUri("amqp://localhost");
//            Connection conn = cfconn.newConnection();
//
//            Channel ch = conn.createChannel();
//
//            OakConsumer newConsumer = new OakConsumer(executor, ch, Queues.TESTS.getValue());
//        }
//        catch (Exception e) {
//            logger.error("Fatal error: " + e.getMessage());
//        }

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
        final RunTest runTest = new RunTest(executor);
        final ThreadStatus status = new ThreadStatus(executor);
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());

        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(runTest);
        environment.jersey().register(status);
    }

}
