package uk.co.hyttioaboa.browser;

import uk.co.hyttioaboa.config.Config;
import uk.co.hyttioaboa.elementInteractions.ElementInteraction;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by jamesbartlett on 30/11/15.
 */
public class Element {
    ElementInterface message;

    public Element (ElementInterface setUpMessage) {
        this.message = setUpMessage;
    }

    public String test() {
            String identifier = this.message.getIdentifier();
            String type = this.message.getType();
            String interaction = this.message.getInteraction();
        /**
            System.out.println("Interaction: " + identifier);
            System.out.println("Type: " + type);
            System.out.println("Interaction: " + interaction);
        */

        HashMap interactionTypes = new HashMap(Config.interactionTypes());
        HashMap elementTypes = new HashMap(Config.elementTypes());

        String interactionType=(String)interactionTypes.get(interaction);
        String elementType=(String)elementTypes.get(type);

        System.out.println("Final interaction type - " + interactionType);
        System.out.println("Final element type - " + elementType);


        Object classInstance =null;
        //GET CLASS from elementTypes
        try {
            Class<?> clazz = Class.forName("uk.co.hyttioaboa.elementInteractions."+ elementType);
            Constructor<?> constructor = clazz.getConstructor();
            classInstance = constructor.newInstance();

        }catch(Exception getClassException){
            throw new Error(getClassException);
        }


        java.lang.reflect.Method methodInstance;
        methodInstance = null;
        try {
            methodInstance = classInstance.getClass().getMethod(interactionType);
        } catch (Exception getMethodException) {
            throw new Error(getMethodException);
        }

        try {
            methodInstance.invoke(interactionType);
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }

        return "Hello";


    }
}
