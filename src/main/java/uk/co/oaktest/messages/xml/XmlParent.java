package uk.co.oaktest.messages.xml;

import org.json.JSONException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.co.oaktest.messages.MessageException;

import java.util.ArrayList;

public class XmlParent extends XmlNode {
    ArrayList<XmlElement> elements = new ArrayList<XmlElement>();

    public XmlParent(String givenTestDefinition) {
        super(givenTestDefinition);
    }

    public XmlParent(Node givenTestDefinition) throws MessageException {
        super(givenTestDefinition);

        setScreenshotSetting(getXmlScreenshotSetting());

        Node elementsNode = getChild(message, "elements");
        if (elementsNode != null) {
            getElements(elementsNode);
        }
    }

    public Integer getXmlScreenshotSetting() {
        Integer xmlScreenshotSetting;
        Integer parentScreenshotSetting = 0;
        Node parentScreenshotSettingNode = getChild(message, "parentScreenshotSetting");
        if (parentScreenshotSettingNode != null) {
            parentScreenshotSetting = Integer.parseInt(parentScreenshotSettingNode.getTextContent());
        }

        return calculateScreenshotSetting(parentScreenshotSetting);
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
