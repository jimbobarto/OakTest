package uk.co.hyttioaboa.elementInteractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.hyttioaboa.config.Config;
import uk.co.hyttioaboa.constants.Selenium;
import uk.co.hyttioaboa.constants.Status;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.results.ResponseNode;

import java.util.HashMap;
import org.openqa.selenium.TimeoutException;
import uk.co.hyttioaboa.results.TestTimer;

/**
 * Created by Pete on 03/12/2015.
 */
public class ElementInteraction {

    WebDriver driver;
    ElementInterface setUpMessage;
    ResponseNode responseNode;
    String identifierType;
    String identifier;
    String type;
    Integer timeoutInSeconds;
    By byIdentifier;
    TestTimer timer;


    public ElementInteraction(WebDriver elementDriver, ElementInterface message, ResponseNode elementResponseNode) {
        this.driver = elementDriver;
        this.setUpMessage = message;
        this.responseNode = elementResponseNode;

        this.identifierType = getIdentifierType();
        this.identifier = setUpMessage.getIdentifier();
        this.type = setUpMessage.getType();
        this.timeoutInSeconds = setTimeout();

        //TODO: initialize the Timer at a higher level...
        this.timer = new TestTimer();
    }

    private Integer setTimeout() {
        Integer messageTimeout = this.setUpMessage.getTimeout();
        Integer timeout;
        if (messageTimeout != null) {
            timeout = messageTimeout;
        }
        else {
            timeout = Selenium.DEFAULT_TIMEOUT.getValue();
        }

        return timeout;
    }

    public boolean typeValue() {
        String inputValue = setUpMessage.getValue();

        WebElement targetElement = findMyElement();

        if ( targetElement != null ) {
            startTimer();
            targetElement.sendKeys(inputValue);
            stopTimer();
            responseNode.addMessage(Status.BASIC_SUCCESS.getValue(), "Value '" + inputValue + "' input.");
            return true;
        }
        else {
            responseNode.addMessage(Status.OBJECT_NOT_FOUND.getValue(), "No object with an " + this.identifierType + " of '" + this.identifier + "' was found.");
            return false;
        }
    }

    public boolean click() {

        WebElement targetElement = findMyElement();

        if (targetElement != null ) {
            startTimer();
            targetElement.click();
            stopTimer();
            responseNode.addMessage(Status.BASIC_SUCCESS.getValue(), "Clicked the " + this.type + " identified by " + this.identifierType + " of " + this.identifier);
            return true;
        } else {
            return false;
        }
    }

    private String getIdentifierType() {
        HashMap idTypes = new HashMap(Config.findByTypes());
        String identifierType = (String)idTypes.get(setUpMessage.getIdentifierType());

        return identifierType;
    }

    public WebElement findMyElement() {
        WebElement targetElement = null;

        By identifier = getElementBy();
        if (elementIsVisible(identifier)) {
            try {
                targetElement = this.driver.findElement(identifier);
            }
            catch (NoSuchElementException noElement) {
                responseNode.addMessage(Status.OBJECT_NOT_FOUND.getValue(), "No object with an " + this.identifierType + " of '" + this.identifier + "' was found.");
                return null;
            }
        }
        return targetElement;
    }

    public By getElementBy() {
        if (this.byIdentifier == null) {

            By identifier;
            switch (this.identifierType) {
                case "ID":
                    identifier = By.id(this.identifier);
                    break;
                case "NAME":
                    identifier = By.name(this.identifier);
                    break;
                case "XPATH":
                    identifier = By.xpath(this.identifier);
                    break;
                case "CSS":
                    identifier = By.cssSelector(this.identifier);
                    break;
                case "CLASS":
                    identifier = By.className(this.identifier);
                    break;
                case "LINKTEXT":
                    identifier = By.linkText(this.identifier);
                    break;
                default:
                    identifier = By.id(this.identifier);
                    break;
            }
            this.byIdentifier = identifier;

            return identifier;
        }
        else {
            return this.byIdentifier;
        }
    }

    private Boolean elementIsVisible(By identifier) {
        WebDriverWait wait = new WebDriverWait(this.driver, this.timeoutInSeconds);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(identifier));
        }
        catch (TimeoutException timeoutException) {
            responseNode.addMessage(Status.TIMEOUT.getValue(), "No object with an " + this.identifierType + " of '" + this.identifier + "' was found.");
            return false;
        }
        return true;
    }

    private Boolean elementIsClickable(By identifier) {
        WebDriverWait wait = new WebDriverWait(this.driver, this.timeoutInSeconds);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(identifier));
        }
        catch (TimeoutException timeoutException) {
            responseNode.addMessage(Status.TIMEOUT.getValue(), "No object with an " + this.identifierType + " of '" + this.identifier + "' could be clicked.");
            return false;
        }
        return true;
    }

    private void startTimer() {
        this.timer.startTimer();
        responseNode.addMessage(Status.TIMER_STARTED.getValue(), this.timer.getFormattedStartTime());
    }

    private void stopTimer() {
        this.timer.stopTimer();
        responseNode.addMessage(Status.TIMER_FINISHED.getValue(), this.timer.getFormattedFinishTime());

        long elapsedTime = this.timer.getElapsedTime();
        responseNode.addMessage(Status.ELAPSED_TIME.getValue(), String.valueOf(elapsedTime));
    }
}
