package uk.co.hyttioaboa;

import uk.co.hyttioaboa.messages.MessageInterface;
import uk.co.hyttioaboa.messages.json.JsonMessage;
import uk.co.hyttioaboa.messages.xml.XmlMessage;

import java.util.ArrayList;

public class TestDefinition {

    private String name;
    private ArrayList pages;
    private ArrayList elements;
    private String type;

    public TestDefinition(String testDefinition) {
        MessageInterface message = new JsonMessage(testDefinition);
        if (message.isValid()) {
            System.out.println("Valid JSON");
            type = "JSON";
        }
        else {
            message = new XmlMessage(testDefinition);
            if (message.isValid()) {
                System.out.println("Valid XML");
                type = "XML";
            }
            else {
                System.out.println("Invalid everything :(");
            }
        }
    }

    public String getTestType() {
        if(type != null && !type.isEmpty()) {
            return type;
        }

        return "";
    }
}

