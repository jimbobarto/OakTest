package uk.co.hyttioaboa.browser;

import org.openqa.selenium.firefox.FirefoxDriver;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import org.openqa.selenium.WebDriver;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;

import java.util.ArrayList;
import java.util.Iterator;

public class BrowserTest {
    MessageInterface message;

    public BrowserTest(MessageInterface setUpMessage) {
        this.message = setUpMessage;

        WebDriver driver = new FirefoxDriver();
        driver.get(message.getUrl());
    }

    public String test() {
        ArrayList<PageInterface> pages = this.message.getPages();

        for (Iterator<PageInterface> pageIterator = pages.iterator(); pageIterator.hasNext(); ) {
            PageInterface pageMessage = pageIterator.next();
            Page page = new Page(pageMessage);

            page.test();

        }

        return "Hello";
    }
}
