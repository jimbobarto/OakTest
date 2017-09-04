package uk.co.oaktest.messages.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import uk.co.oaktest.constants.Status;
import uk.co.oaktest.results.ResponseMessage;
import uk.co.oaktest.variables.Translator;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssertionMessage {

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

    public AssertionMessage() {

    }

    public AssertionMessage(String expected, String actual, String comparisonType, String assertionType) {
        this.expected = expected;
        this.actual = actual;
        this.comparisonType = comparisonType;
        this.assertionType = assertionType;
    }

    public String getExpected() {
        return this.expected;
    }

    public String getActual() {
        return this.actual;
    }

    public String getComparisonType() {
        return this.comparisonType;
    }

    public String getAssertionType() {
        return this.assertionType;
    }

    public String setExpected(String newExpected) {
        this.expected = newExpected;
        return this.expected;
    }

    public String setActual(String newActual) {
        this.actual = newActual;
        return this.actual;
    }

    public String setComparisonType(String newComparisonType) {
        this.comparisonType = newComparisonType;
        return this.comparisonType;
    }

    public String setAssertionType(String newAssertionType) {
        this.assertionType = newAssertionType;
        return this.assertionType;
    }
}
