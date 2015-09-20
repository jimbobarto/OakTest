package uk.co.hyttioaboa.messages.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.hyttioaboa.messages.Element;

import java.util.ArrayList;

public class JsonPage extends JsonParent {

    public JsonPage(JSONObject pageDefinition) {
        super(pageDefinition);
    }

    //TODO: screenshot, if/else (?)
}
