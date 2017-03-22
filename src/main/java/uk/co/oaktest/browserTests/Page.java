package uk.co.oaktest.browserTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.containers.Container;
import uk.co.oaktest.messages.interfaces.ElementInterface;
import uk.co.oaktest.messages.interfaces.PageInterface;
import uk.co.oaktest.messages.jackson.ElementMessage;
import uk.co.oaktest.messages.jackson.PageMessage;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.results.TestTimer;
import uk.co.oaktest.utils.UrlConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Page {
    PageMessage message;
    ResponseNode pageNode;
    Container container;
    TestTimer timer;

    public Page (PageMessage pageMessage, ResponseNode pageResponseNode, Container pageContainer) {
        this.message = pageMessage;
        this.pageNode = pageResponseNode;
        this.container = pageContainer;
        this.timer = new TestTimer();

        Map metaData = this.message.getMetaData();
        if (metaData != null) {
            try {
                String json = new ObjectMapper().writeValueAsString(metaData);
                this.pageNode.addMessage(Status.META_DATA.value(), json);
            }
            catch (JsonProcessingException jsonProcessingException) {
                this.pageNode.addMessage(Status.BASIC_ERROR.value(), jsonProcessingException);
            }
        }
    }

    public Integer test() {
        this.timer.startTimer(this.pageNode);

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

        this.timer.stopTimer(this.pageNode);
        return this.pageNode.getStatus();
    }
}
