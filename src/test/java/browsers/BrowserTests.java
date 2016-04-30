package browsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.oaktest.constants.Queues;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.container.Container;
import uk.co.oaktest.fileContents.GetFileContents;
import org.junit.Test;
import uk.co.oaktest.browserTests.BrowserTest;
import uk.co.oaktest.messages.interfaces.MessageInterface;
import uk.co.oaktest.messages.MessageException;
import uk.co.oaktest.messages.jackson.ElementMessage;
import uk.co.oaktest.messages.jackson.TestMessage;
import uk.co.oaktest.messages.json.JsonMessage;
import uk.co.oaktest.messages.xml.XmlMessage;
import uk.co.oaktest.rabbit.RabbitMessage;
import uk.co.oaktest.rabbit.SimpleProducer;
import uk.co.oaktest.results.ResponseNode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BrowserTests {
    @Test
    public void browserTestFromJson() {
        GetFileContents fileGetter = new GetFileContents();
        TestMessage testMessage = fileGetter.getMessageFromFile("src/test/resources/testMessage.json");

        BrowserTest browser = new BrowserTest(new Container(testMessage));
        browser.test();
    }

//    @Test
//    public void browserTestFromXml() {
//        GetFileContents fileGetter = new GetFileContents();
//        String xmlMessage = fileGetter.getTestMessage("src/test/resources/testMessage.xml");
//
//        MessageInterface testMessage;
//        try {
//            testMessage = new XmlMessage(xmlMessage);
//        }
//        catch (MessageException xmlException) {
//            System.out.println(xmlException.getMessage());
//            return;
//        }
//
//        BrowserTest browser = new BrowserTest(new Container(testMessage));
//        browser.test();
//    }

    @Test
    public void linkIDShouldClickLink() {
        GetFileContents fileGetter = new GetFileContents();
        TestMessage testMessage = fileGetter.getMessageFromFile("src/test/resources/testMessage2.json");

        BrowserTest browser = new BrowserTest(new Container(testMessage));
        browser.test();


        ResponseNode node = browser.getResponseNode();
        //ResponseNode targetNode = node.getNodeByPath("stuff/stuff/stuff");

        assertEquals(200,node.getStatus(),0);


        ResponseNode reporterNode = browser.getResponseNode();
        String reportMessage = "";
        try {
            JSONObject report = reporterNode.createReport();
            reportMessage = report.toString(3);
        }
        catch (JSONException ex) {
            fail("Could not create report: " + ex.getMessage());
        }

        RabbitMessage rabbitMessage = new RabbitMessage("amqp://localhost", "", Queues.RESULTS.getValue());
        rabbitMessage.setMessage(reportMessage);
        SimpleProducer producer = new SimpleProducer(rabbitMessage);
    }

    @Test
    public void enterSearchValue() {
        GetFileContents fileGetter = new GetFileContents();
        TestMessage testMessage = fileGetter.getMessageFromFile("src/test/resources/testMessage3.json");

        BrowserTest browser = new BrowserTest(new Container(testMessage));
        browser.test();
        ResponseNode node = browser.getResponseNode();

        assertEquals(200,node.getNodeByPath("Example test/Do Stuff/Amazon search field").getStatus(),0);
        assertEquals(200, node.getNodeByPath("Example test[0]/Do Stuff[0]/Amazon search field[0]").getStatus(), 0);
    }

    @Test
    public void checkTextSimple() {
        GetFileContents fileGetter = new GetFileContents();
        TestMessage testMessage = fileGetter.getMessageFromFile("src/test/resources/checkTextSimple.json");

        BrowserTest browser = new BrowserTest(new Container(testMessage));
        browser.test();
        ResponseNode node = browser.getResponseNode();

        assertEquals(201, node.getNodeByPath("Check text example/Get the objects text[0]/header object").getStatus(), 0);
        assertEquals(300, node.getNodeByPath("Check text example/Get the objects text[0]/header object[1]").getStatus(), 0);
        assertEquals(401, node.getNodeByPath("Check text example/Get the objects text[0]/header object[2]").getStatus(), 0);
        assertEquals(402, node.getNodeByPath("Check text example/Get the objects text[0]/header object[3]").getStatus(), 0);
    }

    @Test
    public void checkUnknownElement() {
        GetFileContents fileGetter = new GetFileContents();
        TestMessage testMessage = fileGetter.getMessageFromFile("src/test/resources/messageWithUnknownElementType.json");

        BrowserTest browser = new BrowserTest(new Container(testMessage));
        browser.test();
        ResponseNode node = browser.getResponseNode();

        if (!node.getNodeByPath("Example test/1[0]/First link[0]").getStatuses().contains(390)) {
            fail("Unrecognised element status not found");
        }
    }

    @Test
    public void variableIsEvaluated() {
        GetFileContents fileGetter = new GetFileContents();
        TestMessage testMessage = fileGetter.getMessageFromFile("src/test/resources/messageWithVariable.json");

        BrowserTest browser = new BrowserTest(new Container(testMessage));
        browser.test();
        ResponseNode node = browser.getResponseNode();

        assertEquals(200, node.getStatus(), 0);
    }

    //TODO: fix the formatter to match the brave new Jackson world
    /*
    @Test
    public void formatterTestRuns() {
        GetFileContents fileGetter = new GetFileContents();
        TestMessage testMessage = fileGetter.getMessageFromFile("src/test/resources/formatterExample.json");

        BrowserTest browser = new BrowserTest(new Container(testMessage));
        browser.test();
    }

    @Test
    public void formatterTestRunComplex() {
        GetFileContents fileGetter = new GetFileContents();
        String jsonDefinition = fileGetter.getTestMessage("src/test/resources/formatterExampleAviva.json");

        MessageInterface testMessage;
        try {
            testMessage = new JsonMessage(jsonDefinition);
        }
        catch (MessageException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }

        BrowserTest browser = new BrowserTest(new Container(testMessage));
        browser.test();
    }
    */

    @Test
    public void implementationShouldChangeBehaviour() {
        GetFileContents fileGetter = new GetFileContents();
        TestMessage testMessage = fileGetter.getMessageFromFile("src/test/resources/implementationExample.json");

        BrowserTest browser = new BrowserTest(new Container(testMessage));
        browser.test();

        ResponseNode node = browser.getResponseNode();

        assertEquals(Status.TEXT_CHECK_WARNING.getValue(), node.getStatus(), 0);
    }

    @Test
    public void missingImplementationShouldFallBackGracefully() {
        GetFileContents fileGetter = new GetFileContents();
        TestMessage testMessage = fileGetter.getMessageFromFile("src/test/resources/missingImplementationExample.json");

        BrowserTest browser = new BrowserTest(new Container(testMessage));
        browser.test();


        ResponseNode node = browser.getResponseNode();

        assertEquals(Status.UNKNOWN_IMPLEMENTATION.getValue(), node.getStatus(), 0);
    }

}
