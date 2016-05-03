package uk.co.oaktest.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {
    private long runningThreads;
    private long freeThreads;

    public Status() {
        // Jackson deserialization
    }

    public Status(long runningThreads, long freeThreads) {
        this.runningThreads = runningThreads;
        this.freeThreads = freeThreads;
    }

    @JsonProperty
    public long getRunningThreads() {
        return this.runningThreads;
    }

    @JsonProperty
    public long getFreeThreads() {
        return this.freeThreads;
    }
}