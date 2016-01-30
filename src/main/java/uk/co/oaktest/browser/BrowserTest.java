package uk.co.oaktest.browser;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.openqa.selenium.firefox.FirefoxDriver;
import uk.co.oaktest.constants.Queues;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.container.Container;
import uk.co.oaktest.messages.interfaces.MessageInterface;
import org.openqa.selenium.WebDriver;
import uk.co.oaktest.messages.interfaces.PageInterface;
import uk.co.oaktest.rabbit.RabbitMessage;
import uk.co.oaktest.rabbit.SimpleProducer;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.utils.UrlConstructor;
import uk.co.oaktest.variables.Translator;

import java.util.ArrayList;
import java.util.Iterator;

public class BrowserTest {
    MessageInterface message;
    ResponseNode rootResponseNode;
    Translator translator;
    UrlConstructor urlConstructor;
    Container container;
    String rootUrl;
    final static Logger logger = Logger.getLogger(BrowserTest.class);

    public BrowserTest(Container setUpContainer) {
        this.container = setUpContainer;
        this.message = this.container.getMessage();
        this.rootUrl = message.getUrl();

        this.rootResponseNode = new ResponseNode(message.getName());
        this.container.setResponseNode(this.rootResponseNode);

        this.translator = new Translator();
        this.container.setTranslator(this.translator);
        if (this.message.hasVariables()) {
            try {
                translator.initialiseVariables(this.message.getVariables());
            }
            catch (Exception e) {
                this.rootResponseNode.addMessage(Status.BASIC_ERROR.getValue(), e.getMessage(), e.toString());
            }
        }

        this.urlConstructor = new UrlConstructor(this.rootUrl);
        this.container.setUrlConstructor(this.urlConstructor);
    }

    public BrowserTest(MessageInterface setUpMessage) {
        this(new Container(setUpMessage));
    }

    public ResponseNode getResponseNode() {
        return this.rootResponseNode;
    }

    public Integer test() {
        WebDriver driver = new FirefoxDriver();
        this.container.setDriver(driver);

        driver.get(this.rootUrl);

        ArrayList<PageInterface> pages = this.message.getPages();

        try {
            for (Iterator<PageInterface> pageIterator = pages.iterator(); pageIterator.hasNext(); ) {
                PageInterface pageMessage = pageIterator.next();
                ResponseNode pageResponseNode = this.rootResponseNode.createChildNode(pageMessage.getName());

                Page page = new Page(pageMessage, pageResponseNode, this.container);

                page.test();

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
