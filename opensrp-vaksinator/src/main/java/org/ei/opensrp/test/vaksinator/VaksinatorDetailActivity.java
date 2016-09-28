package org.ei.opensrp.test.vaksinator;

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
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.domain.ProfileImage;
import org.ei.opensrp.repository.ImageRepository;
import org.ei.opensrp.test.R;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import util.ImageCache;
import util.ImageFetcher;

/**
 * Created by Iq on 09/06/16.
 */
public class VaksinatorDetailActivity extends Activity {

    //image retrieving
    private static final String TAG = "ImageGridFragment";
    private static final String IMAGE_CACHE_DIR = "thumbs";
    //  private static KmsCalc  kmsCalc;

    private static int mImageThumbSize;
    private static int mImageThumbSpacing;
    private static String showbgm;
    private static ImageFetcher mImageFetcher;

    //image retrieving

    public static CommonPersonObjectClient controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = Context.getInstance();
        setContentView(R.layout.smart_register_jurim_detail_client);

        //profile
        TextView nama = (TextView) findViewById(R.id.childName);
        TextView motherName = (TextView) findViewById(R.id.motherName);
        TextView fatherName = (TextView) findViewById(R.id.fatherName);
        TextView posyandu = (TextView) findViewById(R.id.posyandu);
        TextView village = (TextView) findViewById(R.id.village);
        TextView subVillage = (TextView) findViewById(R.id.subvillage);
        TextView dateOfBirth = (TextView) findViewById(R.id.dateOfBirth);
        TextView birthWeight = (TextView) findViewById(R.id.birthWeight);
        TextView antipiretik = (TextView) findViewById(R.id.antypiretic);

        //vaccination date
        TextView hb1Under7 = (TextView) findViewById(R.id.hb1under7);
        TextView bcg = (TextView) findViewById(R.id.bcg);
        TextView pol1 = (TextView) findViewById(R.id.pol1);
        TextView dpt1 = (TextView) findViewById(R.id.dpt1);
        TextView pol2 = (TextView) findViewById(R.id.pol2);
        TextView dpt2 = (TextView) findViewById(R.id.dpt2);
        TextView pol3 = (TextView) findViewById(R.id.pol3);
        TextView dpt3 = (TextView) findViewById(R.id.dpt3);
        TextView pol4 = (TextView) findViewById(R.id.pol4);
        TextView ipv = (TextView) findViewById(R.id.ipv);
        TextView measles = (TextView) findViewById(R.id.measles);
        TextView complete = (TextView) findViewById(R.id.complete);
        TextView additionalDPT = (TextView) findViewById(R.id.additionalDPT);
        TextView additionalMeasles = (TextView) findViewById(R.id.additionalMeasles);
        final ImageView photo = (ImageView) findViewById(R.id.photo);

        ImageButton backButton = (ImageButton) findViewById(R.id.btn_back_to_home);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                FlurryFacade.logEvent("click_recapitulation_button");
                startActivity(new Intent(VaksinatorDetailActivity.this, VaksinatorSmartRegisterActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        TextView recapitulationLabel = (TextView)findViewById(R.id.recapitulation_label);
        recapitulationLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(VaksinatorDetailActivity.this, VaksinatorRecapitulationActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        nama.setText(": " + (controller.getDetails().get("nama_bayi") != null ? controller.getDetails().get("nama_bayi") : "-"));
        fatherName.setText(": " + (controller.getDetails().get("namaAyah") != null ? controller.getDetails().get("namaAyah") : "-"));
        motherName.setText(": " + (controller.getDetails().get("namaIbu") != null
                ? controller.getDetails().get("namaIbu")
                : controller.getDetails().get("nama_orang_tua")!=null
                        ? controller.getDetails().get("nama_orang_tua")
                        : "-"
            )
        );
        village.setText(": " + (controller.getDetails().get("desa") != null
                ? controller.getDetails().get("desa")
                : "-"));

        subVillage.setText(": " + (controller.getDetails().get("dusun") != null
                ? controller.getDetails().get("dusun")
                : controller.getDetails().get("village") != null
                    ? controller.getDetails().get("village")
                    : "-")
        );

        posyandu.setText(": " + (controller.getDetails().get("nama_lokasi") != null
                ? controller.getDetails().get("nama_lokasi")
                : controller.getDetails().get("posyandu")!=null
                    ? controller.getDetails().get("posyandu")
                    : "-"));
        dateOfBirth.setText(": " + (controller.getDetails().get("tanggal_lahir") != null ? controller.getDetails().get("tanggal_lahir") : "-"));
        birthWeight.setText(": " + (controller.getDetails().get("berat_badan_saat_lahir") != null ? controller.getDetails().get("berat_badan_saat_lahir") : "-"));
        antipiretik.setText(": " + (controller.getDetails().get("getAntypiretic") != null ? controller.getDetails().get("getAntypiretic") : "-"));

        hb1Under7.setText(": " + (hasDate(controller,"hb0")
                ? age(controller.getDetails().get("tanggal_lahir"),controller.getDetails().get("hb0")) <= 7
                    ? controller.getDetails().get("hb0")
                    : "-"
                : hasDate(controller,"hb1_kurang_7_hari")
                    ? controller.getDetails().get("hb1_kurang_7_hari")
                    :"-"));

        bcg.setText(": " + (controller.getDetails().get("bcg") != null ? controller.getDetails().get("bcg") : "-"));
        pol1.setText(": " + (controller.getDetails().get("polio1") != null ? controller.getDetails().get("polio1") : "-"));
        dpt1.setText(": " + (controller.getDetails().get("dpt_hb1") != null ? controller.getDetails().get("dpt_hb1") : "-"));
        pol2.setText(": " + (controller.getDetails().get("polio2") != null ? controller.getDetails().get("polio2") : "-"));
        dpt2.setText(": " + (controller.getDetails().get("dpt_hb2") != null ? controller.getDetails().get("dpt_hb2") : "-"));
        pol3.setText(": " + (controller.getDetails().get("polio3") != null ? controller.getDetails().get("polio3") : "-"));
        dpt3.setText(": " + (controller.getDetails().get("dpt_hb3") != null ? controller.getDetails().get("dpt_hb3") : "-"));
        pol4.setText(": " + (controller.getDetails().get("polio4") != null ? controller.getDetails().get("polio4") : "-"));
        ipv.setText(": " + (controller.getDetails().get("ipv") != null ? controller.getDetails().get("ipv") : "-"));
        measles.setText(": " + (controller.getDetails().get("imunisasi_campak") != null ? controller.getDetails().get("imunisasi_campak") : "-"));

        complete.setText(": " + (controller.getDetails().get("imunisasi_lengkap") != null ? controller.getDetails().get("imunisasi_lengkap") : "-"));
        additionalDPT.setText(": " + (controller.getDetails().get("dpt_hb_campak_lanjutan") != null ? controller.getDetails().get("dpt_hb_campak_lanjutan") : "-"));
        additionalMeasles.setText(": " + (controller.getDetails().get("dpt_hb_campak_lanjutan") != null ? controller.getDetails().get("dpt_hb_campak_lanjutan") : "-"));

        if(controller.getDetails().get("profilepic")!= null){
            if((controller.getDetails().get("jenisKelamin")!=null?controller.getDetails().get("jenisKelamin"):"").equalsIgnoreCase("female")) {
                setImagetoHolderFromUri(VaksinatorDetailActivity.this, controller.getDetails().get("profilepic"), photo, R.drawable.child_boy_infant);
            } else if ((controller.getDetails().get("jenisKelamin")!=null?controller.getDetails().get("jenisKelamin"):"").equalsIgnoreCase("male")){
                setImagetoHolderFromUri(VaksinatorDetailActivity.this, controller.getDetails().get("profilepic"), photo, R.drawable.child_boy_infant);

            }
        }
        else {


            if (controller.getDetails().get("jenis_kelamin").contains("em")) {
                photo.setImageResource(R.drawable.child_girl_infant);
            } else {
                photo.setImageResource(R.drawable.child_boy_infant);
            }
        }

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bindobject = "anak";
                entityid = controller.entityId();
                dispatchTakePictureIntent(photo);

            }
        });
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
            HashMap<String,String> details = new HashMap<String,String>();
            details.put("profilepic", currentfile.getAbsolutePath());
            saveimagereference(bindobject,entityid,details);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(currentfile.getPath(), options);
            mImageView.setImageBitmap(bitmap);
        }
    }
    public void saveimagereference(String bindobject,String entityid,Map<String,String> details){
        Context.getInstance().allCommonsRepositoryobjects(bindobject).mergeDetails(entityid,details);
        String anmId = Context.getInstance().allSharedPreferences().fetchRegisteredANM();
        ProfileImage profileImage = new ProfileImage(UUID.randomUUID().toString(),anmId,entityid,"Image",details.get("profilepic"), ImageRepository.TYPE_Unsynced,"dp");
        ((ImageRepository) Context.getInstance().imageRepository()).add(profileImage);
//                childclient.entityId();
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
    public static void setImagetoHolderFromUri(Activity activity,String file, ImageView view, int placeholder){
        view.setImageDrawable(activity.getResources().getDrawable(placeholder));
        File externalFile = new File(file);
        Uri external = Uri.fromFile(externalFile);
        view.setImageURI(external);


    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, VaksinatorSmartRegisterActivity.class));
        overridePendingTransition(0, 0);


    }

    /*
    * Used to check if the variable contains a date (10 character which representing yyyy-MM-dd) or not
    * params:
    * CommonPersonObjectClient pc
    * String variable
    *
    * return:
    * true - if the variable contains date
    * false - if the variable null or less than 10 character length
    * */
    private boolean hasDate(CommonPersonObjectClient pc, String variable){
        return pc.getDetails().get(variable)!=null && pc.getDetails().get(variable).length()==10;
    }

    private int age(String date1, String date2){
        return (Integer.parseInt(date2.substring(0,3)) - Integer.parseInt(date1.substring(0,3)))*360
                + (Integer.parseInt(date2.substring(5,7)) - Integer.parseInt(date1.substring(5,7)))*30
                + (Integer.parseInt(date2.substring(8)) - Integer.parseInt(date1.substring(8)));
    }
}