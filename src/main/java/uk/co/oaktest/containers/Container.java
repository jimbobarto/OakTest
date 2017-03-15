package uk.co.oaktest.containers;

import org.openqa.selenium.WebDriver;
import uk.co.oaktest.messages.jackson.TestMessage;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.utils.UrlConstructor;
import uk.co.oaktest.variables.Translator;

/*
Horrible class that acts as a container (natch) for lots of things a test will need
*/

public class Container {
    TestMessage testMessage;
    WebDriver driver;
    ResponseNode responseNode;
    Translator translator;
    UrlConstructor urlConstructor;
    String implementation;
    Integer resultId;

    public Container() {
        this.translator = new Translator();
    }

    public Container(TestMessage containerTestMessage) {
        this.testMessage = containerTestMessage;
        if (this.testMessage.getImplementation() != null) {
            setImplementation(this.testMessage.getImplementation());
        }
        if (this.testMessage.getResultId() != null) {
            setResultId(this.testMessage.getResultId());
        }

        this.translator = new Translator();
    }

    public TestMessage getTestMessage() {
        return this.testMessage;
    }

    public TestMessage setMessage(TestMessage containerTestMessage) {
        this.testMessage = containerTestMessage;
        return this.testMessage;
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

    public Integer getResultId() {
        return this.resultId;
    }

    public Integer setResultId(Integer resultId) {
        this.resultId = resultId;
        return this.resultId;
    }
}
