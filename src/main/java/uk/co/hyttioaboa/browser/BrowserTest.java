package uk.co.hyttioaboa.browser;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.firefox.FirefoxDriver;
import uk.co.hyttioaboa.constants.Queues;
import uk.co.hyttioaboa.constants.Status;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import org.openqa.selenium.WebDriver;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;
import uk.co.hyttioaboa.rabbit.RabbitMessage;
import uk.co.hyttioaboa.rabbit.SimpleProducer;
import uk.co.hyttioaboa.results.ResponseNode;

import java.util.ArrayList;
import java.util.Iterator;

public class BrowserTest {
    MessageInterface message;
    ResponseNode rootResponseNode;
    final static Logger logger = Logger.getLogger(BrowserTest.class);

    public BrowserTest(MessageInterface setUpMessage) {
        this.message = setUpMessage;

        this.rootResponseNode = new ResponseNode(message.getName());
    }

    public ResponseNode getResponseNode() {
        return this.rootResponseNode;
    }

    public Integer test() {
        WebDriver driver = new FirefoxDriver();
        driver.get(message.getUrl());

        ArrayList<PageInterface> pages = this.message.getPages();

        try {
            for (Iterator<PageInterface> pageIterator = pages.iterator(); pageIterator.hasNext(); ) {
                PageInterface pageMessage = pageIterator.next();
                ResponseNode pageResponseNode = this.rootResponseNode.createChildNode(pageMessage.getName());

                Page page = new Page(pageMessage, pageResponseNode);

                page.test(driver);

                pageResponseNode.end();
            }
            driver.close();
        }
        catch (Exception e) {
            this.rootResponseNode.addMessage(Status.BASIC_ERROR.getValue(), e.getMessage(), e.toString());
        }
        finally {
            this.rootResponseNode.end();
            publishResults();
        }

        return this.rootResponseNode.getStatus();
    }

    private void publishResults() {
        String reportMessage = "";
        try {
            reportMessage = this.rootResponseNode.createReport().toString(3);
            RabbitMessage rabbitMessage = new RabbitMessage("amqp://localhost", "", Queues.RESULTS.getValue());
            rabbitMessage.setMessage(reportMessage);

            SimpleProducer producer = new SimpleProducer(rabbitMessage);
        }
        catch (JSONException ex) {
            logger.error("Could not publish results: " + reportMessage);
        }
    }
}
