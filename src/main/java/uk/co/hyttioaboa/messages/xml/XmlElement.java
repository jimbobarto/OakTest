package uk.co.hyttioaboa.messages.xml;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import uk.co.hyttioaboa.messages.GenericElement;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;

import java.util.ArrayList;
import java.util.Arrays;


public class XmlElement extends GenericElement implements ElementInterface {

    Node elementXml;

    public XmlElement(Node elementDefinition) {
        setDefinition(elementDefinition);

        NamedNodeMap attributes = this.elementXml.getAttributes();

        getProperties(attributes);
    }

    private void getProperties(NamedNodeMap attributes) {
        if (attributes.getNamedItem("index") != null) {
            setIndex(Integer.parseInt(attributes.getNamedItem("index").getNodeValue()));
        }
        if (attributes.getNamedItem("instruction") != null) {
            setInstruction(attributes.getNamedItem("instruction").getNodeValue());
        }
        if (attributes.getNamedItem("type") != null) {
            setType(attributes.getNamedItem("type").getNodeValue());
        }
        if (attributes.getNamedItem("interaction") != null) {
            setInteraction(attributes.getNamedItem("interaction").getNodeValue());
        }
        if (attributes.getNamedItem("value") != null) {
            setValue(attributes.getNamedItem("value").getNodeValue());
        }
        if (attributes.getNamedItem("timeout") != null) {
            setTimeout(Integer.parseInt(attributes.getNamedItem("timeout").getNodeValue()));
        }
    }

    public Node setDefinition(Node newDefinition) {
        this.elementXml = newDefinition;
        return this.elementXml;
    }

}
