package requests;

import org.junit.Test;
import uk.co.hyttioaboa.requests.Request;
import uk.co.hyttioaboa.requests.RequestException;

public class RequestTests {
    @Test
    public void simpleGet() {
        Request newRequest = new Request();
        try {
            newRequest.get("http://jsonplaceholder.typicode.com/posts/1");
            System.out.println("Status: " + newRequest.getStatus());
            System.out.println("Body: " + newRequest.getBody());
        }
        catch(RequestException reqEx) {
            System.out.println("Got a problem...");
        }
    }
}
