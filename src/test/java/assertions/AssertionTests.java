package assertions;

import org.apache.log4j.Logger;
import org.junit.Test;
import uk.co.oaktest.assertions.Assertion;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.requests.Request;
import uk.co.oaktest.requests.RequestException;
import uk.co.oaktest.results.ResponseMessage;
import uk.co.oaktest.variables.Translator;

import static org.junit.Assert.assertEquals;

public class AssertionTests {
    final static Logger logger = Logger.getLogger(AssertionTests.class);

    @Test
    public void stringEquals() {
        Assertion assertion = new Assertion("string", "string", "equals", "string");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assert responseMessage.getStatus().equals(Status.TEXT_MATCH_SUCCESS.value());
    }

    @Test
    public void stringNotEquals() {
        Assertion assertion = new Assertion("string", "notstring", "equals", "string");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assert responseMessage.getStatus().equals(Status.TEXT_MATCH_SUCCESS.value());
    }

    @Test
    public void stringContains() {
        Assertion assertion = new Assertion("string", "notstring", "contains", "string");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assert responseMessage.getStatus().equals(Status.TEXT_MATCH_SUCCESS.value());
    }

    @Test
    public void stringNotContains() {
        Assertion assertion = new Assertion("string", "not", "contains", "string");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assert responseMessage.getStatus().equals(Status.TEXT_CHECK_FAILURE.value());
    }
}
