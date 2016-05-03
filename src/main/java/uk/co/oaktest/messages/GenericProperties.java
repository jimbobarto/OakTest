package uk.co.oaktest.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class GenericProperties {
    public Integer index;
    public String identifier;
    public String identifierType;
    public String type;
    public String interaction;
    public String name;
    public String value;
    public Integer timeout;
    public String selectBy;
    public String verb;
    public String text;
    public String uri;
    public String headers;
    public String payload;
    public String expectedResults;
    Integer screenshotSetting;

    public GenericProperties() {
        //TODO: screenshot, wait (pause), save value
    }

    public Integer getIndex() {
        return this.index;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getIdentifierType() {
        return this.identifierType;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getText() {return this.text;}

    public String getValue() {return this.value;}

    public String getSelectBy() {return this.selectBy;}

    public String getInteraction() {
        return this.interaction;
    }

    public Integer getTimeout() {
        return this.timeout;
    }

    public String getVerb() {
        return this.verb;
    }

    public String getUri() {
        return this.uri;
    }

    public String getHeaders() {
        return this.headers;
    }

    public String getPayload() {
        return this.payload;
    }

    public String getExpectedResults() {
        return this.expectedResults;
    }

    public Integer setIndex(Integer newIndex) {
        this.index = newIndex;
        return this.index;
    }

    public String setIdentifier(String newIdentifier) {
        this.identifier = newIdentifier;
        return this.identifier;
    }

    public String setIdentifierType(String newIdentifierType) {
        this.identifierType = newIdentifierType;
        return this.identifierType;
    }
    public String setName(String newName) {
        this.name = newName;
        return this.name;
    }

    public String setType(String newType) {
        this.type = newType;
        return this.type;
    }

    public String setValue(String newValue) {
        this.value = newValue;
        return this.value;
    }

    public String setText(String newValue) {
        this.text = newValue;
        return this.text;
    }
    public String setInteraction(String newInteraction) {
        this.interaction = newInteraction;
        return this.interaction;
    }

    public Integer setTimeout(Integer newTimeout) {
        this.timeout = newTimeout;
        return this.timeout;
    }

    public String setSelectBy(String newSelectBy) throws MessageException {
        if (newSelectBy != null) {
            ArrayList<String> validSelectBy = new ArrayList<String>(Arrays.asList("value", "label"));
            if (validSelectBy.contains(newSelectBy)) {
                this.selectBy = newSelectBy;
                return this.selectBy;
            } else {
                throw new MessageException("selectBy of '" + newSelectBy + "' is not valid");
            }
        }

        return this.selectBy;
    }

    public String setVerb(String newVerb) {
        this.verb = newVerb;
        return this.verb;
    }

    public String setUri(String newUri) {
        this.uri = newUri;
        return this.uri;
    }

    public String setHeaders(String newHeaders) {
        this.headers = newHeaders;
        return this.headers;
    }

    public String setPayload(String newPayload) {
        this.payload = newPayload;
        return this.payload;
    }

    public String getExpectedResults(String newExpectedResults) {
        this.expectedResults = newExpectedResults;
        return this.expectedResults;
    }

    public boolean arrayHasElements(ArrayList array) {
        if (array.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean mapHasElements(Map map) {
        if (map == null) {
            return false;
        }
        else if (map.size() == 0) {
            return false;
        }
        return true;
    }

    public Integer getScreenshotSetting() {
        return this.screenshotSetting;
    }

    public Integer setScreenshotSetting(Integer newScreenshotSetting) {
        this.screenshotSetting = newScreenshotSetting;
        return this.screenshotSetting;
    }

    public Integer calculateScreenshotSetting(Integer parentScreenshotSetting) {
        Integer currentScreenshotSetting = getScreenshotSetting();
        if (parentScreenshotSetting != null && parentScreenshotSetting > 0) {
            if (currentScreenshotSetting != null && currentScreenshotSetting > 0) {
                if (parentScreenshotSetting > currentScreenshotSetting) {
                    return parentScreenshotSetting;
                }
                return currentScreenshotSetting;
            }
            else {
                return parentScreenshotSetting;
            }
        }
        else if (currentScreenshotSetting != null && currentScreenshotSetting > 0) {
            return currentScreenshotSetting;
        }
        else {
            return 0;
        }
    }

}
