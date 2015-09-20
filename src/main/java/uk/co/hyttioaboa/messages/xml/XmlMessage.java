package uk.co.hyttioaboa.messages.xml;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by jamesbartlett on 23/08/15.
 */
public class XmlMessage {

    String testDefinition;

    public XmlMessage(String givenTestDefinition) {
        testDefinition = givenTestDefinition;

        if (isMessageValid()) {
            // TODO: construct message
        }
    }

    public boolean isMessageValid() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);

        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            return false;
        }

        builder.setErrorHandler(new XmlErrorHandler());
        try {
            // the "parse" method also validates XML, will throw an exception if misformatted
            Document document = builder.parse(new InputSource(new StringReader(testDefinition)));
        }
        catch (SAXException e) {
            return false;
        }
        catch (IOException e) {
            return false;
        }

        return true;
    }


}
