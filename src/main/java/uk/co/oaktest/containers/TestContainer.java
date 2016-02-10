package uk.co.oaktest.containers;

import org.openqa.selenium.WebDriver;
import uk.co.oaktest.messages.interfaces.MessageInterface;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.utils.UrlConstructor;
import uk.co.oaktest.variables.Translator;

public class TestContainer {
    MessageInterface message;
    WebDriver driver;
    ResponseNode responseNode;
    Translator translator;
    UrlConstructor urlConstructor;
    String implementation;

    public TestContainer() {
    }

    public TestContainer(MessageInterface containerMessage) {
        this.message = containerMessage;
        if (this.message.getImplementation() != null) {
            setImplementation(this.message.getImplementation());
        }
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

    public UrlConstructor getUrlConstructor() {
        return this.urlConstructor;
    }

    public UrlConstructor setUrlConstructor(UrlConstructor containerConstructor) {
        this.urlConstructor = containerConstructor;
        return this.urlConstructor;
    }

    public String getImplementation() {
        return this.implementation;
    }

    public String setImplementation(String newImplementation) {
        this.implementation = newImplementation;
        return this.implementation;
    }
}
