package org.ei.drishti.client;

import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class GZipEncodingHttpClient {
    private DefaultHttpClient httpClient;

    public GZipEncodingHttpClient(DefaultHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public InputStream fetchContent(HttpGet request) throws IOException {
        if (!request.containsHeader("Accept-Encoding")) {
            request.addHeader("Accept-Encoding", "gzip");
        }

        HttpResponse response = httpClient.execute(request);

        if (response.getEntity() != null && response.getEntity().getContentEncoding() != null) {
            HeaderElement[] codecs = response.getEntity().getContentEncoding().getElements();
            for (HeaderElement codec : codecs) {
                if (codec.getName().equalsIgnoreCase("gzip")) {
                    return new GZIPInputStream(response.getEntity().getContent());
                }
            }
        }
        return response.getEntity().getContent();
    }

    public HttpResponse execute(HttpHead request) throws IOException {
        return httpClient.execute(request);
    }

    public CredentialsProvider getCredentialsProvider() {
        return httpClient.getCredentialsProvider();
    }
}
