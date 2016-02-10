package uk.co.oaktest.elementInteractions;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.oaktest.config.Config;
import uk.co.oaktest.constants.Selenium;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.containers.TestContainer;
import uk.co.oaktest.messages.interfaces.ElementInterface;
import uk.co.oaktest.results.ResponseNode;

import java.util.HashMap;
import org.openqa.selenium.TimeoutException;
import uk.co.oaktest.results.TestTimer;
import uk.co.oaktest.variables.Translator;

/**
 * Created by Pete on 03/12/2015.
 */
public class ElementInteraction {

    public WebDriver driver;
    public ElementInterface setUpMessage;
    public ResponseNode responseNode;
    public TestContainer container;
    public Translator translator;
    public String identifierType;
    public String identifier;
    public String type;
    public Integer timeoutInSeconds;
    public By byIdentifier;
    public TestTimer timer;


    public ElementInteraction(ElementInterface message, ResponseNode elementResponseNode, TestContainer elementContainer) {
        this.setUpMessage = message;
        this.responseNode = elementResponseNode;
        this.container = elementContainer;

        this.driver = this.container.getDriver();
        this.translator = this.container.getTranslator();

        this.identifierType = getIdentifierType();
        this.type = setUpMessage.getType();
        this.timeoutInSeconds = setTimeout();

        this.identifier = this.translator.translate(setUpMessage.getIdentifier());

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

        WebElement targetElement = findMyElement();

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

        WebElement targetElement = findMyElement();

        if (targetElement != null ) {
            startTimerInteract();
            targetElement.click();
            stopTimerInteract();
            responseNode.addMessage(Status.BASIC_SUCCESS.getValue(), "Clicked the " + this.type + " identified by " + this.identifierType + " of " + this.identifier);
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

        WebElement targetElement = findMyElement();

        if(targetElement != null ) {

            String displayedText = targetElement.getText();


            if (expectedText.equals(displayedText)) {
                responseNode.addMessage(Status.CHECK_SUCCESS.getValue(), "Text as expected.");
                return true;
            } else if(expectedText.toLowerCase().equals(displayedText.toLowerCase())){
                responseNode.addMessage(Status.TEXT_CHECK_WARNING.getValue(), "Text matched but only when case insensitive.");
                return true;
            } else {
                responseNode.addMessage(Status.TEXT_CHECK_FAILURE.getValue(), "Text expected - " + expectedText + " but found text was - " + displayedText);
                return false;
            }
        } else {
            return false;
        }

    }


    public boolean getAttribute() {

        WebElement targetElement = findMyElement();

        if(targetElement != null ) {

            return true;
        } else {
            return false;
        }
    }

    public String getIdentifierType() {
        HashMap idTypes = new HashMap(Config.findByTypes());
        String identifierType = (String)idTypes.get(setUpMessage.getIdentifierType());

        return identifierType;
    }

    public WebElement findMyElement() {
        WebElement targetElement = null;

        By identifier = getElementBy();

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
