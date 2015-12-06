package uk.co.hyttioaboa.messages.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class XmlParent extends XmlNode {
    ArrayList<XmlElement> elements = new ArrayList<XmlElement>();

    public XmlParent(String givenTestDefinition) {
        super(givenTestDefinition);
    }

    public XmlParent(Node givenTestDefinition) {
        super(givenTestDefinition);

        Node elementsNode = getChild(message, "elements");
        if (elementsNode != null) {
            getElements(elementsNode);
        }
    }

    public ArrayList<Node> getGrandchildren(Node parentNode, String childName) {
        ArrayList<Node> grandChildElements = new ArrayList<Node>();

        NodeList childNodes = parentNode.getChildNodes();
        int numberOfChildNodes = childNodes.getLength();

        for (int nodeCounter = 0; nodeCounter< numberOfChildNodes; nodeCounter++) {
            Node currentNode = childNodes.item(nodeCounter);
            if (currentNode.getNodeName() == childName) {
                grandChildElements.add(currentNode);
            }
        }

        return grandChildElements;
    }

    public ArrayList getElements() {
        return elements;
    }

    public boolean hasElements() {
        return arrayHasElements(elements);
    }

    private boolean arrayHasElements(ArrayList array) {
        if (array.size() == 0) {
            return false;
        }
        return true;
    }

    protected ArrayList<XmlElement> getElements(Node parentElement) {
        try {
            ArrayList<Node> children = getChildren(parentElement, "element");

            for (int i = 0; i < children.size(); i++) {
                XmlElement currentElement = new XmlElement(children.get(i));

                elements.add(currentElement);
            }
        }
        catch (Exception ex) {
            throw new Error(ex);
        }

        return elements;
    }

}
