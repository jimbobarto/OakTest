package uk.co.oaktest.utils;

public class UrlConstructor {

    String prefixUrl;

    public UrlConstructor(String url) {
        this.prefixUrl = url;
    }

    public String buildUrl(String suffixUrl) {
        return this.buildUrl(this.prefixUrl, suffixUrl);
    }

    public String buildUrl(String newPrefixUrl, String suffixUrl) {
        if (newPrefixUrl.endsWith("/")) {
            if (suffixUrl.startsWith("/")) {
                newPrefixUrl = newPrefixUrl.substring(0, newPrefixUrl.length()-1);
            }
        }
        return newPrefixUrl + suffixUrl;
    }

}
