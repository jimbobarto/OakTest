package uk.co.oaktest.browser;

import org.openqa.selenium.WebDriver;
import uk.co.oaktest.container.Container;
import uk.co.oaktest.messages.interfaces.ElementInterface;
import uk.co.oaktest.messages.interfaces.PageInterface;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.utils.UrlConstructor;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jamesbartlett on 30/11/15.
 */
public class Page {
    PageInterface message;
    ResponseNode pageNode;
    Container container;

    public Page (PageInterface pageMessage, ResponseNode pageResponseNode, Container pageContainer) {
        this.message = pageMessage;
        this.pageNode = pageResponseNode;
        this.container = pageContainer;
    }

    public String test() {
        String pageUri = this.message.getUri();
        if (pageUri != null) {
            WebDriver driver = this.container.getDriver();
            if (!pageUri.startsWith("http")) {
                UrlConstructor constructor = container.getUrlConstructor();
                pageUri = constructor.buildUrl(pageUri);
            }
            driver.get(pageUri);
        }
        ArrayList<ElementInterface> elements = this.message.getElements();
        for (Iterator<ElementInterface> elementIterator = elements.iterator(); elementIterator.hasNext(); ) {
            ElementInterface elementMessage = elementIterator.next();
            ResponseNode elementResponseNode = this.pageNode.createChildNode(elementMessage.getName());

            Element element = new Element(elementMessage, elementResponseNode, this.container);
            element.test();

            elementResponseNode.end();
        }

        return "Hello";
    }
}
