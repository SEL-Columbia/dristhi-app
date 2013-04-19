package org.ei.drishti.service;

import android.content.res.AssetManager;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static java.text.MessageFormat.format;
import static org.ei.drishti.util.Log.logError;

public class ZiggyFileLoader {
    private String jsDirectoryName;
    private String formDirectoryName;
    private AssetManager assetManager;

    public ZiggyFileLoader(String jsDirectoryName, String formDirectoryName, AssetManager assetManager) {
        this.jsDirectoryName = jsDirectoryName;
        this.formDirectoryName = formDirectoryName;
        this.assetManager = assetManager;
    }

    public String getJSFiles() throws IOException, URISyntaxException {
        StringBuilder builder = new StringBuilder();
        String[] fileNames = assetManager.list(jsDirectoryName);
        for (String fileName : fileNames) {
            if (fileName.endsWith(".js")) {
                builder.append(IOUtils.toString(assetManager.open(jsDirectoryName + "/" + fileName), "UTF-8"));
            }
        }
        return builder.toString();
    }

    public String loadAppData(String fileName) {
        try {
            return IOUtils.toString(assetManager.open(formDirectoryName + "/" + fileName), "UTF-8");
        } catch (IOException e) {
            logError(format("Error while loading app data file: {0}, with exception: {1}", fileName, e));
        }
        return null;
    }
}
