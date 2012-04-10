package org.ei.drishti.agent;

import org.apache.commons.io.IOUtils;
import org.ei.drishti.domain.Response;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPAgent {
    public Response<String> fetch(String requestURLPath) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(requestURLPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            return new Response<String>(Response.ResponseStatus.success, IOUtils.toString(inputStream));
        } catch (Exception e) {
            return new Response<String>(Response.ResponseStatus.failure, null);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
