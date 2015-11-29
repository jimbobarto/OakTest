package browsers;

import org.junit.Test;
import uk.co.hyttioaboa.browser.BrowserTest;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;
import uk.co.hyttioaboa.messages.json.JsonMessage;

import java.util.ArrayList;
import java.util.Iterator;

public class BrowserTests {
    @Test
    public void browserShouldStart() {
        String jsonDefinition = "{\"url\":\"http://www.google.co.uk\", \"pages\":[{\"name\":\"1\", \"elements\":[{\"index\": 1, \"instruction\": \"elementId\", \"type\": \"link\", \"interaction\": \"click\", \"value\": \"\", \"timeout\": 10}, {\"index\": 2, \"instruction\": \"elementName\", \"type\": \"link\", \"interaction\": \"click\", \"value\": \"\", \"timeout\": 15}]},{\"name\":\"2\"}]}";
        MessageInterface testMessage = new JsonMessage(jsonDefinition);

        BrowserTest test = new BrowserTest(testMessage);

        ArrayList<PageInterface> pages = testMessage.getPages();

        for (Iterator<PageInterface> pageIterator = pages.iterator(); pageIterator.hasNext(); ) {
            PageInterface currentPage = pageIterator.next();

            //currentPage
            ArrayList<ElementInterface> elements = currentPage.getElements();
            for (Iterator<ElementInterface> elementIterator = elements.iterator(); elementIterator.hasNext(); ) {
                ElementInterface currentElement = elementIterator.next();

                String instruction = currentElement.getInstruction();
                String type = currentElement.getType();
                String interaction = currentElement.getInteraction();

                System.out.println("Instruction: " + instruction);
                System.out.println("Type: " + type);
                System.out.println("Interaction: " + interaction);
            }
        }
    }
}
