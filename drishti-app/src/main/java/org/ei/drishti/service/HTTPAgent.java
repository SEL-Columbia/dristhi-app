package org.ei.drishti.service;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ei.drishti.domain.Response;
import org.ei.drishti.domain.ResponseStatus;
import org.ei.drishti.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.ei.drishti.util.Log.logWarn;

public class HTTPAgent {

    private final DefaultHttpClient httpClient;

    public HTTPAgent() {
        httpClient = new DefaultHttpClient();
    }

    public Response<String> fetch(String requestURLPath) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(requestURLPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            return new Response<String>(ResponseStatus.success, IOUtils.toString(inputStream));
        } catch (Exception e) {
            logWarn(e.toString());
            return new Response<String>(ResponseStatus.failure, null);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    public boolean urlCanBeAccessWithGivenCredentials(String requestURL, String userName, String password) {
        httpClient.getCredentialsProvider().setCredentials(new AuthScope("www.commcarehq.org", 443, "DJANGO", "digest"),
                new UsernamePasswordCredentials(userName, password));
        try {
            HttpResponse response = httpClient.execute(new HttpHead(requestURL));
            boolean isValidUser = response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
            if (!isValidUser) {
                Log.logError("Invalid credentials for: " + userName + " using " + requestURL);
            }
            return isValidUser;
        } catch (IOException e) {
            Log.logError("Failed to check credentials of: " + userName + " using " + requestURL + ". Error: " + e.toString());
            return false;
        }
    }
}
