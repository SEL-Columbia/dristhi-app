package org.ei.telemedicine.service;

import static org.ei.telemedicine.AllConstants.REALM;
import static org.ei.telemedicine.domain.LoginResponse.NO_INTERNET_CONNECTIVITY;
import static org.ei.telemedicine.domain.LoginResponse.SUCCESS;
import static org.ei.telemedicine.domain.LoginResponse.UNAUTHORIZED;
import static org.ei.telemedicine.domain.LoginResponse.UNKNOWN_RESPONSE;
import static org.ei.telemedicine.util.HttpResponseUtil.getResponseBody;
import static org.ei.telemedicine.util.Log.logError;
import static org.ei.telemedicine.util.Log.logWarn;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.SSLException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.AbstractVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.ei.telemedicine.DristhiConfiguration;
import org.ei.telemedicine.R;
import org.ei.telemedicine.client.GZipEncodingHttpClient;
import org.ei.telemedicine.domain.LoginResponse;
import org.ei.telemedicine.domain.ProfileImage;
import org.ei.telemedicine.domain.Response;
import org.ei.telemedicine.domain.ResponseStatus;
import org.ei.telemedicine.repository.AllSettings;
import org.ei.telemedicine.repository.AllSharedPreferences;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class HTTPAgent {
    private final GZipEncodingHttpClient httpClient;
    private Context context;
    private AllSettings settings;
    private AllSharedPreferences allSharedPreferences;
    private DristhiConfiguration configuration;


    public HTTPAgent(Context context, AllSettings settings, AllSharedPreferences allSharedPreferences, DristhiConfiguration configuration) {
        this.context = context;
        this.settings = settings;
        this.allSharedPreferences = allSharedPreferences;
        this.configuration = configuration;

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
            Log.e("URLs", requestURLPath);
            setCredentials(allSharedPreferences.fetchRegisteredANM(), settings.fetchANMPassword());
            String responseContent = IOUtils.toString(httpClient.fetchContent(new HttpGet(requestURLPath)));
            return new Response<String>(ResponseStatus.success, responseContent);
        } catch (Exception e) {
            logWarn(e.toString());
            return new Response<String>(ResponseStatus.failure, null);
        }
    }

    public Response<String> post(String postURLPath, String jsonPayload) {
        try {
            setCredentials(allSharedPreferences.fetchRegisteredANM(), settings.fetchANMPassword());
            HttpPost httpPost = new HttpPost(postURLPath);
            StringEntity entity = new StringEntity(jsonPayload, HTTP.UTF_8);
            entity.setContentType("application/json; charset=utf-8");
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.postContent(httpPost);

            ResponseStatus responseStatus = response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED ? ResponseStatus.success : ResponseStatus.failure;
            response.getEntity().consumeContent();
            return new Response<String>(responseStatus, null);
        } catch (Exception e) {
            logWarn(e.toString());
            return new Response<String>(ResponseStatus.failure, null);
        }
    }


    public LoginResponse urlCanBeAccessWithGivenCredentials(String requestURL, String userName, String password) {
//        setCredentials(userName, password);
        try {
            HttpResponse response = httpClient.execute(new HttpGet(requestURL));
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                String responseBody = getResponseBody(response);
                JSONObject responseJson = new JSONObject(responseBody);
                if (responseJson != null && responseJson.has("status")) {
                    logError("Invalid credentials for: " + userName + " using " + requestURL);
                    return UNAUTHORIZED;
                } else if (responseJson != null && !responseJson.has("status")) {
                    logError("Success " + requestURL + "\n result" + responseBody);
                    return SUCCESS.withPayload(responseBody);
                }
            } else {
                logError("Bad response from Dristhi. Status code:  " + statusCode + " username: " + userName + " using " + requestURL);
                return UNKNOWN_RESPONSE;
            }


        } catch (IOException e) {
            logError("Failed to check credentials of: " + userName + " using " + requestURL + ". Error: " + e.toString());
            return NO_INTERNET_CONNECTIVITY;
        } catch (JSONException e) {
            e.printStackTrace();
            return NO_INTERNET_CONNECTIVITY;
        }
        return NO_INTERNET_CONNECTIVITY;
    }

    private void setCredentials(String userName, String password) {
        httpClient.getCredentialsProvider().setCredentials(new AuthScope(configuration.host(), configuration.port(), REALM),
                new UsernamePasswordCredentials(userName, password));
    }

    private SocketFactory sslSocketFactoryWithDrishtiCertificate() {
        try {
            KeyStore trustedKeystore = KeyStore.getInstance("BKS");
            InputStream inputStream = context.getResources().openRawResource(R.raw.dristhi_client);
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
                        if (!configuration.shouldVerifyCertificate() || host.equals(cn)) {
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

    public String callingURL(String requestURL) {
        try {

            HttpResponse response = httpClient.execute(new HttpGet(requestURL));
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                String responseBody = getResponseBody(response);
                logError("Success " + requestURL + "\n result" + responseBody);
                return responseBody;
            } else {
                logError("Bad response. Status code:  " + statusCode + " using " + requestURL);
                return null;
            }
        } catch (IOException e) {
            logError("Failed to get Information");
            return null;
        }
    }

    public String httpImagePost(String url, ProfileImage image) {

        String responseString = "";
        try {
            setCredentials(allSharedPreferences.fetchRegisteredANM(), settings.fetchANMPassword());
            HttpPost httpost = new HttpPost(url);
            Log.e("Image URL", url + "-------------" + image.getContenttype());
            httpost.setHeader("Accept", "multipart/form-data");
            File filetoupload = new File(image.getFilepath());
            Log.v("file to upload", "" + filetoupload.length());
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("anm-id", new StringBody(image.getAnmId()));
            entity.addPart("entity-id", new StringBody(image.getEntityID()));
            entity.addPart("content-type", new StringBody(image.getContenttype()));
            entity.addPart("file-category", new StringBody(image.getEntityID()));
            entity.addPart("file", new FileBody(new File(image.getFilepath())));
            httpost.setEntity(entity);
            Log.e("File Data", new FileBody(new File(image.getFilepath())) + "");
            HttpResponse response = httpClient.postContent(httpost);
            responseString = EntityUtils.toString(response.getEntity());
            int RESPONSE_OK = 200;
            int RESPONSE_OK_ = 201;

            if (response.getStatusLine().getStatusCode() != RESPONSE_OK_ && response.getStatusLine().getStatusCode() != RESPONSE_OK) {
                return "";
            } else
                return responseString.contains("fails") ? "" : responseString;
        } catch (Exception e) {
            return "";
        }

    }
}
