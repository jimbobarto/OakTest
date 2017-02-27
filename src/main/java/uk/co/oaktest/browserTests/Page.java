package uk.co.oaktest.browserTests;

import org.openqa.selenium.WebDriver;
import uk.co.oaktest.containers.Container;
import uk.co.oaktest.messages.interfaces.ElementInterface;
import uk.co.oaktest.messages.interfaces.PageInterface;
import uk.co.oaktest.messages.jackson.ElementMessage;
import uk.co.oaktest.messages.jackson.PageMessage;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.utils.UrlConstructor;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jamesbartlett on 30/11/15.
 */
public class Page {
    PageMessage message;
    ResponseNode pageNode;
    Container container;

    public Page (PageMessage pageMessage, ResponseNode pageResponseNode, Container pageContainer) {
        this.message = pageMessage;
        this.pageNode = pageResponseNode;
        this.container = pageContainer;
    }

    public Integer test() {
        String pageUri = this.message.getUrl();
        if (pageUri != null) {
            WebDriver driver = this.container.getDriver();
            if (!pageUri.startsWith("http")) {
                UrlConstructor constructor = container.getUrlConstructor();
                pageUri = constructor.buildUrl(pageUri);
            }
            driver.get(pageUri);
        }
        ArrayList<ElementMessage> elements = this.message.getElements();
        for (Iterator<ElementMessage> elementIterator = elements.iterator(); elementIterator.hasNext(); ) {
            ElementMessage elementMessage = elementIterator.next();

            String behaviour = elementMessage.getBehaviour();
            if (!behaviour.equals("ignore")) {
                ResponseNode elementResponseNode = this.pageNode.createChildNode(elementMessage.getName());
                Element element = new Element(elementMessage, elementResponseNode, this.container);
                Integer elementResult = element.test();
                elementResponseNode.end();

                if (elementResult > 499) {
                    break;
                }
            }
        }

        return this.pageNode.getStatus();
    }
}
