package uk.co.hyttioaboa.elementInteractions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


/**
 * Created by Pete on 03/12/2015.
 */
public class ElementInteraction {

    public ElementInteraction(){

    }

    public static boolean click(WebDriver driver, String identifier){
        Actions tempAction = new Actions(driver);
        //TODO Put all this in properly
        WebElement targetElement = driver.findElement(By.id(identifier));

        tempAction.click(targetElement);
        tempAction.perform();

        return true;
    }

}
