package org.ei.opensrp.indonesia.child;

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
import org.ei.opensrp.domain.ProfileImage;
import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.indonesia.kartu_ibu.NativeKISmartRegisterActivity;
import org.ei.opensrp.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import util.ImageCache;
import util.ImageFetcher;

import static org.ei.opensrp.util.StringUtil.humanize;
import static org.ei.opensrp.util.StringUtil.humanizeAndDoUPPERCASE;
/**
 * Created by Iq on 07/09/16.
 */
public class AnakDetailActivity extends Activity {

    //image retrieving
    private static final String TAG = "ImageGridFragment";
    private static final String IMAGE_CACHE_DIR = "thumbs";
    //  private static KmsCalc  kmsCalc;
    private static int mImageThumbSize;
    private static int mImageThumbSpacing;
    private static String showbgm;
    private static ImageFetcher mImageFetcher;

    //image retrieving

    public static CommonPersonObjectClient childclient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = Context.getInstance();
        setContentView(R.layout.child_detail_activity);

        final ImageView childview = (ImageView)findViewById(R.id.childdetailprofileview);
        //header
        TextView today = (TextView) findViewById(R.id.detail_today);
        
        //profile
        TextView nama = (TextView) findViewById(R.id.txt_child_name);
        TextView mother = (TextView) findViewById(R.id.txt_mother_name);
        TextView father = (TextView) findViewById(R.id.txt_father_number);
        TextView dob = (TextView) findViewById(R.id.txt_dob);
        
      //  TextView phone = (TextView) findViewById(R.id.txt_contact_phone_number);
        TextView risk1 = (TextView) findViewById(R.id.txt_risk1);
        TextView risk2 = (TextView) findViewById(R.id.txt_risk2);
        TextView risk3 = (TextView) findViewById(R.id.txt_risk3);
        TextView risk4 = (TextView) findViewById(R.id.txt_risk4);
        
        
        //detail data
        TextView txt_noBayi = (TextView) findViewById(R.id.txt_noBayi);
        TextView txt_jenisKelamin = (TextView) findViewById(R.id.txt_jenisKelamin);
        TextView txt_beratLahir = (TextView) findViewById(R.id.txt_beratLahir);
        TextView tinggi = (TextView) findViewById(R.id.txt_hasilPengukuranTinggiBayihasilPengukuranTinggiBayi);
        TextView berat = (TextView) findViewById(R.id.txt_indikatorBeratBedanBayi);
        TextView asi = (TextView) findViewById(R.id.txt_pemberianAsiEksklusif);
        TextView status_gizi = (TextView) findViewById(R.id.txt_statusGizi);
        TextView kpsp = (TextView) findViewById(R.id.txt_hasilDilakukannyaKPSP);
        TextView vita = (TextView) findViewById(R.id.txt_pelayananVita);
        TextView hb0 = (TextView) findViewById(R.id.txt_tanggalpemberianimunisasiHb07);
        TextView pol1 = (TextView) findViewById(R.id.txt_tanggalpemberianimunisasiBCGdanPolio1);
        TextView pol2 = (TextView) findViewById(R.id.txt_tanggalpemberianimunisasiDPTHB1Polio2);
        TextView pol3 = (TextView) findViewById(R.id.txt_tanggalpemberianimunisasiDPTHB2Polio3);
        TextView pol4 = (TextView) findViewById(R.id.txt_tanggalpemberianimunisasiDPTHB3Polio4);
        TextView campak = (TextView) findViewById(R.id.txt_tanggalpemberianimunisasiCampak);



        ImageButton back = (ImageButton) findViewById(org.ei.opensrp.R.id.btn_back_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AnakDetailActivity.this, NativeKIAnakSmartRegisterActivity.class));
                overridePendingTransition(0, 0);
            }
        });


        if(childclient.getDetails().get("profilepic")!= null){
                setImagetoHolderFromUri(AnakDetailActivity.this, childclient.getDetails().get("profilepic"), childview, R.drawable.child_boy_infant);
        }
        else {
            if(childclient.getDetails().get("jenisKelamin").equals("laki")) {
                childview.setImageDrawable(getResources().getDrawable(R.drawable.child_boy_infant));
            }
            childview.setImageDrawable(getResources().getDrawable(R.drawable.child_girl_infant));

        }


       // Date currentDateandTime = new Date();
     //   today.setText(" "+currentDateandTime);


        AllCommonsRepository childRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("anak");

        CommonPersonObject childobject = childRepository.findByCaseID(childclient.entityId());

        AllCommonsRepository iburep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("ibu");
        final CommonPersonObject ibuparent = iburep.findByCaseID(childobject.getColumnmaps().get("ibuCaseId"));
        
        AllCommonsRepository kirep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("kartu_ibu");
        final CommonPersonObject kiparent = kirep.findByCaseID(ibuparent.getColumnmaps().get("kartuIbuId"));

        
        nama.setText(getResources().getString(R.string.name)+humanize (childclient.getColumnmaps().get("namaBayi") != null ? childclient.getColumnmaps().get("namaBayi") : "-"));
        mother.setText(getResources().getString(R.string.child_details_mothers_name_label)+humanize (kiparent.getColumnmaps().get("namalengkap") != null ? kiparent.getColumnmaps().get("namalengkap") : "-"));
        father.setText(getResources().getString(R.string.child_details_fathers_name_label)+ humanize(kiparent.getColumnmaps().get("namaSuami") != null ? kiparent.getColumnmaps().get("namaSuami") : "-"));
        dob.setText(getResources().getString(R.string.date_of_birth)+ humanize(childclient.getColumnmaps().get("tanggalLahirAnak") != null ? childclient.getColumnmaps().get("tanggalLahirAnak") : "-"));
        

        txt_noBayi.setText( ": "+humanize (childclient.getDetails().get("noBayi") != null ? childclient.getDetails().get("noBayi") : "-"));
        txt_jenisKelamin.setText(": "+ humanize (childclient.getDetails().get("jenisKelamin") != null ? childclient.getDetails().get("jenisKelamin") : "-"));
        txt_beratLahir.setText(": "+humanize (childclient.getDetails().get("beratLahir") != null ? childclient.getDetails().get("beratLahir") : "-"));
        tinggi.setText(": "+ humanize(childclient.getDetails().get("hasilPengukuranTinggiBayihasilPengukuranTinggiBayi") != null ? childclient.getDetails().get("hasilPengukuranTinggiBayihasilPengukuranTinggiBayi") : "-"));
        berat.setText(": "+humanize (childclient.getDetails().get("indikatorBeratBedanBayi") != null ? childclient.getDetails().get("indikatorBeratBedanBayi") : "-"));
        asi.setText(": "+humanize (childclient.getDetails().get("pemberianAsiEksklusif") != null ? childclient.getDetails().get("pemberianAsiEksklusif") : "-"));
        status_gizi.setText(": "+ humanize(childclient.getDetails().get("statusGizi") != null ? childclient.getDetails().get("statusGizi") : "-"));
        kpsp.setText(": "+ humanize(childclient.getDetails().get("hasilDilakukannyaKPSP") != null ? childclient.getDetails().get("hasilDilakukannyaKPSP") : "-"));
        hb0.setText(": "+ humanize(childclient.getDetails().get("tanggalpemberianimunisasiHb07") != null ? childclient.getDetails().get("tanggalpemberianimunisasiHb07") : "-"));
        pol1.setText(": "+ humanize(childclient.getDetails().get("tanggalpemberianimunisasiBCGdanPolio1") != null ? childclient.getDetails().get("tanggalpemberianimunisasiBCGdanPolio1") : "-"));
        pol2.setText(": "+ humanize(childclient.getDetails().get("tanggalpemberianimunisasiDPTHB1Polio2") != null ? childclient.getDetails().get("tanggalpemberianimunisasiDPTHB1Polio2") : "-"));
        pol3.setText(": "+ humanize(childclient.getDetails().get("tanggalpemberianimunisasiDPTHB2Polio3") != null ? childclient.getDetails().get("tanggalpemberianimunisasiDPTHB2Polio3") : "-"));
        pol4.setText(": "+ humanize(childclient.getDetails().get("tanggalpemberianimunisasiDPTHB3Polio4") != null ? childclient.getDetails().get("tanggalpemberianimunisasiDPTHB3Polio4") : "-"));
        campak.setText(": "+humanize (childclient.getDetails().get("tanggalpemberianimunisasiCampak") != null ? childclient.getDetails().get("tanggalpemberianimunisasiCampak") : "-"));
        vita.setText(": "+ humanize(childclient.getDetails().get("pelayananVita") != null ? childclient.getDetails().get("pelayananVita") : "-"));


        childview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bindobject = "anak";
                entityid = childclient.entityId();
                dispatchTakePictureIntent(childview);

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
        String anmId = Context.getInstance().allSharedPreferences().fetchRegisteredANM();
        ProfileImage profileImage = new ProfileImage(UUID.randomUUID().toString(),anmId,entityid,"Image",details.get("profilepic"), ImageRepository.TYPE_Unsynced,"dp");
        ((ImageRepository) Context.getInstance().imageRepository()).add(profileImage);
//                childclient.entityId();
//        Toast.makeText(this,entityid,Toast.LENGTH_LONG).show();
    }
    public static void setImagetoHolder(Activity activity, String file, ImageView view, int placeholder){
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
        startActivity(new Intent(this, NativeKISmartRegisterActivity.class));
        overridePendingTransition(0, 0);


    }
}
