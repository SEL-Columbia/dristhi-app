package org.ei.drishti.util;

import android.util.Log;

import org.apache.http.util.ByteArrayBuffer;
import org.ei.drishti.service.FormPathService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Dimas Ciputra on 3/21/15.
 */
public class DownloadForm {

    public static String DownloadFromURL(String downloadURL, String fileName) {

        HttpURLConnection connection = null;

        try {

            File dir = new File(FormPathService.sdcardPathDownload);

            if(!dir.exists()) {
                dir.mkdirs();
            }

            URL url = new URL(downloadURL);
            File file = new File(dir, fileName);

            long startTime = System.currentTimeMillis();
            Log.d("DownloadFormService", "download begin");
            Log.d("DownloadFormService", "download url: " + url);
            Log.d("DownloadFormService", "download file name: " + fileName);

            /* Open connection to URL */
            URLConnection uc = url.openConnection();

            /* expect HTTP 200 OK */
            if (((HttpURLConnection)uc).getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + ((HttpURLConnection)uc).getResponseCode()
                        + " " + ((HttpURLConnection)uc).getResponseMessage();
            }

            /* Define InputStreams to read from the URLConnections */
            InputStream is = uc.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            /* This will be for count download percentage */
            int fileLength = uc.getContentLength();

            Log.d("DownloadFormService", "file length : " + fileLength);

            /* Read bytes to the Buffer until there is nothing more to read */
            ByteArrayBuffer baf = new ByteArrayBuffer(9999);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            /* Convert the bytes to String */
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baf.toByteArray());
            fos.flush();
            fos.close();

            Log.d("DownloadFormService", "download finished in " + ((System.currentTimeMillis()-startTime) / 1000) + " sec");

        } catch (IOException e) {
            Log.d("DownloadFormService", "download error : " + e);
            return e.toString();
        }

        return null;
    }

}