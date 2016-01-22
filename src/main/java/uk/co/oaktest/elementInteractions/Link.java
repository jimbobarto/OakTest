package uk.co.oaktest.elementInteractions;


import org.openqa.selenium.WebDriver;
import uk.co.oaktest.container.Container;
import uk.co.oaktest.messages.interfaces.ElementInterface;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.variables.Translator;

/**
 * Created by Pete on 03/12/2015.
 */
public class Link extends BaseElement{

    public Link (ElementInterface message, ResponseNode elementResponseNode, Container elementContainer) {
        super(message, elementResponseNode, elementContainer);
    }


}
