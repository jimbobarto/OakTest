package uk.co.hyttioaboa.elementInteractions;


import org.openqa.selenium.WebDriver;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.results.ResponseNode;

/**
 * Created by Pete on 03/12/2015.
 */
public class Link extends BaseElement{

    public Link (WebDriver elementDriver, ElementInterface message, ResponseNode elementResponseNode) {
        super(elementDriver, message, elementResponseNode);
    }


}
