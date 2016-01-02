package uk.co.hyttioaboa;

import uk.co.hyttioaboa.messages.interfaces.MessageInterface;
import uk.co.hyttioaboa.messages.MessageException;
import uk.co.hyttioaboa.messages.json.JsonMessage;
import uk.co.hyttioaboa.messages.xml.XmlMessage;

import java.util.ArrayList;

public class TestDefinition {

    private String name;
    private ArrayList pages;
    private ArrayList elements;
    private String type;

    public TestDefinition(String testDefinition) {
        MessageInterface message;
        try {
            message = new JsonMessage(testDefinition);
        }
        catch (MessageException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }

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

    public TestDefinition(String testDefinition, String testType) {
        if (testType == "json") {
            MessageInterface message;
            try {
                message = new JsonMessage(testDefinition);
            }
            catch (MessageException jsonException) {
                System.out.println(jsonException.getMessage());
                return;
            }

            if (message.isValid()) {
                type = "JSON";
            }
        }
        else if (testType == "xml") {
            MessageInterface message = new XmlMessage(testDefinition);
            if (message.isValid()) {
                type = "XML";
            }
        }
        else {
            System.out.println("Unrecognised test type: " + testType);
        }
    }

    public String getTestType() {
        if(type != null && !type.isEmpty()) {
            return type;
        }

        return "";
    }
}

