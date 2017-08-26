package uk.co.oaktest.elementInteractions;

import org.openqa.selenium.WebElement;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.containers.Container;
import uk.co.oaktest.messages.jackson.ElementMessage;
import uk.co.oaktest.messages.jackson.Option;
import uk.co.oaktest.messages.jackson.Variable;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.variables.Translator;

import java.util.ArrayList;

public class OptionBasedElement extends BaseElement {
    public OptionBasedElement(ElementMessage message, ResponseNode elementResponseNode, Container elementContainer) {
        super(message, elementResponseNode, elementContainer);
    }

    public boolean click() {
        if (this.setUpMessage.hasOptions()) {
            ArrayList<Option> options = this.setUpMessage.getOptions();
            for (Option currentOption : options) {
                if (currentOption.getSelected()) {
                    ArrayList<Variable> optionVariables = translateOptionToVariables(currentOption);
                    Translator optionTranslator = new Translator(optionVariables);

                    String optionIdentifier = optionTranslator.translate(this.identifier);

                    WebElement targetOption = findElement(optionIdentifier);
                    if (targetOption != null) {
                        click(targetOption, optionIdentifier);
                    } else {
                        this.responseNode.addMessage(Status.BASIC_ERROR.getValue(), "Could not find the " + this.type + " identified by " + this.identifierType + " of " + optionIdentifier);
                        return false;
                    }
                }
            }
            return true;
        }
        else {
            return super.click();
        }
    }

    private ArrayList<Variable> translateOptionToVariables(Option option) {
        ArrayList<Variable> optionVariables = new ArrayList<>();
        if (!option.getText().equals("")) {
            optionVariables.add(new Variable("option-text", "string", option.getText()));
        }
        if (!option.getValue().equals("")) {
            optionVariables.add(new Variable("option-value", "string", option.getValue()));
        }
        if (!option.getSelected()) {
            optionVariables.add(new Variable("option-selected", "string", option.getSelected().toString()));
        }

        return optionVariables;
    }
}
