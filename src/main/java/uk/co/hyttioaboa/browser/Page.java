package uk.co.hyttioaboa.browser;

import org.openqa.selenium.WebDriver;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;
import uk.co.hyttioaboa.results.ResponseNode;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jamesbartlett on 30/11/15.
 */
public class Page {
    PageInterface message;
    ResponseNode pageNode;

    public Page (PageInterface setUpMessage, ResponseNode pageResponseNode) {
        this.message = setUpMessage;
        this.pageNode = pageResponseNode;
    }

    public String test(WebDriver driver) {
        ArrayList<ElementInterface> elements = this.message.getElements();
        for (Iterator<ElementInterface> elementIterator = elements.iterator(); elementIterator.hasNext(); ) {
            ElementInterface elementMessage = elementIterator.next();
            ResponseNode elementResponseNode = this.pageNode.createChildNode(elementMessage.getName());

            Element element = new Element(elementMessage, elementResponseNode);
            element.test(driver);

            elementResponseNode.end();
        }

        return "Hello";
    }
}
