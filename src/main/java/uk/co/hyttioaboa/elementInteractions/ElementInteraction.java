package uk.co.hyttioaboa.elementInteractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.NoSuchElementException;
import uk.co.hyttioaboa.config.Config;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.results.ResponseNode;

import java.util.HashMap;


/**
 * Created by Pete on 03/12/2015.
 */
public class ElementInteraction {

    public ElementInteraction(){
    }

    public boolean typeValue(WebDriver driver, ElementInterface setUpMessage, ResponseNode elementResponseNode) {
        String identifier = setUpMessage.getIdentifier();
        HashMap idType = new HashMap(Config.findByTypes());
        String identifierType=(String)idType.get(setUpMessage.getIdentifierType());
        String inputValue = setUpMessage.getValue();
        WebElement targetElement = findMyElement(identifierType, identifier ,driver);

        if ( targetElement != null ) {
            targetElement.sendKeys(inputValue);
            elementResponseNode.addMessage(200, "Value '" + inputValue + "' input.");
            return true;
        }
        else {
            elementResponseNode.addMessage(404, "No object with an " + setUpMessage.getIdentifierType()+ " of '" + identifier + "' was found.");
            return false;
        }
    }

        public boolean click(WebDriver driver, ElementInterface setUpMessage, ResponseNode elementResponseNode){

        String identifier = setUpMessage.getIdentifier();
        HashMap idType = new HashMap(Config.findByTypes());
        String identifierType=(String)idType.get(setUpMessage.getIdentifierType());

        Actions tempAction = new Actions(driver);

        WebElement targetElement = findMyElement(identifierType, identifier ,driver);

        if (targetElement != null ) {
            tempAction.click(targetElement);
            tempAction.perform();
            elementResponseNode.addMessage(200, "Clicked the " + setUpMessage.getType() + " identified by " + setUpMessage.getIdentifierType() + " of " + setUpMessage.getIdentifier());
            return true;
        } else {
            //TODO report it didn't work with a failure
            elementResponseNode.addMessage(404, "No object with an " + setUpMessage.getIdentifierType()+ " of '" + identifier + "' was found.");
            return false;
        }
    }




    public WebElement findMyElement(String identifierType, String identifier, WebDriver driver){
        WebElement targetElement = null;

        try{
            if ( identifierType.equals("ID") ) {
                targetElement = driver.findElement(By.id(identifier));
            }
            else if (identifierType.equals("XPATH")) {
                targetElement = driver.findElement(By.xpath(identifier));
            }
            else if (identifierType.equals("CSS")) {
                targetElement = driver.findElement(By.cssSelector(identifier));
            }
            else if (identifierType.equals("CLASS")) {
                targetElement = driver.findElement(By.className(identifier));
            }
            else if (identifierType.equals("LINKTEXT")) {
                targetElement = driver.findElement(By.linkText(identifier));
            }
        } catch ( NoSuchElementException noElement) {
        }

        return targetElement;
    }
}
