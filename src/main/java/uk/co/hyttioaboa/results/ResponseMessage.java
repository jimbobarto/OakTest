package uk.co.hyttioaboa.results;

public class ResponseMessage {
    Integer status;
    String message;

    public ResponseMessage(Integer newStatus, String newMessage) {
        this.status = newStatus;
        this.message = newMessage;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }
}
