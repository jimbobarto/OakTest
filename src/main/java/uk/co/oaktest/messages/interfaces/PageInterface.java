package uk.co.oaktest.messages.interfaces;

import java.util.ArrayList;

public interface PageInterface {
    ArrayList<ElementInterface> elements = new ArrayList<ElementInterface>();

    public boolean isValid();

    public ArrayList getElements();

    public boolean hasElements();

    public String getName();

    public String setName(String newName);

    public String getType();

    public String setType(String newName);

    public String getVerb();

    public String setVerb(String newName);

    public String getUri();

    public String setUri(String newName);

    public String getHeaders();

    public String setHeaders(String newName);

    public String getPayload();

    public String setPayload(String newName);

    public String getExpectedResult();

    public String setExpectedResult(String newName);

    public Integer getExpectedStatusCode();

    public Integer setExpectedStatusCode(int newStatusCode);

    public Integer getScreenshotSetting();

    public Integer setScreenshotSetting(Integer screenshotSetting);
}
