package assertions;

import org.apache.log4j.Logger;
import org.junit.Test;
import uk.co.oaktest.assertions.Assertion;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.messages.jackson.Variable;
import uk.co.oaktest.requests.Request;
import uk.co.oaktest.requests.RequestException;
import uk.co.oaktest.results.ResponseMessage;
import uk.co.oaktest.variables.Translator;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AssertionTests {
    final static Logger logger = Logger.getLogger(AssertionTests.class);

    @Test
    public void stringEquals() {
        Assertion assertion = new Assertion("string", "string", "equals", "string");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.TEXT_MATCH_SUCCESS.value());
    }

    @Test
    public void stringNotEquals() {
        Assertion assertion = new Assertion("string", "notstring", "equals", "string");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.TEXT_CHECK_FAILURE.value());
    }

    @Test
    public void stringContains() {
        Assertion assertion = new Assertion("longstringmaybe", "string", "contains", "string");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.TEXT_MATCH_SUCCESS.value());
    }

    @Test
    public void stringNotContains() {
        Assertion assertion = new Assertion("string", "not", "contains", "string");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.TEXT_CHECK_FAILURE.value());
    }

    @Test
    public void integerEquals() {
        Assertion assertion = new Assertion("2", "2", "equals", "integer");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.CHECK_SUCCESS.value());
    }

    @Test
    public void integerNotEquals() {
        Assertion assertion = new Assertion("2", "3", "equals", "integer");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.BASIC_FAILURE.value());
    }

    @Test
    public void integerGreaterThan() {
        Assertion assertion = new Assertion("4", "3", "greater than", "integer");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.CHECK_SUCCESS.value());
    }

    @Test
    public void integerNotGreaterThan() {
        Assertion assertion = new Assertion("4", "4", "greater than", "integer");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.BASIC_FAILURE.value());
    }

    @Test
    public void integerLessThan() {
        Assertion assertion = new Assertion("4", "5", "less than", "integer");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.CHECK_SUCCESS.value());
    }

    @Test
    public void integerNotLessThan() {
        Assertion assertion = new Assertion("4", "4", "less than", "integer");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.BASIC_FAILURE.value());
    }

    @Test
    public void decimalEquals() {
        Assertion assertion = new Assertion("2.05", "2.05", "equals", "decimal");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.CHECK_SUCCESS.value());
    }

    @Test
    public void decimalNotEquals() {
        Assertion assertion = new Assertion("3.01", "3.00", "equals", "decimal");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.BASIC_FAILURE.value());
    }

    @Test
    public void decimalGreaterThan() {
        Assertion assertion = new Assertion("4.01", "4.00", "greater than", "decimal");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.CHECK_SUCCESS.value());
    }

    @Test
    public void decimalNotGreaterThan() {
        Assertion assertion = new Assertion("4.00", "4.00", "greater than", "decimal");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.BASIC_FAILURE.value());
    }

    @Test
    public void decimalLessThan() {
        Assertion assertion = new Assertion("5.00", "5.01", "less than", "decimal");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.CHECK_SUCCESS.value());
    }

    @Test
    public void decimalNotLessThan() {
        Assertion assertion = new Assertion("4.00", "4.00", "less than", "decimal");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.BASIC_FAILURE.value());
    }

    @Test
    public void pathExists() {
        ArrayList<Variable> variables = new ArrayList<>();
        variables.add(new Variable("variable", "string", "new value"));
        variables.add(new Variable("variablePath", "string", "{\"name\": \"found it\"}"));

        Translator translator = new Translator(variables);

        Assertion assertion = new Assertion("variablePath.name", "found it", "exists", "path");
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.BASIC_SUCCESS.value());
    }

    @Test
    public void pathNotExists() {
        ArrayList<Variable> variables = new ArrayList<>();
        variables.add(new Variable("variable", "string", "new value"));
        variables.add(new Variable("variablePath", "string", "{\"name\": \"found it\"}"));

        Translator translator = new Translator(variables);

        Assertion assertion = new Assertion("variablePath.namep", "found it", "does not exist", "path");
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(responseMessage.getStatus(), Status.BASIC_FAILURE.value());
    }

    @Test
    public void dateEquals() {
        Assertion assertion = new Assertion("13/01/1978", "13/01/1978", "equals", "date");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(Status.BASIC_SUCCESS.value(), responseMessage.getStatus());
    }

    @Test
    public void dateNotEquals() {
        Assertion assertion = new Assertion("14/01/1978", "13/01/1978", "equals", "date");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(Status.BASIC_FAILURE.value(), responseMessage.getStatus());
    }

    @Test
    public void dateAfter() {
        Assertion assertion = new Assertion("13/01/1978", "14/01/1978", "after", "date");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(Status.BASIC_SUCCESS.value(), responseMessage.getStatus());
    }

    @Test
    public void dateNotAfter() {
        Assertion assertion = new Assertion("14/01/1978", "14/01/1978", "after", "date");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(Status.BASIC_FAILURE.value(), responseMessage.getStatus());
    }

    @Test
    public void dateBefore() {
        Assertion assertion = new Assertion("13/01/1978", "12/01/1978", "before", "date");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(Status.BASIC_SUCCESS.value(), responseMessage.getStatus());
    }

    @Test
    public void dateNotBefore() {
        Assertion assertion = new Assertion("14/01/1978", "14/01/1978", "before", "date");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(Status.BASIC_FAILURE.value(), responseMessage.getStatus());
    }

    @Test
    public void dateIsDate() {
        Assertion assertion = new Assertion("14/01/1978", "", "is date", "date");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(Status.BASIC_SUCCESS.value(), responseMessage.getStatus());
    }

    @Test
    public void dateIsNotDate() {
        Assertion assertion = new Assertion("random text", "", "is date", "date");
        Translator translator = new Translator();
        ResponseMessage responseMessage = assertion.check(translator);

        assertEquals(Status.BASIC_FAILURE.value(), responseMessage.getStatus());
    }

}
