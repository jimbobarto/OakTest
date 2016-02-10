package uk.co.oaktest.elementInteractions;

import uk.co.oaktest.containers.TestContainer;
import uk.co.oaktest.messages.interfaces.ElementInterface;
import uk.co.oaktest.results.ResponseNode;

/**
 * Created by Pete on 12/12/2015.
 */
public class TextBox extends BaseElement{

    public TextBox(ElementInterface message, ResponseNode elementResponseNode, TestContainer elementContainer) {
        super(message, elementResponseNode, elementContainer);
    }
}
