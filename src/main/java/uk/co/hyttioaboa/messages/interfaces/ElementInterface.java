package uk.co.hyttioaboa.messages.interfaces;

import org.json.JSONException;

/**
 * Created by jamesbartlett on 05/10/15.
 */
public interface ElementInterface {
    public Integer setIndex(Integer index);

    public String setInstruction(String instruction);

    public String setType(String type);

    public String setInteraction(String interaction);

    public Integer setTimeout(Integer timeout);


    public Integer getIndex();

    public String getInstruction();

    public String getType();

    public String getValue();

    public String getInteraction();

    public Integer getTimeout();

}
