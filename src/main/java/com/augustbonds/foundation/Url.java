package com.augustbonds.foundation;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Url {

    private final URL url;
    private final Map<String, String> query;

    private Url(URL url) throws UnsupportedEncodingException {
        this.url = url;
        query = parseQueryString(url.getQuery());
    }

    public static Url parse(String url) throws MalformedURLException, UnsupportedEncodingException {
        URL parsed = new URL(url);
        return new Url(parsed);
    }

    public Map<String,String> getQuery() {
        return query;
    }

    /*
     * Code taken from https://codereview.stackexchange.com/a/175559 @Roland Illig
     */
    private static Map<String, String> parseQueryString(String query) throws UnsupportedEncodingException {
        Map<String, String> params = new LinkedHashMap<>();
        for (String param : query.split("&")) {
            String[] keyValue = param.split("=", 2);
            String key = URLDecoder.decode(keyValue[0], "UTF-8");
            String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], "UTF-8") : "";
            if (!key.isEmpty()) {
                params.put(key, value);
            }
        }
        return params;
    }
}
