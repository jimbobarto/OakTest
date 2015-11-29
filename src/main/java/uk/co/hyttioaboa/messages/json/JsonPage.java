package uk.co.hyttioaboa.messages.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.hyttioaboa.messages.Element;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;

import java.util.ArrayList;

public class JsonPage extends JsonParent implements PageInterface {

    public JsonPage(JSONObject pageDefinition) {
        super(pageDefinition);
    }

    //TODO: screenshot, if/else (?)
}
