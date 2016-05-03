package uk.co.oaktest.elementInteractions;

import uk.co.oaktest.containers.Container;
import uk.co.oaktest.messages.interfaces.ElementInterface;
import uk.co.oaktest.messages.jackson.ElementMessage;
import uk.co.oaktest.results.ResponseNode;

/**
 * Created by Pete on 12/12/2015.
 */
public class TextBox extends BaseElement{

    public TextBox(ElementMessage message, ResponseNode elementResponseNode, Container elementContainer) {
        super(message, elementResponseNode, elementContainer);
    }
}
