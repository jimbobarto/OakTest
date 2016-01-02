package uk.co.hyttioaboa.messages;

import uk.co.hyttioaboa.messages.json.JsonMessage;
import uk.co.hyttioaboa.messages.json.JsonPage;
import uk.co.hyttioaboa.messages.xml.XmlMessage;

import java.util.ArrayList;

public class Message {
    ArrayList<Page> pages;
    ArrayList<Element> elements;
    private String type;
    private String url;

    public Message(String testDefinition) {
        JsonMessage jsonMessage;
        try {
            jsonMessage = new JsonMessage(testDefinition);
        }
        catch (MessageException jsonException) {
            System.out.println(jsonException.getMessage());
            return;
        }

        if (jsonMessage.isValid()) {
            if (jsonMessage.hasPages() && !jsonMessage.hasElements()) {

            }


            System.out.println("Valid JSON");
            type = "JSON";
        }
        else {
            XmlMessage xmlMessage = new XmlMessage(testDefinition);
            if (xmlMessage.isValid()) {
                System.out.println("Valid XML");
                type = "XML";
            }
            else {
                System.out.println("Invalid everything :(");
            }
        }
    }

    private ArrayList getPages(JsonMessage parentElement) {
        ArrayList<JsonPage> newArray = new ArrayList<JsonPage>();

        if (parentElement.hasPages()) {
            newArray = parentElement.getPages();
        }

        return newArray;
    }


}
