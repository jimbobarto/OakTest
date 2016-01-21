package uk.co.hyttioaboa.constants;

public enum Status {
    //100s - information
    //200 - success
    //300 -
    //400 - failures
    //500 - bad errors

    NODE_CREATED(101),
    NODE_FINISHED(111),
    TIMER_STARTED_FIND(121),
    TIMER_FINISHED_FIND(122),
    ELAPSED_TIME_FIND(123),
    TIMER_STARTED_INTERACT(124),
    TIMER_FINISHED_INTERACT(125),
    ELAPSED_TIME_INTERACT(126),
    STACK_TRACE_ADDED(150),
    BASIC_SUCCESS(200),
    CHECK_SUCCESS(201),
    TEXT_CHECK_WARNING(300),
    BASIC_FAILURE(400),
    TEXT_CHECK_FAILURE(401),
    NO_TEXT_CHECK_DATA(402),
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
