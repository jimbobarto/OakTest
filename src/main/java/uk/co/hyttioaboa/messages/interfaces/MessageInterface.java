package uk.co.hyttioaboa.messages.interfaces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.hyttioaboa.messages.json.JsonPage;

import java.util.ArrayList;

public interface MessageInterface {
    ArrayList<PageInterface> pages = new ArrayList<PageInterface>();;
    String url = "";

    public String getUrl();

    public String setUrl(String newUrl);

    public boolean isValid();

    public ArrayList getPages();

    public ArrayList getElements();

    public boolean hasPages();

    public boolean hasElements();

    boolean arrayHasElements(ArrayList array);

}
