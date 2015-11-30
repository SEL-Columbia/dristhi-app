package org.ei.telemedicine;

import android.content.SharedPreferences;
import android.content.res.AssetManager;

import org.ei.telemedicine.util.IntegerUtil;

import java.io.IOException;
import java.util.Properties;

public class DristhiConfiguration {

    private static final String DRISHTI_BASE_URL = "DRISHTI_BASE_URL";
    private static final String DRISHTI_DJANGO_BASE_URL = "DRISHTI_DJANGO_BASE_URL";
    private static final String HOST = "HOST";
    private static final String PORT = "PORT";
    private static final String SHOULD_VERIFY_CERTIFICATE = "SHOULD_VERIFY_CERTIFICATE";
    private static final String SYNC_DOWNLOAD_BATCH_SIZE = "SYNC_DOWNLOAD_BATCH_SIZE";

    private Properties properties = new Properties();

    public DristhiConfiguration(AssetManager assetManager) {
        try {
            properties.load(assetManager.open("app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String get(String key) {
        return properties.getProperty(key);
    }

    public String host() {
        return this.get(HOST);
    }

    public int port() {
        return Integer.parseInt(this.get(PORT));
    }

    public boolean shouldVerifyCertificate() {
        return Boolean.parseBoolean(this.get(SHOULD_VERIFY_CERTIFICATE));
    }

    public String dristhiBaseURL() {
        return this.get(DRISHTI_BASE_URL);
    }

    public String dristhiDjangoBaseURL() {
        return this.get(DRISHTI_DJANGO_BASE_URL);
    }

    public int syncDownloadBatchSize() {
        return IntegerUtil.tryParse(this.get(SYNC_DOWNLOAD_BATCH_SIZE), 100);
    }

}
