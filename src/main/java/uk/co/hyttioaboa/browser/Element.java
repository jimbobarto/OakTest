package uk.co.hyttioaboa.browser;

import org.openqa.selenium.WebDriver;
import uk.co.hyttioaboa.config.Config;
import uk.co.hyttioaboa.elementInteractions.ElementInteraction;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;
import uk.co.hyttioaboa.results.ResponseNode;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by jamesbartlett on 30/11/15.
 */
public class Element {
    ElementInterface message;
    ResponseNode elementNode;

    public Element (ElementInterface setUpMessage, ResponseNode elementResponseNode) {
        this.message = setUpMessage;
        this.elementNode = elementResponseNode;
    }

    public String test(WebDriver driver) {
            String identifier = this.message.getIdentifier();
            String type = this.message.getType();
            String interaction = this.message.getInteraction();
        /**
            System.out.println("Interaction: " + identifier);
            System.out.println("Type: " + type);
            System.out.println("Interaction: " + interaction);
        */
        //Get the maps for classes and methods
        HashMap interactionTypes = new HashMap(Config.interactionTypes());
        HashMap elementTypes = new HashMap(Config.elementTypes());

        //Get the relevant values from the maps
        String interactionType=(String)interactionTypes.get(interaction);
        String elementType=(String)elementTypes.get(type);

        Object classInstance;
        //GET the relevant class required
        try {
            Class<?> clazz = Class.forName("uk.co.hyttioaboa.elementInteractions."+ elementType);
            Constructor<?> constructor = clazz.getConstructor();
            classInstance = constructor.newInstance();
        }catch(Exception getClassException){
            throw new Error(getClassException);
        }

        //Get the relevant method
        java.lang.reflect.Method methodInstance;
        try {
            methodInstance = classInstance.getClass().getMethod(interactionType, WebDriver.class, ElementInterface.class, ResponseNode.class);
            //methodInstance = classInstance.getClass().getMethod(interaction, WebDriver.class, String.class);
        } catch (Exception getMethodException) {
            throw new Error(getMethodException);
        }


        //Run the class/method
        try {
            //methodInstance.invoke(classInstance, driver, identifier);
            methodInstance.invoke(classInstance, driver, this.message, this.elementNode);

        } catch (IllegalArgumentException e) {System.out.println(e);
        } catch (IllegalAccessException e) {System.out.println(e);
        } catch (InvocationTargetException e) {
            throw new Error(e);

        }

        return "Hello";
    }
}
