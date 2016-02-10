package uk.co.oaktest.messages.interfaces;

import uk.co.oaktest.messages.MessageException;

/**
 * Created by jamesbartlett on 05/10/15.
 */
public interface ElementInterface {

    public Integer setIndex(Integer index);

    public String setIdentifier(String identifier);

    public String setName(String name);

    public String setType(String type);

    public String setText(String text);

    public String setInteraction(String interaction);

    public Integer setTimeout(Integer timeout);

    public String setIdentifierType(String identifierType);

    public String setSelectBy(String selectBy) throws MessageException;

    public Integer setScreenshotSetting(Integer screenshotSetting);



    public Integer getIndex();

    public String getIdentifier();

    public String getName();

    public String getType();

    public String getValue();

    public String getInteraction();

    public Integer getTimeout();

    public String getIdentifierType();

    public String getText();

    public String getSelectBy();

    public Integer getScreenshotSetting();
}
