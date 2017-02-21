package uk.co.oaktest.apiTests;

import org.apache.log4j.Logger;
import org.json.JSONException;
import uk.co.oaktest.assertions.Assertion;
import uk.co.oaktest.constants.MessageSource;
import uk.co.oaktest.constants.Queues;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.containers.Container;
import uk.co.oaktest.messages.interfaces.MessageInterface;
import uk.co.oaktest.messages.interfaces.PageInterface;
import uk.co.oaktest.messages.jackson.PageMessage;
import uk.co.oaktest.messages.jackson.TestMessage;
import uk.co.oaktest.rabbit.RabbitMessage;
import uk.co.oaktest.rabbit.SimpleProducer;
import uk.co.oaktest.requests.Request;
import uk.co.oaktest.requests.RequestException;
import uk.co.oaktest.results.ResponseNode;

import java.util.ArrayList;
import java.util.Iterator;

public class ApiTest {
    TestMessage testMessage;
    ResponseNode rootResponseNode;
    Container container;
    MessageSource messageSource;
    final static Logger logger = Logger.getLogger(ApiTest.class);

    public ApiTest(Container setUpContainer) {
        this.container = setUpContainer;

        this.testMessage = this.container.getTestMessage();

        this.rootResponseNode = new ResponseNode(this.testMessage.getName());
    }

    public ApiTest(Container setUpContainer, MessageSource messageSource) {
        this.container = setUpContainer;
        this.messageSource = messageSource;

        this.testMessage = this.container.getTestMessage();

        this.rootResponseNode = new ResponseNode(this.testMessage.getName());
    }

    public Integer test() {
        Request requestContainer = new Request(this.testMessage.getUrl());

        ArrayList<PageMessage> pageMessages = this.testMessage.getPages();

        for (PageMessage pageMessage: pageMessages) {
            ResponseNode requestResponseNode = this.rootResponseNode.createChildNode(pageMessage.getName());

            try {
                ApiRequest request = new ApiRequest(requestContainer, pageMessage, requestResponseNode, this.container);
                request.test();
            }
            catch(Exception exception) {
                requestResponseNode.addMessage(Status.BASIC_ERROR.getValue(), exception.getMessage());
            }

            try {
                ArrayList<Assertion> assertions = pageMessage.getAssertions();
                for (Assertion assertion: assertions) {
                    requestResponseNode.addMessage( assertion.check(this.container.getTranslator()) );
                }
            }
            catch(Exception exception) {
                requestResponseNode.addMessage(Status.BASIC_ERROR.getValue(), exception.getMessage());
            }

            requestResponseNode.end();
        }


        this.rootResponseNode.end();
        publishResults();

        return this.rootResponseNode.getStatus();
    }

    private void publishResults() {
        publishResults("complete");
    }

    private void publishResults(String progress) {
        String reportMessage = "";

        if (this.messageSource == MessageSource.RABBIT) {
            try {
                reportMessage = this.rootResponseNode.createReport().toString(3);
                RabbitMessage rabbitMessage = new RabbitMessage("amqp://localhost", "", Queues.RESULTS.getValue());
                rabbitMessage.setMessage(reportMessage);

                SimpleProducer producer = new SimpleProducer(rabbitMessage);
            } catch (JSONException ex) {
                logger.error("Could not publish results: " + reportMessage);
            }
        }
        else if (this.messageSource == MessageSource.HTTP) {
            try {
                String resultUrl = this.testMessage.getResultUrl();
                reportMessage = this.rootResponseNode.createReport().toString(3);
                Request request = new Request();

                request.put(resultUrl, reportMessage);
            }
            catch (RequestException requestException) {
                logger.error("Error returning results! " + requestException.getMessage());
            }
            catch (JSONException jsonException) {
                logger.error("Error creating report! " + jsonException.getMessage());
            }
        }
    }
}
