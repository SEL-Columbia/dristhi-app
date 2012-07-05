package org.ei.drishti.service;

import android.content.Context;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.AbstractVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.ei.drishti.client.GZipEncodingHttpClient;
import org.ei.drishti.domain.Response;
import org.ei.drishti.domain.ResponseStatus;
import org.ei.drishti.util.Log;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

import static org.ei.drishti.util.Log.logWarn;

public class HTTPAgent {

    private final GZipEncodingHttpClient httpClient;
    private Context context;

    public HTTPAgent(Context context) {
        this.context = context;

        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, 30000);
        HttpConnectionParams.setSoTimeout(basicHttpParams, 60000);

        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", sslSocketFactoryWithDrishtiCertificate(), 443));

        SingleClientConnManager connectionManager = new SingleClientConnManager(basicHttpParams, registry);
        httpClient = new GZipEncodingHttpClient(new DefaultHttpClient(connectionManager, basicHttpParams));
    }

    public Response<String> fetch(String requestURLPath) {
        try {
            String responseContent = IOUtils.toString(httpClient.fetchContent(new HttpGet(requestURLPath)));
            return new Response<String>(ResponseStatus.success, responseContent);
        } catch (Exception e) {
            logWarn(e.toString());
            return new Response<String>(ResponseStatus.failure, null);
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

    private SocketFactory sslSocketFactoryWithDrishtiCertificate() {
        try {
            KeyStore trustedKeystore = KeyStore.getInstance("BKS");
            InputStream inputStream = context.getResources().openRawResource(org.ei.drishti.R.raw.drishti_client);
            try {
                trustedKeystore.load(inputStream, "phone red pen".toCharArray());
            } finally {
                inputStream.close();
            }
            SSLSocketFactory socketFactory = new SSLSocketFactory(trustedKeystore);
            final X509HostnameVerifier oldVerifier = socketFactory.getHostnameVerifier();
            socketFactory.setHostnameVerifier(new AbstractVerifier() {
                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                    for (String cn : cns) {
                        if (host.equals(cn)){
                            return;
                        }
                    }
                    oldVerifier.verify(host, cns, subjectAlts);
                }
            });
            return socketFactory;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
