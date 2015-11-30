package uk.co.hyttioaboa.browser;

import org.openqa.selenium.WebDriver;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jamesbartlett on 30/11/15.
 */
public class Page {
    PageInterface message;

    public Page (PageInterface setUpMessage) {
        this.message = setUpMessage;
    }

    public String test() {
        ArrayList<ElementInterface> elements = this.message.getElements();
        for (Iterator<ElementInterface> elementIterator = elements.iterator(); elementIterator.hasNext(); ) {
            ElementInterface elementMessage = elementIterator.next();

            Element element = new Element(elementMessage);
            element.test();
        }

        return "Hello";
    }
}
