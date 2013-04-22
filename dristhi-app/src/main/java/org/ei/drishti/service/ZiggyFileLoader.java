package org.ei.drishti.service;

import android.content.res.AssetManager;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static java.text.MessageFormat.format;
import static org.ei.drishti.util.Log.logError;

public class ZiggyFileLoader {
    private String ziggyDirectoryPath;
    private String formDirectoryPath;
    private AssetManager assetManager;

    public ZiggyFileLoader(String ziggyDirectoryPath, String formDirectoryPath, AssetManager assetManager) {
        this.ziggyDirectoryPath = ziggyDirectoryPath;
        this.formDirectoryPath = formDirectoryPath;
        this.assetManager = assetManager;
    }

    public String getJSFiles() throws IOException, URISyntaxException {
        StringBuilder builder = new StringBuilder();
        String[] fileNames = assetManager.list(ziggyDirectoryPath);
        for (String fileName : fileNames) {
            if (fileName.endsWith(".js")) {
                builder.append(IOUtils.toString(assetManager.open(ziggyDirectoryPath + "/" + fileName), "UTF-8"));
            }
        }
        return builder.toString();
    }

    public String loadAppData(String fileName) {
        try {
            return IOUtils.toString(assetManager.open(formDirectoryPath + "/" + fileName), "UTF-8");
        } catch (IOException e) {
            logError(format("Error while loading app data file: {0}, with exception: {1}", fileName, e));
        }
        return null;
    }
}
