package uk.co.oaktest.elementInteractions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.containers.Container;
import uk.co.oaktest.messages.jackson.ElementMessage;
import uk.co.oaktest.messages.jackson.Option;
import uk.co.oaktest.messages.jackson.Variable;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.variables.Translator;

import java.util.ArrayList;
import java.util.List;

public class Radio extends OptionBasedElement {
    public Radio(ElementMessage message, ResponseNode elementResponseNode, Container elementContainer) {
        super(message, elementResponseNode, elementContainer);
    }
}
