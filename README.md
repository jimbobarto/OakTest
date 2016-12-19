# OakTest
A selenium framework that requires no coding knowledge

This is the execution part of the overall framework - the part that runs the tests/drives browsers/sends HTTP requests.

OakTest provides a HTTP endpoint to which you can send a 'test' - a test being JSON that represents an actual test.
We have removed RabbitMQ functionality for now.

If the 'test' contains a link then results are passed back to that link, again as JSON.
The executor is database-independant.


##Test structure:

    String url (required)
        This is the first URL the test starts at.

    ArrayList<Variable> variables (optional)
        This is a list of 'variables', data that may be used or reused within the test.

    ArrayList<PageMessage> pages (optional)
        This is a list of pages. In simplest terms each page is in turn a list of elements, which are the HTML elements
        the test will *actually* interact with.

    String name (optional)
        The test name

    String implementation (optional)
        Effectively a filter - this allows the executor to provide different functionality for the same operation. For
        example, a test may include a 'button' element. An 'implementation' allows different actions for a 'button',
        one 'implementation' may just click the button while another may right-click it.

    String resultUrl (optional)
        if this is defined, the results will be PUT to this URL.


##Page Structure

    String name (required)
        The page name

    String type (required)
        The page type - can be a browser or API request

    String url (optional)
        This allows an override URL to be visited before the page starts

    String verb (optional)
        API request only - defines the HTTP verb for the request

    String headers (optional)
        API request only - defines the headers to set for the request

    String payload (optional)
        API request only - defines the payload to set for the request

    String expectedResults (optional)
        API request only - defines the entire expected results for the request

    long expectedStatusCode (optional)
        API request only - defines the expected HTTP status code for the request

    ArrayList<ElementMessage> (required)
        This is a list of elements - the HTML elements the test will *actually* interact with.


##Element Structure

    String type (required)
        The type of the HTML element (e.g. 'link', 'textbox')

    String identifier (required)
        The identifier used to find the element

    String identifierType (required)
        The identifier *type* used to find the element (e.g. 'id', 'css')

    String interaction (required)
        How to interact with the element (e.g. 'click', 'select')

    String name (required)
        The element name

    String text (optional)
        The element text (label) to check

    String value (optional)
        If a textbox or similar, the value to enter in the field

    Integer timeout (optional)
        Allows a custom timeout to be set per element, overrides the default

    String selectBy (optional)
        Select elements only - defines if an option is selected by text or value.


##Variable Structure

    * String name (required)
        The variable name, used to reference the variable in the test
    * String type (required)
        The variable type - currently only Strings but will include at least Dates in the future
    * String value (required)
        The vaiable value - will replace all references to the varibale name in the test at run time
