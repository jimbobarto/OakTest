package uk.co.oaktest.results;

import java.util.UUID;

public class Uuid {

    UUID uuid;
    String uuidString;

    public Uuid() {
        this.uuid = UUID.randomUUID();
        this.uuidString = this.uuid.toString();
    }

    public String toString() {
        return this.uuidString;
    }

    public UUID get() {
        return this.uuid;
    }
}
