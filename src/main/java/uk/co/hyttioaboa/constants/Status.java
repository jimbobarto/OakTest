package uk.co.hyttioaboa.constants;

public enum Status {
    NODE_CREATED(101),
    NODE_FINISHED(111),
    ELAPSED_TIME(120),
    TIMER_STARTED(121),
    TIMER_FINISHED(122),
    STACK_TRACE_ADDED(150),
    BASIC_SUCCESS(200),
    BASIC_FAILURE(400),
    OBJECT_NOT_FOUND(404),
    TIMEOUT(405),
    BASIC_ERROR(500);

    private Integer value;

    private Status(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
