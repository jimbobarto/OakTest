package uk.co.hyttioaboa.messages.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import uk.co.hyttioaboa.messages.GenericProperties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class XmlNode extends GenericProperties {
    Node message;
    String testDefinition;
    String name;
    NamedNodeMap attributes;

    public XmlNode(String givenTestDefinition) {
        testDefinition = givenTestDefinition;

        if (isValid()) {
            message = parseDocument();
            addAttributes();
        }
    }

    public XmlNode(Node givenTestDefinition) {
        this.message = givenTestDefinition;
        addAttributes();
    }

    private void addAttributes() {
        this.attributes = this.message.getAttributes();
        if (this.attributes.getNamedItem("name") != null) {
            setName(this.attributes.getNamedItem("name").getNodeValue());
        }
    }

    public String getName() {
        return this.name;
    }

    public String setName(String newName) {
        this.name = newName;
        return this.name;
    }

    public String getChildStringValue(String childNodeName) {
        Node childValueNode = getChild(message, childNodeName);
        if (childValueNode != null) {
            return childValueNode.getTextContent();
        }
        return "";
    }

    public Integer getChildIntegerValue(String childNodeName) {
        Node childValueNode = getChild(message, childNodeName);
        if (childValueNode != null) {
            return Integer.parseInt(childValueNode.getTextContent());
        }
        return null;
    }

    public String getAttributeStringValue(String childNodeName) {
        Node childValueNode = getChild(message, childNodeName);
        if (childValueNode != null) {
            return childValueNode.getTextContent();
        }
        return "";
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

    public ArrayList<Node> getChildren(Node parent, String childName) {
        ArrayList<Node> children = new ArrayList<Node>();

        NodeList childNodes = parent.getChildNodes();
        int numberOfChildNodes = childNodes.getLength();

        for (int nodeCounter = 0; nodeCounter< numberOfChildNodes; nodeCounter++) {
            Node currentNode = childNodes.item(nodeCounter);
            if (currentNode.getNodeName() == childName) {
                children.add(currentNode);
            }
        }

        return children;
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
            //return null;
            throw new Error(e);
        }

        builder.setErrorHandler(new XmlErrorHandler());
        Document document;
        try {
            // the "parse" method also validates XML, will throw an exception if misformatted
            document = builder.parse(new InputSource(new StringReader(testDefinition)));
        }
        catch (SAXException e) {
            throw new Error(e);
        }
        catch (IOException e) {
            throw new Error(e);
        }

        return document.getDocumentElement();
    }
}
