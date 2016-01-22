package org.ei.opensrp;

import android.content.res.AssetManager;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.ei.opensrp.repository.AllSharedPreferences;
import org.ei.opensrp.util.IntegerUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DristhiConfiguration {

    private static final String DRISHTI_BASE_URL = "DRISHTI_BASE_URL";
    private static final String HOST = "HOST";
    private static final String PORT = "PORT";
    private static final String SHOULD_VERIFY_CERTIFICATE = "SHOULD_VERIFY_CERTIFICATE";
    private static final String SYNC_DOWNLOAD_BATCH_SIZE = "SYNC_DOWNLOAD_BATCH_SIZE";
    private static final String APP_NAME = "APP_NAME";
    private static final String SYNC_FORM = "SYNC_FORM";
    public static AllSharedPreferences preferences;
    private Properties properties = new Properties();
    private String dummyData = null;

    public DristhiConfiguration(AssetManager assetManager) {
        preferences=Context.getInstance().allSharedPreferences();
        try {
            properties.load(assetManager.open("app.properties"));
            //InputStream dummyNameFile = assetManager.open("dummy_name.json");
            //dummyData = IOUtils.toString(dummyNameFile);
            //IOUtils.closeQuietly(dummyNameFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDummyData() { return this.dummyData; }

    private String get(String key) {
        return properties.getProperty(key);
    }

    public String host() {

        return this.get(HOST);

    }

    public int port() {


        return preferences.fetchPort(Integer.parseInt(this.get(PORT)));
    }

    public boolean shouldVerifyCertificate() {
        return Boolean.parseBoolean(this.get(SHOULD_VERIFY_CERTIFICATE));
    }

    public String dristhiBaseURL() {

        return preferences.fetchBaseURL(this.get(DRISHTI_BASE_URL));
    }

    public int syncDownloadBatchSize() {
        return IntegerUtil.tryParse(this.get(SYNC_DOWNLOAD_BATCH_SIZE), 100);
    }

    public String appName() {
        return this.get(APP_NAME) != null ? this.get(APP_NAME) : "";
    }

    public boolean shouldSyncForm() {
        return this.get(SYNC_FORM) != null ? Boolean.parseBoolean(this.get(SYNC_FORM)) : false;
    }
}
