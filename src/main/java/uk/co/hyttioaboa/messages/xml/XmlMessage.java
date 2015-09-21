package uk.co.hyttioaboa.messages.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import uk.co.hyttioaboa.messages.MessageInterface;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class XmlMessage extends XmlParent implements MessageInterface {
    ArrayList<XmlPage> pages = new ArrayList<XmlPage>();
    String testDefinition;

    public XmlMessage(String givenTestDefinition) {
        super(givenTestDefinition);
        testDefinition = givenTestDefinition;

        if (isValid()) {
            convertDefinitionToMessage();
        }
    }

    public String getUrl() {
        return url;
    }

    public ArrayList getPages() {
        return pages;
    }

    public boolean hasPages() {
        return arrayHasElements(pages);
    }

    public boolean arrayHasElements(ArrayList array) {
        if (array.size() == 0) {
            return false;
        }
        return true;
    }

    public Node convertDefinitionToMessage() throws Error {
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

        return message;
    }

    protected ArrayList<XmlPage> getPages(Node parentElement) {
        Node pagesNode = getChild(parentElement, "pages");
        if (pagesNode != null) {
            try {
                ArrayList<Node> grandchildren = getGrandchildren(pagesNode, "page");

                for (int i = 0; i < grandchildren.size(); i++) {
                    XmlPage currentPage = new XmlPage(grandchildren.get(i));

                    pages.add(currentPage);
                }
            }
            catch (Exception ex) {
                throw new Error(ex);
            }
        }

        return pages;
    }

}
