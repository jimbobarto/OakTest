package uk.co.hyttioaboa.messages.xml;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import uk.co.hyttioaboa.messages.GenericElement;
import uk.co.hyttioaboa.messages.interfaces.ElementInterface;

import java.util.ArrayList;
import java.util.Arrays;


public class XmlElement extends GenericElement implements ElementInterface {

    Node elementXml;
    XmlNode xmlNode;

    public XmlElement(Node elementDefinition) {
        setDefinition(elementDefinition);

        NamedNodeMap attributes = this.elementXml.getAttributes();

        xmlNode = new XmlNode(elementDefinition);

        getProperties(attributes);
    }

    private void getProperties(NamedNodeMap attributes) {
        if (attributes.getNamedItem("name") != null) {
            setName(attributes.getNamedItem("name").getNodeValue());
        }

        String identifier = xmlNode.getChildStringValue("identifier");
        setIdentifier(identifier);
        String identifierType = xmlNode.getChildStringValue("identifierType");
        setIdentifier(identifierType);

        String type = xmlNode.getChildStringValue("type");
        setType(type);
        String interaction = xmlNode.getChildStringValue("interaction");
        setInteraction(interaction);
        String value = xmlNode.getChildStringValue("value");
        setValue(value);
        //String text = xmlNode.getChildStringValue("text");
        Integer timeout = xmlNode.getChildIntegerValue("timeout");
        setTimeout(timeout);
    }

    public Node setDefinition(Node newDefinition) {
        this.elementXml = newDefinition;
        return this.elementXml;
    }

}
