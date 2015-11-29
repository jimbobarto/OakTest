package uk.co.hyttioaboa.messages.interfaces;

import uk.co.hyttioaboa.messages.interfaces.ElementInterface;
import java.util.ArrayList;

public interface PageInterface {
    ArrayList<ElementInterface> elements = new ArrayList<ElementInterface>();

    public boolean isValid();

    public ArrayList getElements();

    public boolean hasElements();

}
