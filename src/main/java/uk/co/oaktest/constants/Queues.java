package uk.co.oaktest.constants;

public enum Queues {
    TESTS("SimpleQueue"),
    RESULTS("Results");

    private String value;

    private Queues(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
