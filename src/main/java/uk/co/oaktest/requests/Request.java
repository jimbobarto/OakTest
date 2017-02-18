package uk.co.oaktest.requests;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Request {
    CloseableHttpClient httpclient;
    HttpUriRequest httpRequest;
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
        return request(verb, uri, "");
    }

    public int request(String verb, String uri, String body) throws RequestException {
        String finalUrl = formUrl(uri);

        try {
            HttpUriRequest httpRequest = createRequest(verb, finalUrl, body);
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

    public void addHeader(String headerKey, String headerValue) throws RequestException {
        if (this.httpRequest != null) {
            this.httpRequest.addHeader(new BasicHeader(headerKey, headerValue));
        }
        else {
            throw new RequestException("Trying to add header before creating the request");
        }
    }

    public void setUp(String verb, String uri) throws RequestException {
        String finalUrl = formUrl(uri);
        try {
            this.httpRequest = createRequest(verb, finalUrl, "");
        }
        catch (Exception exception) {
            throw new RequestException(exception.getMessage());
        }
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

    public Integer put(String uri, String body) throws RequestException {
        String finalUrl = formUrl(uri);
        try {
            HttpPut httpPut = new HttpPut(finalUrl);
            StringEntity jsonEntity = new StringEntity(body, ContentType.APPLICATION_JSON);
            httpPut.setEntity(jsonEntity);

            this.response = this.httpclient.execute(httpPut);
            this.responseBody = extractBody();
            this.statusCode = this.response.getStatusLine().getStatusCode();
            this.response.close();
        }
        catch (IOException ex) {
            throw new RequestException("Unexpected response status: " + this.statusCode, ex);
        }

        return this.statusCode;
    }

    public HttpUriRequest createRequest(String methodName, String uri, String body) throws RequestException {
        //HttpUriRequest request;
        switch (methodName) {
            case "get":
                return new HttpGet(uri);
            case "post":
                HttpEntityEnclosingRequestBase postRequest = new HttpPost(uri);
                try {
                    postRequest = addBody(postRequest, body);
                }
                catch (Exception exception) {
                    throw new RequestException(exception.getMessage());
                }
                return postRequest;
            case "put":
                HttpEntityEnclosingRequestBase putRequest = new HttpPut(uri);
                try {
                    putRequest = addBody(putRequest, body);
                }
                catch (Exception exception) {
                    throw new RequestException(exception.getMessage());
                }
                return putRequest;
            case "delete":
                return new HttpDelete(uri);
            default:
                return new HttpGet(uri);
        }
    }

    private HttpEntityEnclosingRequestBase addBody(HttpEntityEnclosingRequestBase request, String body) throws RequestException {
        try {
            if (!body.equals("")) {
                StringEntity bodyEntity = new StringEntity(body);
                request.setEntity(bodyEntity);
            }
            return request;
        }
        catch (Exception exception) {
            throw new RequestException("Could not add body to request: " + exception.getMessage());
        }
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
            if (uri == null) {
                return this.baseUrl;
            }
            else {
                String lastCharacterOfBaseUrl = this.baseUrl.substring(this.baseUrl.length() - 1);
                String firstCharacterOfUri = uri.substring(0, 1);
                if (lastCharacterOfBaseUrl.equals("/") && firstCharacterOfUri.equals("/")) {
                    finalUrl = this.baseUrl + uri.substring(1, uri.length());
                } else if (!lastCharacterOfBaseUrl.equals("/") && !firstCharacterOfUri.equals("/")) {
                    finalUrl = this.baseUrl + "/" + uri;
                } else {
                    finalUrl = this.baseUrl + uri;
                }
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
