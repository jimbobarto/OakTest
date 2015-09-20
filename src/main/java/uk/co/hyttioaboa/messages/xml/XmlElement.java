package uk.co.hyttioaboa.messages.xml;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import uk.co.hyttioaboa.messages.GenericElement;


public class XmlElement extends GenericElement {

    Node elementXml;
    Integer index;
    String instruction;
    String type;
    String interaction;
    String value;
    String timeout;

    public XmlElement(Node elementDefinition) {
        setDefinition(elementDefinition);

        NamedNodeMap attributes = this.elementXml.getAttributes();

        setIndex(Integer.parseInt(attributes.getNamedItem("index").getNodeValue()));
        setInstruction(attributes.getNamedItem("instruction").getNodeValue());
        setType(attributes.getNamedItem("type").getNodeValue());
        setInteraction(attributes.getNamedItem("interaction").getNodeValue());
        setValue(attributes.getNamedItem("value").getNodeValue());
        setTimeout(attributes.getNamedItem("timeout").getNodeValue());
    }

    public Node setDefinition(Node newDefinition) {
        this.elementXml = newDefinition;
        return this.elementXml;
    }
}
