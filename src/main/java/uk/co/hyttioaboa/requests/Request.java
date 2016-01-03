package uk.co.hyttioaboa.requests;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import uk.co.hyttioaboa.messages.interfaces.PageInterface;

import java.io.IOException;

public class Request {
    CloseableHttpClient httpclient;
    CloseableHttpResponse response;
    ResponseHandler<String> responseHandler;
    Integer statusCode;
    String responseBody;
    String baseUrl;

    public Request() {
        this.httpclient = HttpClients.createDefault();
    }

    public Request(String url) {
        this.baseUrl = url;
        this.httpclient = HttpClients.createDefault();
    }

    public int request(String verb, String uri) throws RequestException {
        String finalUrl = formUrl(uri);

        try {
            HttpUriRequest httpRequest = createRequest(verb, finalUrl);
            this.response = this.httpclient.execute(httpRequest);
            this.responseBody = extractBody();
            this.statusCode = this.response.getStatusLine().getStatusCode();
            this.response.close();
        }
        catch (IOException ex) {
            throw new RequestException("Unexpected response status: " + this.statusCode, ex);
        }

        return this.statusCode;
    }

    public Integer request(PageInterface requestDetails) throws RequestException {
        String uri = requestDetails.getUri();
        String finalUrl = formUrl(uri);
        String verb = requestDetails.getVerb();

        try {
            HttpUriRequest httpRequest = createRequest(verb, finalUrl);
            this.response = this.httpclient.execute(httpRequest);
            this.responseBody = extractBody();
            this.statusCode = this.response.getStatusLine().getStatusCode();
            this.response.close();
        }
        catch (IOException ex) {
            throw new RequestException("Unexpected response status: " + this.statusCode, ex);
        }

        return this.statusCode;
    }

    public Integer get(String uri) throws RequestException {
        String finalUrl = formUrl(uri);
        try {
            HttpUriRequest httpGet = new HttpGet(finalUrl);
            this.response = this.httpclient.execute(httpGet);
            this.responseBody = extractBody();
            this.statusCode = this.response.getStatusLine().getStatusCode();
            this.response.close();
        }
        catch (IOException ex) {
            throw new RequestException("Unexpected response status: " + this.statusCode, ex);
        }

        return this.statusCode;
    }

    private HttpUriRequest createRequest(String methodName, String uri) {
        HttpUriRequest request;
        switch (methodName) {
            case "get":
                request = new HttpGet(uri);
                break;
            case "post":
                request = new HttpPost(uri);
                break;
            case "put":
                request = new HttpPut(uri);
                break;
            case "delete":
                request = new HttpDelete(uri);
                break;
            default:
                request = new HttpGet(uri);
                break;
        }

        return request;
    }

    private String extractBody() throws RequestException {
        try {
            HttpEntity entity = this.response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        }
        catch(IOException ex) {
            throw new RequestException("Could not get message body", ex);
        }
    }

    private String formUrl(String uri) {
        String finalUrl = uri;
        if (this.baseUrl == null) {
            return finalUrl;
        }
        else {
            String lastCharacterOfBaseUrl = this.baseUrl.substring(this.baseUrl.length() - 1);
            String firstCharacterOfUri = uri.substring(0, 1);
            if (lastCharacterOfBaseUrl.equals("/") && firstCharacterOfUri.equals("/")) {
                finalUrl = this.baseUrl + uri.substring(1, uri.length());
            }
            else if (!lastCharacterOfBaseUrl.equals("/") && !firstCharacterOfUri.equals("/")) {
                finalUrl = this.baseUrl + "/" + uri;
            }
            else {
                finalUrl = this.baseUrl + uri;
            }
        }

        return finalUrl;
    }

    public String getBody() {
        return this.responseBody;
    }

    public Integer getStatus() {
        return this.statusCode;
    }
}
