package uk.co.hyttioaboa.messages;

import org.json.JSONException;
import org.json.JSONObject;
import uk.co.hyttioaboa.messages.json.JsonElement;

public class Element {
    Integer elementIndex;
    String executorInstruction;

    public Element(Integer index, String instruction) {
        elementIndex = index;
        executorInstruction = instruction;
    }

    public Element(JsonElement elementdefinition) {
        setIndex(elementdefinition.getIndex());
        setInstruction(elementdefinition.getIdentifier());
    }

    public Integer setIndex(Integer index) {
        elementIndex = index;
        return elementIndex;
    }

    public String setInstruction(String instruction) {
        executorInstruction = instruction;
        return executorInstruction;
    }

    public Integer getIndex() {
        return elementIndex;
    }

    public String getInstruction() {
        return executorInstruction;
    }
}
