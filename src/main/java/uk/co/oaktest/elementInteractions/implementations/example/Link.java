package uk.co.oaktest.elementInteractions.implementations.example;


import org.openqa.selenium.WebElement;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.container.Container;
import uk.co.oaktest.elementInteractions.BaseElement;
import uk.co.oaktest.messages.interfaces.ElementInterface;
import uk.co.oaktest.results.ResponseNode;

/**
 * Created by Pete on 03/12/2015.
 */
public class Link extends BaseElement {

    public Link(ElementInterface message, ResponseNode elementResponseNode, Container elementContainer) {
        super(message, elementResponseNode, elementContainer);
    }

    public boolean click() {

        WebElement targetElement = findMyElement();

        if (targetElement != null) {
            startTimerInteract();
            targetElement.click();
            stopTimerInteract();
            this.responseNode.addMessage(Status.TEXT_CHECK_WARNING.getValue(), "Clicked the " + this.type + " identified by " + this.identifierType + " of " + this.identifier);
            return true;
        } else {
            return false;
        }
    }
}
