package uk.co.hyttioaboa.elementInteractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.NoSuchElementException;
import uk.co.hyttioaboa.config.Config;
import uk.co.hyttioaboa.constants.Status;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.results.ResponseNode;

import java.util.HashMap;


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

    public ElementInteraction(WebDriver elementDriver, ElementInterface message, ResponseNode elementResponseNode) {
        this.driver = elementDriver;
        this.setUpMessage = message;
        this.responseNode = elementResponseNode;

        this.identifierType = getIdentifierType();
        this.identifier = setUpMessage.getIdentifier();
        this.type = setUpMessage.getType();
    }

    public boolean typeValue() {
        String inputValue = setUpMessage.getValue();

        WebElement targetElement = findMyElement();

        if ( targetElement != null ) {
            targetElement.sendKeys(inputValue);
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
            targetElement.click();
            responseNode.addMessage(Status.BASIC_SUCCESS.getValue(), "Clicked the " + this.type + " identified by " + this.identifierType + " of " + this.identifier);
            return true;
        } else {
            responseNode.addMessage(Status.OBJECT_NOT_FOUND.getValue(), "No object with an " + this.identifierType + " of '" + this.identifier + "' was found.");
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

        //TODO: add in a wait ability
        try{
            if (this.identifierType.equals("ID") ) {
                targetElement = this.driver.findElement(By.id(this.identifier));
            }
            else if (this.identifierType.equals("XPATH")) {
                targetElement = this.driver.findElement(By.xpath(this.identifier));
            }
            else if (this.identifierType.equals("CSS")) {
                targetElement = this.driver.findElement(By.cssSelector(this.identifier));
            }
            else if (this.identifierType.equals("CLASS")) {
                targetElement = this.driver.findElement(By.className(this.identifier));
            }
            else if (this.identifierType.equals("LINKTEXT")) {
                targetElement = this.driver.findElement(By.linkText(this.identifier));
            }
        } catch ( NoSuchElementException noElement) {
        }

        return targetElement;
    }
}
