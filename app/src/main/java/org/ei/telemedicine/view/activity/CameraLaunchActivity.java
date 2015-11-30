package org.ei.telemedicine.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.event.CapturedPhotoInformation;
import org.ei.telemedicine.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static org.ei.telemedicine.AllConstants.CHILD_TYPE;
import static org.ei.telemedicine.AllConstants.ENTITY_ID;
import static org.ei.telemedicine.AllConstants.WOMAN_TYPE;
import static org.ei.telemedicine.event.Event.ON_PHOTO_CAPTURED;

public class CameraLaunchActivity extends SecuredActivity {
    private static final int TAKE_PHOTO_REQUEST_CODE = 111;
    private static final String JPG_FILE_SUFFIX = ".jpg";
    private static final String DRISTHI_DIRECTORY_NAME = "Dristhi";
    private File imageFile;
    private String entityType;
    private String entityId;

    @Override
    protected void onCreation() {
        Intent intent = getIntent();
        entityId = intent.getStringExtra(ENTITY_ID);
        entityType = intent.getStringExtra(AllConstants.TYPE);
        startCamera();
    }

    @Override
    protected void onResumption() {
    }

    public void startCamera() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            imageFile = createImageFile();
        } catch (IOException e) {
            Log.logError("Could not create temp file for storing image. Not taking photo. " + e);
            return;
        }
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST_CODE);

    }

    private File createImageFile() throws IOException {
        String imageFileName = UUID.randomUUID().toString();
        File directory = new File(getExternalStoragePublicDirectory(DIRECTORY_PICTURES), DRISTHI_DIRECTORY_NAME);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return new File(directory, imageFileName + JPG_FILE_SUFFIX);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != TAKE_PHOTO_REQUEST_CODE)
            return;
        if (imageFile.exists()) {
            String imageFilePath = imageFile.getAbsolutePath();
            setPic(imageFilePath);
            updateEntity("file://" + imageFilePath);
            Toast.makeText(this, "Photo captured", Toast.LENGTH_SHORT).show();
        }
        super.onBackPressed();
    }

    private void setPic(String mCurrentPhotoPath) {
        int targetWidth = 100;
        int targetHeight = 100;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bitmapOptions);
        int originalWidth = bitmapOptions.outWidth;
        int originalHeight = bitmapOptions.outHeight;

        int scaleFactor = Math.min(originalWidth / targetWidth, originalHeight / targetHeight);
        bitmapOptions.inJustDecodeBounds = false;
        bitmapOptions.inSampleSize = scaleFactor;
        bitmapOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bitmapOptions);
        saveBitmap(bitmap, mCurrentPhotoPath);
    }

    private void updateEntity(String imagePath) {
        if (WOMAN_TYPE.equals(entityType)) {
            context.allEligibleCouples().updatePhotoPath(entityId, imagePath);
        }
        if(CHILD_TYPE.equals(entityType)) {
            context.childService().updatePhotoPath(entityId, imagePath);
        }
        ON_PHOTO_CAPTURED.notifyListeners(new CapturedPhotoInformation(entityId, imagePath));
    }

    private void saveBitmap(Bitmap bitmap, String location) {
        try {
            FileOutputStream out = new FileOutputStream(location);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.logError("Could not save resized image. " + e);
        }
    }
}
