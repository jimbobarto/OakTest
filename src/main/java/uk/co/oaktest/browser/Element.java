package uk.co.oaktest.browser;

import org.openqa.selenium.WebDriver;
import uk.co.oaktest.config.Config;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.container.Container;
import uk.co.oaktest.messages.interfaces.ElementInterface;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.variables.Translator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by jamesbartlett on 30/11/15.
 */
public class Element {
    ElementInterface message;
    ResponseNode elementNode;
    Translator translator;
    Container container;

    public Element (ElementInterface setUpMessage, ResponseNode elementResponseNode, Container elementContainer) {
        this.message = setUpMessage;
        this.elementNode = elementResponseNode;
        this.container = elementContainer;
    }

    public String test() {
        String identifier = this.message.getIdentifier();
        String type = this.message.getType();
        String interaction = this.message.getInteraction();

        //Get the maps for classes and methods
        HashMap interactionTypes = new HashMap(Config.interactionTypes());
        HashMap elementTypes = new HashMap(Config.elementTypes());

        //Get the relevant values from the maps
        String interactionType=(String)interactionTypes.get(interaction);
        String elementType=(String)elementTypes.get(type);

        Class<?> runtimeClass;
        Object classInstance;

        try {
            runtimeClass = Class.forName("uk.co.oaktest.elementInteractions." + elementType);
        }
        catch (ClassNotFoundException exception) {
            runtimeClass = uk.co.oaktest.elementInteractions.BaseElement.class;
            this.elementNode.addMessage(Status.UNKNOWN_ELEMENT.getValue(), "Element type '" + type + "' unrecognised");
        }

        try {
            Constructor<?> constructor = runtimeClass.getConstructor(ElementInterface.class, ResponseNode.class, Container.class);
            classInstance = constructor.newInstance(this.message, this.elementNode, this.container);
        }
        catch(Exception getClassException) {
            //TODO: replace with exception
            throw new Error(getClassException);
        }

        //Get the relevant method
        java.lang.reflect.Method methodInstance;
        try {
            methodInstance = classInstance.getClass().getMethod(interactionType);
        }
        catch (Exception getMethodException) {
            //TODO: replace with exception
            throw new Error(getMethodException);
        }


        //Run the class/method
        try {
            methodInstance.invoke(classInstance);

        } catch (IllegalArgumentException e) {System.out.println(e);
        } catch (IllegalAccessException e) {System.out.println(e);
        } catch (InvocationTargetException e) {
            //TODO: replace with exception
            throw new Error(e);

        }

        return "Hello";
    }
}
