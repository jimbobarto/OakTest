package uk.co.oaktest.container;

import org.openqa.selenium.WebDriver;
import uk.co.oaktest.messages.interfaces.MessageInterface;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.variables.Translator;

public class Container {
    MessageInterface message;
    WebDriver driver;
    ResponseNode responseNode;
    Translator translator;

    public Container() {
    }

    public Container(MessageInterface containerMessage) {
        this.message = containerMessage;
    }

    public MessageInterface getMessage() {
        return this.message;
    }

    public MessageInterface setMessage(MessageInterface containerMessage) {
        this.message = containerMessage;
        return this.message;
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public WebDriver setDriver(WebDriver containerDriver) {
        this.driver = containerDriver;
        return this.driver;
    }

    public ResponseNode getResponseNode() {
        return this.responseNode;
    }

    public ResponseNode setResponseNode(ResponseNode containerResponseNode) {
        this.responseNode = containerResponseNode;
        return this.responseNode;
    }

    public Translator getTranslator() {
        return this.translator;
    }

    public Translator setTranslator(Translator containerTranslator) {
        this.translator = containerTranslator;
        return this.translator;
    }
}
