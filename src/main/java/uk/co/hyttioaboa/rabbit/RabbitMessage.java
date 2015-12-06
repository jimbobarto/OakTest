package uk.co.hyttioaboa.rabbit;

/**
 * Created by jamesbartlett on 06/12/15.
 */
public class RabbitMessage {
    String uri;
    String message;
    String exchange;
    String routingKey;

    public RabbitMessage() {
    }

    public RabbitMessage(String newUri, String newExchange, String newRoutingKey) {
        this.uri = newUri;
        this.exchange = newExchange;
        this.routingKey = newRoutingKey;
    }

    public RabbitMessage(String newUri, String newMessage, String newExchange, String newRoutingKey) {
        this.uri = newUri;
        this.message = newMessage;
        this.exchange = newExchange;
        this.routingKey = newRoutingKey;
    }

    public Boolean hasMessage() {
        if (message != null) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getUri() {
        return this.uri;
    }

    public String getMessage() {
        return this.message;
    }

    public String getExchange() {
        return this.exchange;
    }

    public String getRoutingKey() {
        return this.routingKey;
    }

    public String setUri(String newUri) {
        this.uri = newUri;
        return this.uri;
    }

    public String setMessage(String newMessage) {
        this.message = newMessage;
        return this.message;
    }

    public String setExchange(String newExchange) {
        this.exchange = newExchange;
        return this.exchange;
    }

    public String setRoutingKey(String newRoutingKey) {
        this.routingKey = newRoutingKey;
        return this.routingKey;
    }

}
