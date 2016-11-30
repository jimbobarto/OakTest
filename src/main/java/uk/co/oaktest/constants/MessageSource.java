package uk.co.oaktest.constants;

public enum MessageSource {
    RABBIT("RabbitMQ"),
    HTTP("Http");

    private String value;

    private MessageSource(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
