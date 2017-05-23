package uk.co.oaktest.utils;

public class UrlConstructor {

    String prefixUrl;

    public UrlConstructor(String url) {
        this.prefixUrl = url;
    }

    public String buildUrl(String uri) {
        return this.buildUrl(this.prefixUrl, uri);
    }

    public String buildUrl(String startUri, String endUri) {
        if (startUri == null) {
            return endUri;
        }
        else {
            if (endUri == null) {
                return startUri;
            }
            else {
                if (endUri.startsWith("http")) {
                    return endUri;
                }
                else {
                    String lastCharacterOfBaseUrl = startUri.substring(startUri.length() - 1);
                    String firstCharacterOfUri = endUri.substring(0, 1);
                    if (lastCharacterOfBaseUrl.equals("/") && firstCharacterOfUri.equals("/")) {
                        return startUri + endUri.substring(1, endUri.length());
                    } else if (!lastCharacterOfBaseUrl.equals("/") && !firstCharacterOfUri.equals("/")) {
                        return startUri + "/" + endUri;
                    } else {
                        return startUri + endUri;
                    }
                }
            }
        }
    }

}
