package uk.co.oaktest.constants;

public enum Selenium {
    DEFAULT_TIMEOUT(2);

    private Integer value;

    private Selenium(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
