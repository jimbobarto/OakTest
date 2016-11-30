package uk.co.oaktest.constants;

public enum Status {
    //100s - information
    //200 - success
    //300 -
    //400 - failures
    //500 - bad errors

    NODE_CREATED(101, "Node created"),
    NODE_FINISHED(111, "Node finished"),
    TIMER_STARTED_FIND(121, "Timer started to find object"),
    TIMER_FINISHED_FIND(122, "Timer finished to find object"),
    ELAPSED_TIME_FIND(123, "Time to find object"),
    TIMER_STARTED_INTERACT(124, "Timer started to interact with object"),
    TIMER_FINISHED_INTERACT(125, "Timer finished to interact with object"),
    ELAPSED_TIME_INTERACT(126, "Time to interact with object"),
    ACTUAL_IDENTIFIER(130, "Identifier used to find object"),
    STACK_TRACE_ADDED(150, "Stack trace added"),

    BASIC_SUCCESS(200, "Success"),
    CHECK_SUCCESS(201, "Successful check"),

    TEXT_CHECK_WARNING(300, "Text does not match"),
    UNKNOWN_ELEMENT(390, "Unknown element"),
    UNKNOWN_IMPLEMENTATION(391, "Unknown implementation"),

    BASIC_FAILURE(400, "Failed"),
    TEXT_CHECK_FAILURE(401, "Text check failed"),
    NO_TEXT_CHECK_DATA(402, "No text found to check"),
    OBJECT_NOT_FOUND(404, "Object not found"),
    TIMEOUT(405, "Timeout"),

    BASIC_ERROR(500, "Error"),
    BASE_ELEMENT_NOT_FOUND(590, "Error - base element not found");

    private Integer value;
    private String description;

    private Status(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }
}
