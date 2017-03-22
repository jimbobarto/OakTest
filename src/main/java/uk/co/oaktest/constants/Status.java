package uk.co.oaktest.constants;

public enum Status {
    //100s - information
    //200 - success
    //300 -
    //400 - failures
    //500 - bad errors

    NODE_CREATED(101, "Node created"),
    UUID(102, "uuid"),
    META_DATA(105, "Meta data"),
    NODE_FINISHED(111, "Node finished"),
    TIMER_STARTED_FIND(121, "Timer started to find object"),
    TIMER_FINISHED_FIND(122, "Timer finished to find object"),
    ELAPSED_TIME_FIND(123, "Time to find object"),
    TIMER_STARTED_INTERACT(124, "Timer started to interact with object"),
    TIMER_FINISHED_INTERACT(125, "Timer finished to interact with object"),
    ELAPSED_TIME_INTERACT(126, "Time to interact with object"),
    TIMER_STARTED(127, "Timer started"),
    TIMER_FINISHED(128, "Timer finished"),
    ELAPSED_TIME(129, "Elapsed time"),
    ACTUAL_IDENTIFIER(130, "Identifier used to find object"),
    STACK_TRACE_ADDED(150, "Stack trace added"),

    BASIC_SUCCESS(200, "Success"),
    CHECK_SUCCESS(201, "Successful check"),
    TEXT_MATCH_SUCCESS(210, "Text matches"),
    ACTUAL_URL(240, "Final URL used for request"),
    SCREENSHOT_BEFORE(260, "Screenshot before"),
    SCREENSHOT_AFTER(261, "Screenshot after"),

    TEXT_CHECK_WARNING(300, "Text does not match"),
    UNKNOWN_ELEMENT(390, "Unknown element"),
    UNKNOWN_IMPLEMENTATION(391, "Unknown implementation"),

    BASIC_FAILURE(400, "Failed"),
    TEXT_CHECK_FAILURE(401, "Text check failed"),
    NO_TEXT_CHECK_DATA(402, "No text found to check"),
    OBJECT_NOT_FOUND(404, "Object not found"),
    TIMEOUT(405, "Timeout"),
    NOT_A_PATH(420, "Path assertion on something that is not a path"),

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

    public Integer value() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }
}
