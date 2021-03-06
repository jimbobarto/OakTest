package uk.co.oaktest.browserTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.openqa.selenium.WebDriverException;
import uk.co.oaktest.constants.MessageSource;
import uk.co.oaktest.constants.Queues;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.containers.Container;
import org.openqa.selenium.WebDriver;
import uk.co.oaktest.drivers.BrowserSpecificDriver;
import uk.co.oaktest.messages.jackson.TestMessage;
import uk.co.oaktest.messages.jackson.PageMessage;
import uk.co.oaktest.rabbit.RabbitMessage;
import uk.co.oaktest.rabbit.SimpleProducer;
import uk.co.oaktest.requests.Request;
import uk.co.oaktest.requests.RequestException;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.results.TestTimer;
import uk.co.oaktest.utils.UrlConstructor;
import uk.co.oaktest.variables.Translator;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BrowserTest {

    TestMessage testMessage;
    ResponseNode rootResponseNode;
    Translator translator;
    UrlConstructor urlConstructor;
    Container container;
    String rootUrl;
    MessageSource messageSource;
    TestTimer timer;
    final static Logger logger = Logger.getLogger(BrowserTest.class);

    public BrowserTest(Container setUpContainer) {
        setUpBrowserTest(setUpContainer);
        //TODO: ability to set a status that sets when a test stops (e.g. if a status is over 399 then the test stops), and not just rely on a default
    }

    public BrowserTest(Container setUpContainer, MessageSource source) {
        setUpBrowserTest(setUpContainer);
        this.messageSource = source;

        if (this.messageSource == MessageSource.HTTP && this.testMessage.getResultUrl().equals("")) {
            this.rootResponseNode.addMessage(Status.BASIC_ERROR.getValue(), "Message was received from an HTTP request but no return URL for results was specified", "");
        }
    }

    private void setUpBrowserTest(Container setUpContainer) {
        this.container = setUpContainer;
        this.testMessage = setUpContainer.getTestMessage();
        this.rootUrl = testMessage.getUrl();
        this.timer = new TestTimer();

        this.rootResponseNode = new ResponseNode(testMessage.getName());
        this.container.setResponseNode(this.rootResponseNode);

        this.translator = setUpContainer.getTranslator();
        this.urlConstructor = new UrlConstructor(this.rootUrl);
        this.container.setUrlConstructor(this.urlConstructor);

        Map metaData = this.testMessage.getMetaData();
        if (metaData != null) {
            try {
                String json = new ObjectMapper().writeValueAsString(metaData);
                this.rootResponseNode.addMessage(Status.META_DATA.value(), json);
            }
            catch (JsonProcessingException jsonProcessingException) {
                this.rootResponseNode.addMessage(Status.BASIC_ERROR.value(), jsonProcessingException);
            }
        }
    }

    public ResponseNode getResponseNode() {
        return this.rootResponseNode;
    }

    public Integer test() {
        this.timer.startTimer(this.rootResponseNode);

        String browser = this.testMessage.getBrowser();
        BrowserSpecificDriver browserDriver = new BrowserSpecificDriver(browser);
        WebDriver driver = browserDriver.getDriver();
        this.container.setDriver(driver);

        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        try {
            driver.get(this.rootUrl);
            this.rootResponseNode.addMessage(Status.ACTUAL_URL.getValue(), "URL: " + this.rootUrl);
        }
        catch (org.openqa.selenium.TimeoutException timeoutException) {
            this.rootResponseNode.addMessage(Status.BASIC_ERROR.getValue(), "Timed out waiting for page to load");
            finish(driver);
            return this.rootResponseNode.getStatus();
        }
        catch (WebDriverException webDriverException) {
            String message = webDriverException.getMessage();
            if (message.contains("is not well-formed")) {
                this.rootResponseNode.addMessage(Status.BASIC_ERROR.getValue(), "Invalid URL");
                finish(driver);
                return this.rootResponseNode.getStatus();
            }
            else {
                this.rootResponseNode.addMessage(Status.BASIC_ERROR.getValue(), webDriverException.getMessage(), webDriverException.toString());
                finish(driver);
                return this.rootResponseNode.getStatus();
            }
        }
        catch (Exception exception) {
            this.rootResponseNode.addMessage(Status.BASIC_ERROR.getValue(), exception.getMessage(), exception.toString());
            finish(driver);
            return this.rootResponseNode.getStatus();
        }

        ArrayList<PageMessage> pageMessages = this.testMessage.getPages();

        try {
            for (PageMessage pageMessage: pageMessages) {
                ResponseNode pageResponseNode = this.rootResponseNode.createChildNode(pageMessage.getName());

                Page page = new Page(pageMessage, pageResponseNode, this.container);

                Integer pageResult = page.test();

                pageResponseNode.end();

                //TODO: remove this hard-coded default!!!
                if (pageResult > 499) {
                    break;
                }
            }
        }
        catch (Exception e) {
            this.rootResponseNode.addMessage(Status.BASIC_ERROR.getValue(), e.getMessage(), e.toString());
        }
        finally {
            //TODO: we must always make sure Rabbit is up!
            this.timer.stopTimer(this.rootResponseNode);
            finish(driver);
        }

        return this.rootResponseNode.getStatus();
    }

    private void finish(WebDriver driver) {
        this.rootResponseNode.end();
        publishResults();
        driver.close();
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
