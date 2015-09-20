package uk.co.hyttioaboa.messages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.hyttioaboa.messages.json.JsonPage;

import java.util.ArrayList;

public interface MessageInterface {
    ArrayList<JsonPage> pages = new ArrayList<JsonPage>();;
    String url = "";

    public String getUrl();

    public ArrayList getPages();

    public ArrayList getElements();

    public boolean hasPages();

    public boolean hasElements();

    boolean arrayHasElements(ArrayList array);

}
