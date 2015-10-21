package org.ei.opensrp.util;


import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by raihan on 5/25/15.
 */
public class FileUtilities {
    private Writer writer;
    private String absolutePath;
//    private final Context context;

    public FileUtilities() {
        super();
//        this.context = context;
    }

    public void write(String fileName, String data) {
        File root = Environment.getExternalStorageDirectory();
        File outDir = new File(root.getAbsolutePath() + File.separator + "EZ_time_tracker");
        if (!outDir.isDirectory()) {
            outDir.mkdir();
        }
        try {
            if (!outDir.isDirectory()) {
                throw new IOException(
                        "Unable to create directory EZ_time_tracker. Maybe the SD card is mounted?");
            }
            File outputFile = new File(outDir, fileName);
            writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(data);
//            Toast.makeText(context.getApplicationContext(),
//                    "Report successfully saved to: " + outputFile.getAbsolutePath(),
//                    Toast.LENGTH_LONG).show();
            writer.close();
        } catch (IOException e) {
            Log.w("eztt", e.getMessage(), e);
//            Toast.makeText(context, e.getMessage() + " Unable to write to external storage.",
//                    Toast.LENGTH_LONG).show();
        }

    }

    public Writer getWriter() {
        return writer;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

}
