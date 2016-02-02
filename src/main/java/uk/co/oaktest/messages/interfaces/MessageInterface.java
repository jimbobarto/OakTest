package uk.co.oaktest.messages.interfaces;

import java.util.ArrayList;
import java.util.Map;

public interface MessageInterface {
    ArrayList<PageInterface> pages = new ArrayList<PageInterface>();;
    String url = "";

    public String getUrl();

    public String setUrl(String newUrl);

    public String getName();

    public String setName(String newName);

    public String getType();

    public String setType(String newName);

    public String getImplementation();

    public String setImplementation(String newImplementation);

    public boolean isValid();

    public ArrayList getPages();

    public ArrayList getElements();

    public Map getVariables();

    public boolean hasPages();

    public boolean hasElements();

    public boolean hasVariables();

    boolean arrayHasElements(ArrayList array);

}
