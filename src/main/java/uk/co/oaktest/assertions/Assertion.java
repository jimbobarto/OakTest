package uk.co.oaktest.assertions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.results.ResponseMessage;
import uk.co.oaktest.variables.Translator;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Assertion {

    @NotEmpty
    @JsonProperty
    private String expected;

    @NotEmpty
    @JsonProperty
    private String actual;

    @NotEmpty
    @JsonProperty
    private String comparisonType;

    @NotEmpty
    @JsonProperty
    private String assertionType;

    private String translatedExpected;
    private String translatedActual;

    private Translator translator;

    public Assertion() {

    }

    public Assertion(String expected, String actual, String comparisonType, String assertionType) {
        this.expected = expected;
        this.actual = actual;
        this.comparisonType = comparisonType;
        this.assertionType = assertionType;
    }

    public ResponseMessage check(Translator translator) {
        this.translator = translator;

        this.translatedExpected = this.translator.translate(this.expected);
        this.translatedActual = this.translator.translate(this.actual);

        switch (this.assertionType) {
            case "string":
                return checkStrings();
            case "path":
                return checkPaths();
            case "integer":
            case "decimal":
            case "date":
            default:
                return new ResponseMessage(Status.BASIC_FAILURE.value(), "Unknown assertion type '" + this.assertionType + "'");
        }
    }

    private ResponseMessage checkStrings() {
        switch (this.comparisonType) {
            case "equals":
                if (this.translatedExpected.equals(this.translatedActual)) {
                    return new ResponseMessage(Status.TEXT_MATCH_SUCCESS.value(), "The variable '" + this.actual + "' had the value '" + this.translatedExpected + "' as expected");
                } else {
                    return new ResponseMessage(Status.TEXT_CHECK_FAILURE.value(), "The variable '" + this.actual + "' had the value '" + this.translatedActual + "' but expected '" + this.translatedExpected + "'");
                }
            case "contains":
                if (this.translatedExpected.contains(this.translatedActual)) {
                    return new ResponseMessage(Status.TEXT_MATCH_SUCCESS.value(), "The variable '" + this.actual + "' contained '" + this.translatedExpected + "' as expected");
                } else {
                    return new ResponseMessage(Status.TEXT_CHECK_FAILURE.value(), "The variable '" + this.actual + "' had the value '" + this.translatedActual + "' but did not contain '" + this.translatedExpected + "'");
                }
            default:
                return new ResponseMessage(Status.BASIC_FAILURE.value(), "Unknown comparison type '" + this.comparisonType + "'");
        }
    }

    private ResponseMessage checkPaths() {
        switch (this.comparisonType) {
            case "does not exist":
                if (this.translator.isPath(this.translatedActual)) {
                    return new ResponseMessage(Status.BASIC_FAILURE.value(), "The path '" + this.actual + "' existed");
                }
                else {
                    return new ResponseMessage(Status.BASIC_SUCCESS.value(), "The path '" + this.actual + "' did not exist, as expected");
                }
            case "exists":
                if (this.translator.isPath(this.translatedActual)) {
                    return new ResponseMessage(Status.BASIC_SUCCESS.value(), "The path '" + this.actual + "' exists as expected");
                }
                else {
                    return new ResponseMessage(Status.BASIC_FAILURE.value(), "The path '" + this.actual + "' did not exist");
                }
            default:
                return new ResponseMessage(Status.BASIC_FAILURE.value(), "Unknown comparison type '" + this.comparisonType + "'");
        }
    }
}
