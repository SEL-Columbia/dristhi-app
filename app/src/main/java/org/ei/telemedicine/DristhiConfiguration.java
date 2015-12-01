package org.ei.telemedicine;

import android.content.*;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;

import org.ei.telemedicine.util.IntegerUtil;
import org.ei.telemedicine.view.activity.LoginActivity;

import java.io.IOException;
import java.util.Properties;

public class DristhiConfiguration {

    private static final String DRISHTI_BASE_URL = "DRISHTI_BASE_URL";
    private static final String DRISHTI_DJANGO_BASE_URL = "DRISHTI_DJANGO_BASE_URL";
    private static final String HOST = "HOST";
    private static final String PORT = "PORT";
    private static final String SHOULD_VERIFY_CERTIFICATE = "SHOULD_VERIFY_CERTIFICATE";
    private static final String SYNC_DOWNLOAD_BATCH_SIZE = "SYNC_DOWNLOAD_BATCH_SIZE";
    private static final String DRISHTI_AUDIO_URL = "DRISHTI_AUDIO_URL";

    private Properties properties = new Properties();
    SharedPreferences preferences;

    public DristhiConfiguration(AssetManager assetManager, android.content.Context context) {
        try {
            properties.load(assetManager.open("app.properties"));
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
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

    public String drishtiAudioURL() {
        return this.get(DRISHTI_AUDIO_URL);
    }

    public String dristhiDjangoBaseURL() {
        return this.get(DRISHTI_DJANGO_BASE_URL);
    }

    public int syncDownloadBatchSize() {
        return IntegerUtil.tryParse(this.get(SYNC_DOWNLOAD_BATCH_SIZE), 100);
    }

}
