package uk.co.oaktest.constants;

public enum Browser {
    IE("internet_explorer"),
    CHROME("chrome"),
    FIREFOX("firefox");

    private String value;

    private Browser(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
