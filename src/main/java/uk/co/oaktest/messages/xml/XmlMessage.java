package uk.co.oaktest.messages.xml;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.co.oaktest.messages.MessageException;
import uk.co.oaktest.messages.interfaces.MessageInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XmlMessage extends XmlParent implements MessageInterface {
    ArrayList<XmlPage> pages = new ArrayList<XmlPage>();
    Map<String, String> variables;
    String testDefinition;
    String url;

    public XmlMessage(String givenTestDefinition) throws MessageException {
        super(givenTestDefinition);
        testDefinition = givenTestDefinition;

        if (isValid()) {
            convertDefinitionToMessage();
        }
    }

    public String getUrl() {
        return this.url;
    }

    public String setUrl(String newUrl) {
        this.url = newUrl;
        return url;
    }

    public ArrayList getPages() {
        return pages;
    }

    public Map getVariables() {
        return this.variables;
    }

    public boolean hasPages() {
        return arrayHasElements(pages);
    }

    public boolean hasVariables() {
        return mapHasElements(this.variables);
    }

    public Node convertDefinitionToMessage() throws MessageException {
        try {
            message = parseDocument();
        }
        catch (Exception ex) {
            throw new Error(ex);
        }

        pages = getPages(message);
        elements = getElements(message);

        if (hasPages() && !hasElements()) {
            //pages = getPages(message);

        }
        else if (hasElements() && !hasPages()) {
            //elements = getElements(message);
        }
        else if (hasElements() && hasPages()) {
            throw new Error("Message has both pages and elements at the top level");
        }

        String url = getChildStringValue("url");
        if (url != "") {
            setUrl(url);
        }
        NamedNodeMap attributes = message.getAttributes();
        if (attributes.getNamedItem("name") != null) {
            setName(attributes.getNamedItem("name").getNodeValue());
        }

        return message;
    }

    protected ArrayList<XmlPage> getPages(Node parentElement) throws MessageException {
        Node pagesNode = getChild(parentElement, "pages");
        if (pagesNode != null) {
            ArrayList<Node> grandchildren = getGrandchildren(pagesNode, "page");

            for (int i = 0; i < grandchildren.size(); i++) {
                XmlPage currentPage = new XmlPage(grandchildren.get(i));

                pages.add(currentPage);
            }
        }

        return pages;
    }

    public Map<String, String> getVariables(Node parentElement) throws MessageException {
        Map<String, String> newMap = new HashMap<String, String>();
        Node variablesNode;

        try {
            if (getChild(parentElement, "variables") != null) {
                variablesNode = getChild(parentElement, "variables");

                ArrayList<Node> grandchildren = getGrandchildren(variablesNode, "variable");

                for (int variableIndex = 0; variableIndex < grandchildren.size(); variableIndex++) {
                    Node currentNode = grandchildren.get(variableIndex);
                    NodeList variableNodes = currentNode.getChildNodes();

                    String key;
                    String value;
                    for (int variableChildIndex = 0; variableChildIndex < variableNodes.getLength(); variableChildIndex++) {
                        Node variableChild = variableNodes.item(variableChildIndex);
                        NamedNodeMap variableAttributes = variableChild.getAttributes();

                        key = variableAttributes.getNamedItem("name").getNodeName();
                        value = variableChild.getNodeValue();
                        newMap.put(key, value);
                    }
                }
            }
        }
        catch (Exception ex) {
            throw new MessageException("Invalid variable/s", ex);
        }

        return newMap;
    }
}
