package uk.co.hyttioaboa.browser;

import org.openqa.selenium.firefox.FirefoxDriver;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import org.openqa.selenium.WebDriver;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;
import uk.co.hyttioaboa.results.ResponseNode;

import java.util.ArrayList;
import java.util.Iterator;

public class BrowserTest {
    MessageInterface message;
    ResponseNode rootResponseNode;

    public BrowserTest(MessageInterface setUpMessage) {
        this.message = setUpMessage;

        this.rootResponseNode = new ResponseNode(message.getName());
    }

    public ResponseNode getResponseNode() {
        return this.rootResponseNode;
    }

    public String test() {
        WebDriver driver = new FirefoxDriver();
        driver.get(message.getUrl());

        ArrayList<PageInterface> pages = this.message.getPages();

        for (Iterator<PageInterface> pageIterator = pages.iterator(); pageIterator.hasNext(); ) {
            PageInterface pageMessage = pageIterator.next();
            ResponseNode pageResponseNode = this.rootResponseNode.createChildNode(pageMessage.getName());

            Page page = new Page(pageMessage, pageResponseNode);

            page.test(driver);

            pageResponseNode.end();
        }
        driver.close();

        this.rootResponseNode.end();

        System.out.println("Final status: " + this.rootResponseNode.getStatus());

        return "Hello";

    }
}
