package uk.co.hyttioaboa.elementInteractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.NoSuchElementException;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.results.ResponseNode;


/**
 * Created by Pete on 03/12/2015.
 */
public class ElementInteraction {

    public ElementInteraction(){

    }

    //public boolean click(WebDriver driver, String identifier){
    public boolean click(WebDriver driver, ElementInterface setUpMessage, ResponseNode elementResponseNode){

        String identifier = setUpMessage.getIdentifier();
        String identifierType = setUpMessage.getIdentifierType();

        Actions tempAction = new Actions(driver);

        WebElement targetElement = findMyElement(identifierType, identifier ,driver);

        if ( targetElement != null ) {
            tempAction.click(targetElement);
            tempAction.perform();
            elementResponseNode.addMessage(200, "Clicked summat");
        } else {
            //TODO report it didn't work with a failure
            elementResponseNode.addMessage(404, "No object found dude!");
        }

        return true;
    }

    public WebElement findMyElement(String identifierType, String locationValue, WebDriver driver){
        WebElement targetElement = null;

        try{
            if ( identifierType.equals("ID") ) {
                targetElement = driver.findElement(By.id(locationValue));
            }

        }catch ( NoSuchElementException noElement) {
        }

        return targetElement;
    }
}
