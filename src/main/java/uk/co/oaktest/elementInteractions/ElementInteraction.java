package uk.co.oaktest.elementInteractions;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.oaktest.config.Config;
import uk.co.oaktest.constants.Selenium;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.containers.Container;
import uk.co.oaktest.messages.jackson.ElementMessage;
import uk.co.oaktest.results.ResponseNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.TimeoutException;
import uk.co.oaktest.results.TestTimer;
import uk.co.oaktest.variables.Translator;

/**
 * Created by Pete on 03/12/2015.
 */
public class ElementInteraction {

    public WebDriver driver;
    public ElementMessage setUpMessage;
    public ResponseNode responseNode;
    public Container container;
    public Translator translator;
    public String identifierType;
    public String identifier;
    public String type;
    public Integer timeoutInSeconds;
    public TestTimer timer;


    public ElementInteraction(ElementMessage message, ResponseNode elementResponseNode, Container elementContainer) {
        this.setUpMessage = message;
        this.responseNode = elementResponseNode;
        this.container = elementContainer;

        this.driver = this.container.getDriver();
        this.translator = this.container.getTranslator();

        this.identifierType = getIdentifierType();
        this.type = setUpMessage.getType();
        this.timeoutInSeconds = setTimeout();

        this.identifier = this.translator.translate(setUpMessage.getIdentifier());
        responseNode.addMessage(Status.ACTUAL_IDENTIFIER.getValue(), this.identifier);

        //TODO: initialize the Timer at a higher level...
        this.timer = new TestTimer();
    }

    public Integer setTimeout() {
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

        WebElement targetElement = findElement();

        if ( targetElement != null ) {
            startTimerInteract();
            targetElement.sendKeys(inputValue);
            stopTimerInteract();
            responseNode.addMessage(Status.BASIC_SUCCESS.getValue(), "Value '" + inputValue + "' input.");
            return true;
        }
        else {
            responseNode.addMessage(Status.OBJECT_NOT_FOUND.getValue(), "No object with an " + this.identifierType + " of '" + this.identifier + "' was found.");
            return false;
        }
    }

    public boolean click() {
        return click(findElement());
    }

    public boolean click(WebElement targetElement) {
        return click(targetElement, this.identifier);
    }

    public boolean click(WebElement targetElement, String overrideIdentifier) {
        if (targetElement != null ) {
            startTimerInteract();
            targetElement.click();
            stopTimerInteract();
            responseNode.addMessage(Status.BASIC_SUCCESS.getValue(), "Clicked the " + this.type + " identified by " + this.identifierType + " of " + overrideIdentifier);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkElementText() {
        String expectedText = this.setUpMessage.getText();

        if (StringUtils.isBlank(expectedText)) {
            responseNode.addMessage(Status.NO_TEXT_CHECK_DATA.getValue(), "No check text supplied!");
            return false;
        }

        expectedText = this.translator.translate(expectedText);
        WebElement targetElement = findElement();

        if(targetElement != null ) {

            String displayedText = targetElement.getText();

            uk.co.oaktest.utils.StringUtils utils = new uk.co.oaktest.utils.StringUtils();
            return utils.compareStrings(expectedText, displayedText, responseNode);
        } else {
            return false;
        }

    }

    public boolean hover() {

        WebElement targetElement = findElement();

        if (targetElement != null ) {
            startTimerInteract();

            Actions action = new Actions(driver);
            action.moveToElement(targetElement).build().perform();

            stopTimerInteract();
            responseNode.addMessage(Status.BASIC_SUCCESS.getValue(), "Hovered over the " + this.type + " identified by " + this.identifierType + " of " + this.identifier);
            return true;
        } else {
            return false;
        }
    }

    public boolean getAttribute() {

        WebElement targetElement = findElement();

        return targetElement != null;
    }

    public String getIdentifierType() {
        HashMap idTypes = new HashMap(Config.findByTypes());
        String identifierType = (String)idTypes.get(setUpMessage.getIdentifierType());

        return identifierType;
    }

    public WebElement findElement() {
        return findElement(getElementBy());
    }

    public WebElement findElement(String identifier) {
        return findElement(getElementBy(identifier));
    }

    public WebElement findElement(By identifier) {
        WebElement targetElement = null;

        startTimerFind();
        if (elementIsVisible(identifier)) {
            try {
                targetElement = this.driver.findElement(identifier);
            }
            catch (NoSuchElementException noElement) {
                responseNode.addMessage(Status.OBJECT_NOT_FOUND.getValue(), "No object with an " + this.identifierType + " of '" + this.identifier + "' was found.");
                return null;
            }
            finally {
                stopTimerFind();
            }
        }
        else {
            stopTimerFind();
        }
        return targetElement;
    }

    public By getElementBy() {
        return getElementBy(this.identifier);
    }

    public By getElementBy(String identifierString) {
        By identifier;
        switch (this.identifierType) {
            case "ID":
                identifier = By.id(identifierString);
                break;
            case "NAME":
                identifier = By.name(identifierString);
                break;
            case "XPATH":
                identifier = By.xpath(identifierString);
                break;
            case "CSS":
                identifier = By.cssSelector(identifierString);
                break;
            case "CLASS":
                identifier = By.className(identifierString);
                break;
            case "LINKTEXT":
                identifier = By.linkText(identifierString);
                break;
            default:
                identifier = By.id(identifierString);
                break;
        }
        return identifier;
    }

    public Boolean elementIsVisible(By identifier) {
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

    public Boolean elementIsClickable(By identifier) {
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

    public void startTimer(Integer status) {
        this.timer.startTimer();
        responseNode.addMessage(status, this.timer.getFormattedStartTime());
    }

    public void startTimerFind() {
        this.startTimer(Status.TIMER_STARTED_FIND.getValue());
    }

    public void startTimerInteract() {
        this.startTimer(Status.TIMER_STARTED_INTERACT.getValue());
    }

    public void stopTimer(Integer timerStatus, Integer elapsedStatus) {
        this.timer.stopTimer();
        responseNode.addMessage(timerStatus, this.timer.getFormattedFinishTime());

        long elapsedTime = this.timer.getElapsedTime();
        responseNode.addMessage(elapsedStatus, String.valueOf(elapsedTime));
    }

    public void stopTimerFind() {
        this.stopTimer(Status.TIMER_FINISHED_FIND.getValue(), Status.ELAPSED_TIME_FIND.getValue());
    }

    public void stopTimerInteract() {
        this.stopTimer(Status.TIMER_FINISHED_INTERACT.getValue(), Status.ELAPSED_TIME_INTERACT.getValue());
    }
}
