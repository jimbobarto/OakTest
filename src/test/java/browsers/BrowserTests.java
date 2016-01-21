package browsers;

import org.json.JSONException;
import org.json.JSONObject;
import uk.co.hyttioaboa.constants.Queues;
import uk.co.hyttioaboa.fileContents.GetFileContents;
import org.junit.Test;
import uk.co.hyttioaboa.browser.BrowserTest;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.MessageException;
import uk.co.hyttioaboa.messages.json.JsonMessage;
import uk.co.hyttioaboa.messages.xml.XmlMessage;
import uk.co.hyttioaboa.rabbit.RabbitMessage;
import uk.co.hyttioaboa.rabbit.SimpleProducer;
import uk.co.hyttioaboa.results.ResponseNode;

import static org.junit.Assert.assertEquals;

public class BrowserTests {
    @Test
    public void browserTestFromJson() {
        GetFileContents fileGetter = new GetFileContents();
        String jsonDefinition = fileGetter.getTestMessage("src/test/resources/testMessage.json");

        MessageInterface testMessage;
        try {
            testMessage = new JsonMessage(jsonDefinition);
        }
        catch (MessageException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }

        BrowserTest browser = new BrowserTest(testMessage);
        browser.test();
    }

    @Test
    public void browserTestFromXml() {
        GetFileContents fileGetter = new GetFileContents();
        String xmlMessage = fileGetter.getTestMessage("src/test/resources/testMessage.xml");

        MessageInterface testMessage = new XmlMessage(xmlMessage);

        BrowserTest browser = new BrowserTest(testMessage);
        browser.test();
    }

    @Test
    public void linkIDShouldClickLink() {
        GetFileContents fileGetter = new GetFileContents();
        String jsonDefinition = fileGetter.getTestMessage("src/test/resources/testMessage2.json");

        MessageInterface testMessage;
        try {
            testMessage = new JsonMessage(jsonDefinition);
        }
        catch (MessageException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }

        BrowserTest browser = new BrowserTest(testMessage);
        browser.test();


        ResponseNode node = browser.getResponseNode();
        //ResponseNode targetNode = node.getNodeByPath("stuff/stuff/stuff");

        assertEquals(200,node.getStatus(),0);


        ResponseNode reporterNode = browser.getResponseNode();
        String reportMessage = "";
        try {
            JSONObject report = reporterNode.createReport();
            System.out.println(report.toString(3));
            reportMessage = report.toString(3);
        }
        catch (JSONException ex) {
            //TODO something here with the exception
            System.out.println("Badness");
        }

        RabbitMessage rabbitMessage = new RabbitMessage("amqp://localhost", "", Queues.RESULTS.getValue());
        rabbitMessage.setMessage(reportMessage);
        SimpleProducer producer = new SimpleProducer(rabbitMessage);
    }

    @Test
    public void enterSearchValue() {
        GetFileContents fileGetter = new GetFileContents();
        String jsonDefinition = fileGetter.getTestMessage("src/test/resources/testMessage3.json");

        MessageInterface testMessage;
        try {
            testMessage = new JsonMessage(jsonDefinition);
        }
        catch (MessageException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }

        BrowserTest browser = new BrowserTest(testMessage);
        browser.test();
        ResponseNode node = browser.getResponseNode();

        assertEquals(200,node.getNodeByPath("Example test/Do Stuff/Amazon search field").getStatus(),0);
        assertEquals(200,node.getNodeByPath("Example test[0]/Do Stuff[0]/Amazon search field[0]").getStatus(),0);
    }


    @Test
    public void checkTextSimple() {
        GetFileContents fileGetter = new GetFileContents();
        String jsonDefinition = fileGetter.getTestMessage("src/test/resources/checkTextSimple.json");

        MessageInterface testMessage;
        try {
            testMessage = new JsonMessage(jsonDefinition);
        }
        catch (MessageException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }

        BrowserTest browser = new BrowserTest(testMessage);
        browser.test();
        ResponseNode node = browser.getResponseNode();

        assertEquals(201,node.getNodeByPath("Check text example/Get the objects text[0]/header object").getStatus(),0);
        assertEquals(300,node.getNodeByPath("Check text example/Get the objects text[0]/header object[1]").getStatus(),0);
        assertEquals(401,node.getNodeByPath("Check text example/Get the objects text[0]/header object[2]").getStatus(),0);
        assertEquals(402,node.getNodeByPath("Check text example/Get the objects text[0]/header object[3]").getStatus(),0);
    }
}
