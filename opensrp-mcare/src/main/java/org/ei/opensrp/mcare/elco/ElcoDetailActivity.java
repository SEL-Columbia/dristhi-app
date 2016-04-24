package org.ei.opensrp.mcare.elco;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.mcare.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import util.ImageCache;
import util.ImageFetcher;

import static org.ei.opensrp.util.StringUtil.humanize;

/**
 * Created by raihan on 5/11/15.
 */
public class ElcoDetailActivity extends Activity {

    //image retrieving
    private static final String TAG = "ImageGridFragment";
    private static final String IMAGE_CACHE_DIR = "thumbs";

    private static int mImageThumbSize;
    private static int mImageThumbSpacing;

    private static ImageFetcher mImageFetcher;




    //image retrieving

    public static CommonPersonObjectClient Elcoclient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = Context.getInstance();
        setContentView(R.layout.elco_detail_activity);
        TextView name = (TextView) findViewById(R.id.name);
        TextView brid = (TextView) findViewById(R.id.brid);
        TextView nid = (TextView) findViewById(R.id.womannid);

        TextView husbandname = (TextView) findViewById(R.id.husbandname);
        TextView age = (TextView) findViewById(R.id.age);
        TextView jivitahhid = (TextView) findViewById(R.id.jivitahhid);
        TextView godhhid = (TextView) findViewById(R.id.gobhhid);
        TextView village = (TextView) findViewById(R.id.village);
        TextView mw_reg_date = (TextView) findViewById(R.id.mw_reg_date);
        TextView psf_due_date = (TextView) findViewById(R.id.last_psf_date);







        ImageButton back = (ImageButton) findViewById(org.ei.opensrp.R.id.btn_back_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        name.setText(humanize((Elcoclient.getColumnmaps().get("FWWOMFNAME") != null ? Elcoclient.getColumnmaps().get("FWWOMFNAME") : "").replace("+", "_")));

        brid.setText(getString(R.string.BRID) +humanize((Elcoclient.getDetails().get("FWWOMBID") != null ? Elcoclient.getDetails().get("FWWOMBID") : "").replace("+", "_")));
        nid.setText(getString(R.string.NID) +humanize((Elcoclient.getDetails().get("FWWOMNID") != null ? Elcoclient.getDetails().get("FWWOMNID") : "").replace("+", "_")));

        husbandname.setText(getString(R.string.elco_details_husband_name_label)+(Elcoclient.getDetails().get("FWHUSNAME") != null ? Elcoclient.getDetails().get("FWHUSNAME") : ""));
        age.setText(getString(R.string.elco_age_label) + (Elcoclient.getDetails().get("FWWOMAGE") != null ? Elcoclient.getDetails().get("FWWOMAGE") : ""));
        jivitahhid.setText(getString(R.string.hhiid_jivita_elco_label)+(Elcoclient.getColumnmaps().get("JiVitAHHID") != null ? Elcoclient.getColumnmaps().get("JiVitAHHID") : ""));
        godhhid.setText(getString(R.string.hhid_gob_elco_label)+(Elcoclient.getColumnmaps().get("GOBHHID") != null ? Elcoclient.getColumnmaps().get("GOBHHID") : ""));
        psf_due_date.setText(Elcoclient.getDetails().get("FWPSRDATE") != null ? Elcoclient.getDetails().get("FWPSRDATE") : "");


        village.setText(humanize(Elcoclient.getDetails().get("location_name") != null ? Elcoclient.getDetails().get("location_name") : ""));
            /////from househld
        AllCommonsRepository allelcoRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("elco");
        CommonPersonObject elcoobject = allelcoRepository.findByCaseID(Elcoclient.entityId());
        AllCommonsRepository householdrep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("household");
        CommonPersonObject householdparent = householdrep.findByCaseID(elcoobject.getRelationalId());
        String location = "";
        if(householdparent.getDetails().get("existing_Mauzapara") != null) {
            location = householdparent.getDetails().get("existing_Mauzapara");
        }
        village.setText(getString(R.string.elco_details_mauza)+humanize(location.replace("+","_")));


        mw_reg_date.setText((Elcoclient.getDetails().get("WomanREGDATE") != null ? Elcoclient.getDetails().get("WomanREGDATE") : ""));
        ///////////////////////////////////////////////////


        final ImageView householdview = (ImageView) findViewById(R.id.householdprofileview);

        if (Elcoclient.getDetails().get("profilepic") != null) {
            setImagetoHolder(ElcoDetailActivity.this, Elcoclient.getDetails().get("profilepic"), householdview, R.mipmap.womanimageload);
        }
//        householdview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bindobject = "household";
//                entityid = Elcoclient.entityId();
//                dispatchTakePictureIntent(householdview);
//
//            }
//        });


    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    static final int REQUEST_TAKE_PHOTO = 1;
   static ImageView mImageView;
    static File currentfile;
    static String bindobject;
    static String entityid;
    private void dispatchTakePictureIntent(ImageView imageView) {
        mImageView = imageView;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                currentfile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            String imageBitmap = (String) extras.get(MediaStore.EXTRA_OUTPUT);
//            Toast.makeText(this,imageBitmap,Toast.LENGTH_LONG).show();
            HashMap <String,String> details = new HashMap<String,String>();
            details.put("profilepic",currentfile.getAbsolutePath());
            saveimagereference(bindobject,entityid,details);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(currentfile.getPath(), options);
            mImageView.setImageBitmap(bitmap);
        }
    }
    public void saveimagereference(String bindobject,String entityid,Map<String,String> details){
        Context.getInstance().allCommonsRepositoryobjects(bindobject).mergeDetails(entityid,details);
//                Elcoclient.entityId();
//        Toast.makeText(this,entityid,Toast.LENGTH_LONG).show();
    }
    public static void setImagetoHolder(Activity activity,String file, ImageView view, int placeholder){
        mImageThumbSize = 300;
        mImageThumbSpacing = Context.getInstance().applicationContext().getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);


        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(activity, IMAGE_CACHE_DIR);
             cacheParams.setMemCacheSizePercent(0.50f); // Set memory cache to 25% of app memory
        mImageFetcher = new ImageFetcher(activity, mImageThumbSize);
        mImageFetcher.setLoadingImage(placeholder);
        mImageFetcher.addImageCache(activity.getFragmentManager(), cacheParams);
//        Toast.makeText(activity,file,Toast.LENGTH_LONG).show();
        mImageFetcher.loadImage("file:///"+file,view);

//        Uri.parse(new File("/sdcard/cats.jpg")






//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(file, options);
//        view.setImageBitmap(bitmap);
    }
}
