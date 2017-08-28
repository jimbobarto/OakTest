package uk.co.oaktest.constants;

public enum OperatingSystemType {
    WINDOWS("Windows"),
    OSX("Osx"),
    LINUX("Linux");

    private String value;

    private OperatingSystemType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
