package uk.co.oaktest.browserTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;
import uk.co.oaktest.config.Config;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.containers.Container;
import uk.co.oaktest.messages.interfaces.ElementInterface;
import uk.co.oaktest.messages.jackson.ElementMessage;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.results.Uuid;
import uk.co.oaktest.variables.Translator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Element {
    ElementMessage message;
    ResponseNode elementNode;
    Translator translator;
    Container container;

    public Element (ElementMessage setUpMessage, ResponseNode elementResponseNode, Container elementContainer) {
        this.message = setUpMessage;
        this.elementNode = elementResponseNode;
        this.container = elementContainer;

        Map metaData = this.message.getMetaData();
        if (metaData != null) {
            try {
                String json = new ObjectMapper().writeValueAsString(metaData);
                this.elementNode.addMessage(Status.META_DATA.value(), json);
            }
            catch (JsonProcessingException jsonProcessingException) {
                this.elementNode.addMessage(Status.BASIC_ERROR.value(), jsonProcessingException);
            }
        }
    }

    public Integer test() {
        String identifier = this.message.getIdentifier();
        String type = this.message.getType();
        String interaction = this.message.getInteraction();
        String implementation = this.container.getImplementation();

        //Get the maps for classes and methods
        HashMap interactionTypes = new HashMap(Config.interactionTypes());
        HashMap elementTypes = new HashMap(Config.elementTypes());

        //Get the relevant values from the maps
        String interactionType = (String) interactionTypes.get(interaction);
        String elementType = (String) elementTypes.get(type);

        Boolean screenshotBefore = this.message.getScreenshotBefore();
        Boolean screenshotAfter = this.message.getScreenshotAfter();

        if (screenshotBefore) {
            Screenshot screenshot = new Screenshot(this.container, this.elementNode, Status.SCREENSHOT_BEFORE.value());
            screenshot.cleanUpload("http://localhost:8090/Copper/result/uploadScreenshot/");
        }

        Class<?> runtimeClass = getRuntimeClass(implementation, elementType, type);
        if (runtimeClass == null) {
            return this.elementNode.getStatus();
        }

        Object classInstance;
        try {
            Constructor<?> constructor = runtimeClass.getConstructor(ElementMessage.class, ResponseNode.class, Container.class);
            classInstance = constructor.newInstance(this.message, this.elementNode, this.container);
        } catch (Exception getClassException) {
            //TODO: replace with exception
            this.elementNode.addMessage(Status.BASIC_ERROR.value(), getClassException);
            return Status.BASIC_ERROR.value();
        }

        //Get the relevant method
        java.lang.reflect.Method methodInstance;
        try {
            methodInstance = classInstance.getClass().getMethod(interactionType);
        } catch (Exception getMethodException) {
            //TODO: replace with exception
            this.elementNode.addMessage(Status.BASIC_ERROR.value(), getMethodException);
            return Status.BASIC_ERROR.value();
        }


        //Run the class/method
        try {
            methodInstance.invoke(classInstance);

        } catch (IllegalArgumentException e) {
            this.elementNode.addMessage(Status.BASIC_ERROR.value(), e);
            return Status.BASIC_ERROR.value();
        } catch (IllegalAccessException e) {
            this.elementNode.addMessage(Status.BASIC_ERROR.value(), e);
            return Status.BASIC_ERROR.value();
        } catch (InvocationTargetException e) {
            this.elementNode.addMessage(Status.BASIC_ERROR.value(), e);
            return Status.BASIC_ERROR.value();
        }

        if (screenshotAfter) {
            Screenshot screenshot = new Screenshot(this.container, this.elementNode, Status.SCREENSHOT_AFTER.value());
            screenshot.cleanUpload("http://localhost:8090/Copper/result/uploadScreenshot/");
        }

        return this.elementNode.getStatus();
    }

    private Class<?> checkImplementation(String implementationName, String elementType) {
        Class<?> runtimeClass = null;
        try {
            runtimeClass = Class.forName("uk.co.oaktest.elementInteractions.implementations." + implementationName + "." + elementType);
        }
        catch (ClassNotFoundException e) {
            return null;
        }

        return runtimeClass;
    }

    private Class<?> getClass(String classPath) {
        Class<?> runtimeClass = null;
        try {
            runtimeClass = Class.forName(classPath);
        }
        catch (ClassNotFoundException e) {
            return null;
        }

        return runtimeClass;
    }

    private Class<?> checkClass(String classPath, Integer missingClassStatus, String missingClassMessage) {
        Class<?> runtimeClass = getClass(classPath);
        if (runtimeClass == null) {
            this.elementNode.addMessage(missingClassStatus, missingClassMessage);
        }

        return runtimeClass;
    }

    private Class<?> getRuntimeClass(String implementationName, String elementType, String type) {
        Class<?> runtimeClass = null;
        if (implementationName != null) {
            runtimeClass = checkClass("uk.co.oaktest.elementInteractions.implementations." + implementationName + "." + elementType, Status.UNKNOWN_IMPLEMENTATION.getValue(), "ElementMessage type '" + type + "' in implementation '" + implementationName + "' was not found");
            if (runtimeClass != null) {
                return runtimeClass;
            }
        }
        runtimeClass = checkClass("uk.co.oaktest.elementInteractions." + elementType, Status.UNKNOWN_ELEMENT.getValue(), "ElementMessage type '" + type + "' unrecognised");
        if (runtimeClass != null) {
            return runtimeClass;
        }

        runtimeClass = checkClass("uk.co.oaktest.elementInteractions.BaseElement", Status.BASE_ELEMENT_NOT_FOUND.getValue(), "Base element not found!");
        if (runtimeClass != null) {
            return runtimeClass;
        }

        return runtimeClass;
    }
}
