package uk.co.hyttioaboa.messages.json;

public class JsonException extends Exception {
    public JsonException () {

    }

    public JsonException (String message) {
        super (message);
    }

    public JsonException (Throwable cause) {
        super (cause);
    }

    public JsonException (String message, Throwable cause) {
        super (message, cause);
    }
}
