package org.ei.drishti.view.controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import org.ei.drishti.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.ei.drishti.view.activity.SecuredWebActivity.TAKE_PHOTO_REQUEST_CODE;

public class CameraController {

    private final Activity context;

    public CameraController(Activity activity) {
        this.context = activity;
    }

    public void takePhoto() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imageFile = null;
        try {
            imageFile = createImageFile();
        } catch (IOException e) {
            Log.logError("Could not create temp file for storing image. Not taking photo.");
            return;
        }
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        context.startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST_CODE);
    }

    private File createImageFile() throws IOException {
        String imageFileName = UUID.randomUUID().toString();
        File directory = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                ),
                "Dristhi"
        );
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                directory
        );
        return image;
    }
}
