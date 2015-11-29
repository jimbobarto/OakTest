package uk.co.hyttioaboa.browser;

import org.openqa.selenium.firefox.FirefoxDriver;
import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import org.openqa.selenium.WebDriver;

/**
 * Created by jamesbartlett on 29/11/15.
 */
public class BrowserTest {
    MessageInterface message;

    public BrowserTest(MessageInterface message) {
        WebDriver driver = new FirefoxDriver();
        driver.get(message.getUrl());
    }
}
