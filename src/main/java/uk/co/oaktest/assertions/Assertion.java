package uk.co.oaktest.assertions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.results.ResponseMessage;
import uk.co.oaktest.variables.Translator;

import java.math.BigDecimal;

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
                return checkIntegers();
            case "decimal":
                return checkDecimals();
            case "date":
            default:
                return new ResponseMessage(Status.BASIC_FAILURE.value(), "Unknown assertion type '" + this.assertionType + "'");
        }
    }

    private ResponseMessage checkStrings() {
        switch (this.comparisonType) {
            case "does not exist":
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

    private ResponseMessage checkIntegers() {
        Integer expectedInteger = convertInteger(this.translatedExpected);
        if (expectedInteger == null) {
            return new ResponseMessage(Status.BASIC_ERROR.value(), "The expected value '" + this.translatedExpected + "' could not be converted to an integer");
        }
        Integer actualInteger = convertInteger(this.translatedActual);
        if (actualInteger == null) {
            return new ResponseMessage(Status.BASIC_ERROR.value(), "The actual value '" + this.translatedActual + "' could not be converted to an integer");
        }

        int comparisonResult = expectedInteger.compareTo(actualInteger);
        return compareNumbers(comparisonResult, this.comparisonType, expectedInteger.toString(), actualInteger.toString());
    }

    private ResponseMessage checkDecimals() {
        BigDecimal expectedDecimal = convertDecimal(this.translatedExpected);
        if (expectedDecimal == null) {
            return new ResponseMessage(Status.BASIC_ERROR.value(), "The expected value '" + this.translatedExpected + "' could not be converted to a decimal");
        }
        BigDecimal actualDecimal = convertDecimal(this.translatedActual);
        if (actualDecimal == null) {
            return new ResponseMessage(Status.BASIC_ERROR.value(), "The actual value '" + this.translatedActual + "' could not be converted to a decimal");
        }

        int comparisonResult = expectedDecimal.compareTo(actualDecimal);
        return compareNumbers(comparisonResult, this.comparisonType, expectedDecimal.toString(), actualDecimal.toString());
    }

    private ResponseMessage compareNumbers(int comparisonResult, String comparisonType, String expectedNumber, String actualNumber) {
        switch (comparisonType) {
            case "equals":
                if (comparisonResult == 0) {
                    return new ResponseMessage(Status.TEXT_MATCH_SUCCESS.value(), "The actual value '" + actualNumber + "' was equal to the expected value '" + expectedNumber + "' as expected");
                } else {
                    return new ResponseMessage(Status.TEXT_CHECK_FAILURE.value(), "The actual value '" + actualNumber + "' was not equal to '" + expectedNumber + "'");
                }
            case "greater than":
                if (comparisonResult > 0) {
                    return new ResponseMessage(Status.TEXT_MATCH_SUCCESS.value(), "The expected value '" + expectedNumber + "' was greater than '" + actualNumber + "' as expected");
                } else {
                    return new ResponseMessage(Status.TEXT_CHECK_FAILURE.value(), "The expected value  '" + expectedNumber + "' was not greater than '" + actualNumber + "'");
                }
            case "less than":
                if (comparisonResult < 0) {
                    return new ResponseMessage(Status.TEXT_MATCH_SUCCESS.value(), "The expected value '" + expectedNumber + "' was less than '" + actualNumber + "' as expected");
                } else {
                    return new ResponseMessage(Status.TEXT_CHECK_FAILURE.value(), "The expected value  '" + expectedNumber + "' was not less than '" + actualNumber + "'");
                }
            default:
                return new ResponseMessage(Status.BASIC_FAILURE.value(), "Unknown comparison type '" + comparisonType + "'");
        }
    }

    private Integer convertInteger(String integerAsString) {
        try {
            return Integer.parseInt(integerAsString);
        }
        catch (NumberFormatException integerConversionException) {
            return null;
        }
    }

    private BigDecimal convertDecimal(String decimalAsString) {
        try {
            return new BigDecimal(decimalAsString);
        }
        catch (NumberFormatException integerConversionException) {
            return null;
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
