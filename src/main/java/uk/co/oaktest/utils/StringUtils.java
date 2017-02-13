package uk.co.oaktest.utils;

import uk.co.oaktest.constants.Status;
import uk.co.oaktest.results.ResponseNode;

public class StringUtils {

    public StringUtils() {

    }

    public boolean compareStrings(String expectedText, String actualText, ResponseNode responseNode) {
        if (expectedText.equals(actualText)) {
            responseNode.addMessage(Status.CHECK_SUCCESS.getValue(), "Text as expected");
            return true;
        }
        else if(expectedText.toLowerCase().equals(actualText.toLowerCase())){
            responseNode.addMessage(Status.TEXT_CHECK_WARNING.getValue(), "Text matched but only when case insensitive");
            return true;
        }
        else {
            String expectedTextWithWhitespaceRemoved = expectedText.replaceAll("\\s", "");
            String actualTextWithWhitespaceRemoved = actualText.replaceAll("\\s", "");

            if (expectedTextWithWhitespaceRemoved.equals(actualTextWithWhitespaceRemoved)) {
                responseNode.addMessage(Status.TEXT_CHECK_WARNING.getValue(), "Text matched but only when whitespace was removed");
                return true;
            }
            else {
                responseNode.addMessage(Status.TEXT_CHECK_FAILURE.getValue(), "Text expected - " + expectedText + "<br/>but found text was -<br/>" + actualText);
                return false;
            }
        }

    }
}
