package uk.co.oaktest.elementInteractions;

import org.openqa.selenium.WebElement;

import uk.co.oaktest.constants.Status;
import uk.co.oaktest.containers.Container;
import uk.co.oaktest.messages.jackson.ElementMessage;
import uk.co.oaktest.results.ResponseNode;

public class Select extends OptionBasedElement {
    public Select (ElementMessage message, ResponseNode elementResponseNode, Container elementContainer) {
        super(message, elementResponseNode, elementContainer);
    }
}
