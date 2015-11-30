package uk.co.hyttioaboa.browser;

import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jamesbartlett on 30/11/15.
 */
public class Element {
    ElementInterface message;

    public Element (ElementInterface setUpMessage) {
        this.message = setUpMessage;
    }

    public String test() {
        String instruction = this.message.getInstruction();
        String type = this.message.getType();
        String interaction = this.message.getInteraction();

        System.out.println("Instruction: " + instruction);
        System.out.println("Type: " + type);
        System.out.println("Interaction: " + interaction);

        return "Hello";
    }
}
