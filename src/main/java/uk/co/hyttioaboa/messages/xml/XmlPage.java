package uk.co.hyttioaboa.messages.xml;

import org.w3c.dom.Node;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;

/**
 * Created by jamesbartlett on 20/09/15.
 */
public class XmlPage extends XmlParent implements PageInterface {
    public XmlPage(Node pageDefinition) {
        super(pageDefinition);
    }
}
