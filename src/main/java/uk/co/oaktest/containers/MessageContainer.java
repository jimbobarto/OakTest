package uk.co.oaktest.containers;

import org.openqa.selenium.WebDriver;
import uk.co.oaktest.messages.interfaces.MessageInterface;
import uk.co.oaktest.results.ResponseNode;
import uk.co.oaktest.utils.UrlConstructor;
import uk.co.oaktest.variables.Translator;

public class MessageContainer {
    Integer parentScreenshotSetting;

    public MessageContainer() {
    }

    public MessageContainer(Integer newParentScreenshotSetting) {
        this.parentScreenshotSetting = newParentScreenshotSetting;
    }

    public Integer getParentShotSetting() {
        return this.parentScreenshotSetting;
    }

    public Integer setParentShotSetting(Integer newParentScreenshotSetting) {
        this.parentScreenshotSetting = newParentScreenshotSetting;
        return this.parentScreenshotSetting;
    }

}
