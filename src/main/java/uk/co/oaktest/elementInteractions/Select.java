package uk.co.oaktest.elementInteractions;

import org.openqa.selenium.WebElement;

import uk.co.oaktest.constants.Status;
import uk.co.oaktest.container.Container;
import uk.co.oaktest.messages.interfaces.ElementInterface;
import uk.co.oaktest.messages.jackson.ElementMessage;
import uk.co.oaktest.results.ResponseNode;

public class Select extends BaseElement{

    public Select (ElementMessage message, ResponseNode elementResponseNode, Container elementContainer) {
        super(message, elementResponseNode, elementContainer);
    }

    public boolean select() {

        WebElement targetElement = findMyElement();

        if (targetElement != null ) {
            String selectBy = this.setUpMessage.getSelectBy();
            String inputValue = this.setUpMessage.getValue();

            startTimerInteract();

            org.openqa.selenium.support.ui.Select seleniumSelect = new org.openqa.selenium.support.ui.Select(targetElement);
            //seleniumSelect.deselectAll();
            if (selectBy.equals("label")) {
                seleniumSelect.selectByVisibleText(inputValue);
            }
            else {
                seleniumSelect.selectByValue(inputValue);
            }

            stopTimerInteract();
            responseNode.addMessage(Status.BASIC_SUCCESS.getValue(), "Selected " + inputValue + " on a dropdown identified by " + this.identifierType + " of " + this.identifier);
            return true;
        } else {
            return false;
        }
    }

}
