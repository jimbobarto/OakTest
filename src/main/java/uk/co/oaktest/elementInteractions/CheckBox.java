package uk.co.oaktest.elementInteractions;

import uk.co.oaktest.containers.Container;
import uk.co.oaktest.messages.jackson.ElementMessage;
import uk.co.oaktest.results.ResponseNode;

public class CheckBox extends OptionBasedElement {
    public CheckBox(ElementMessage message, ResponseNode elementResponseNode, Container elementContainer) {
        super(message, elementResponseNode, elementContainer);
    }
}
