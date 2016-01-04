package uk.co.hyttioaboa.constants;

public enum Status {
    NODE_CREATED(101),
    NODE_FINISHED(111),
    STACK_TRACE_ADDED(150),
    BASIC_SUCCESS(200),
    BASIC_FAILURE(400),
    OBJECT_NOT_FOUND(404),
    BASIC_ERROR(500);

    private Integer value;

    private Status(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
