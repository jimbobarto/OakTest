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

public class XmlParent {
    Node message;
    String testDefinition;
    ArrayList<XmlElement> elements = new ArrayList<XmlElement>();

    public XmlParent(String givenTestDefinition) {
        testDefinition = givenTestDefinition;
    }

    public XmlParent(Node givenTestDefinition) {
        message = givenTestDefinition;

        Node elementsNode = getChild(message, "elements");
        if (elementsNode != null) {
            elements = getElements(elementsNode);
        }
    }

    public Node getChild(Node parent, String childName) {
        NodeList childNodes = parent.getChildNodes();
        int numberOfChildNodes = childNodes.getLength();

        for (int nodeCounter = 0; nodeCounter< numberOfChildNodes; nodeCounter++) {
            Node currentNode = childNodes.item(nodeCounter);
            if (currentNode.getNodeName() == childName) {
                return currentNode;
            }
        }

        return null;
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

    public boolean isValid() {
        if (parseDocument() != null) {
            return true;
        }
        return false;
    }

    public Node parseDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);

        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            return null;
        }

        builder.setErrorHandler(new XmlErrorHandler());
        Document document;
        try {
            // the "parse" method also validates XML, will throw an exception if misformatted
            document = builder.parse(new InputSource(new StringReader(testDefinition)));
        }
        catch (SAXException e) {
            return null;
        }
        catch (IOException e) {
            return null;
        }

        return document.getDocumentElement();
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
        Node elementsNode = getChild(parentElement, "elements");
        if (elementsNode != null) {
            try {
                ArrayList<Node> grandchildren = getGrandchildren(elementsNode, "element");

                for (int i = 0; i < grandchildren.size(); i++) {
                    XmlElement currentElement = new XmlElement(grandchildren.get(i));

                    elements.add(currentElement);
                }
            }
            catch (Exception ex) {
                throw new Error(ex);
            }
        }

        return elements;
    }

}
