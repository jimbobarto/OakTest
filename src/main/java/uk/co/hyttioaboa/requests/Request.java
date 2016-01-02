package uk.co.hyttioaboa.requests;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Request {
    CloseableHttpClient httpclient;
    CloseableHttpResponse response;
    ResponseHandler<String> responseHandler;
    int statusCode;
    String responseBody;

    public Request() {
        this.httpclient = HttpClients.createDefault();
    }

    public int get(String uri) throws RequestException {
        try {
            HttpGet httpGet = new HttpGet(uri);
            this.response = this.httpclient.execute(httpGet);
            this.responseBody = extractBody();
            //this.response.close();
            this.statusCode = this.response.getStatusLine().getStatusCode();
        }
        catch (IOException ex) {
            throw new RequestException("Unexpected response status: " + this.statusCode, ex);
        }

        return this.statusCode;
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

    public String getBody() {
        return this.responseBody;
    }

    public int getStatus() {
        return this.statusCode;
    }
}
