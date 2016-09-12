package org.ei.opensrp.indonesia.kb;

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

/**
 * Created by Iq on 07/09/16.
 */
public class KBDetailActivity extends Activity {

    //image retrieving
    private static final String TAG = "ImageGridFragment";
    private static final String IMAGE_CACHE_DIR = "thumbs";
    //  private static KmsCalc  kmsCalc;
    private static int mImageThumbSize;
    private static int mImageThumbSpacing;
    private static String showbgm;
    private static ImageFetcher mImageFetcher;

    //image retrieving

    public static CommonPersonObjectClient kiclient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = Context.getInstance();
        setContentView(R.layout.kb_detail_activity);

        final ImageView kiview = (ImageView)findViewById(R.id.motherdetailprofileview);
        //header
        TextView today = (TextView) findViewById(R.id.detail_today);
        
        //profile
        TextView nama = (TextView) findViewById(R.id.txt_wife_name);
        TextView nik = (TextView) findViewById(R.id.txt_nik);
        TextView husband_name = (TextView) findViewById(R.id.txt_husband_name);
        TextView dob = (TextView) findViewById(R.id.txt_dob);
        TextView phone = (TextView) findViewById(R.id.txt_contact_phone_number);
        TextView risk1 = (TextView) findViewById(R.id.txt_risk1);
        TextView risk2 = (TextView) findViewById(R.id.txt_risk2);
        TextView risk3 = (TextView) findViewById(R.id.txt_risk3);
        TextView risk4 = (TextView) findViewById(R.id.txt_risk4);
        
        
        //detail data
        TextView village = (TextView) findViewById(R.id.txt_village_name);
        TextView subvillage = (TextView) findViewById(R.id.txt_subvillage);
        TextView age = (TextView) findViewById(R.id.txt_age);
        TextView alamat = (TextView) findViewById(R.id.txt_alamat);
        TextView education = (TextView) findViewById(R.id.txt_edu);
        TextView religion = (TextView) findViewById(R.id.txt_agama);
        TextView job = (TextView) findViewById(R.id.txt_job);
        TextView gakin = (TextView) findViewById(R.id.txt_gakin);
        TextView blood_type = (TextView) findViewById(R.id.txt_blood);
        TextView asuransi = (TextView) findViewById(R.id.txt_asuransi);

        TextView jenisKontrasepsi = (TextView) findViewById(R.id.txt_jenisKontrasepsi);
        TextView td_diastolik = (TextView) findViewById(R.id.txt_td_diastolik);
        TextView tdSistolik = (TextView) findViewById(R.id.txt_tdSistolik);
        TextView alkilila  = (TextView) findViewById(R.id.txt_alkilila);
        TextView alkiPenyakitIms = (TextView) findViewById(R.id.txt_alkiPenyakitIms);
        TextView keteranganTentangPesertaKB   = (TextView) findViewById(R.id.txt_keteranganTentangPesertaKB);
        TextView keteranganTentangPesertaKB2 = (TextView) findViewById(R.id.txt_keteranganTentangPesertaKB2);
        TextView alkiPenyakitKronis = (TextView) findViewById(R.id.txt_alkiPenyakitKronis);
        TextView alkihb = (TextView) findViewById(R.id.txt_alkihb);
        TextView keteranganGantiCara = (TextView) findViewById(R.id.txt_keteranganGantiCara);
        ImageButton back = (ImageButton) findViewById(org.ei.opensrp.R.id.btn_back_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(KBDetailActivity.this, NativeKBSmartRegisterActivity.class));
                overridePendingTransition(0, 0);
            }
        });


        if(kiclient.getDetails().get("profilepic")!= null){
            setImagetoHolderFromUri(KBDetailActivity.this, kiclient.getDetails().get("profilepic"), kiview, R.mipmap.woman_placeholder);
        }


        Date currentDateandTime = new Date();
        today.setText(" "+currentDateandTime);
       
        nama.setText("Nama : "+ (kiclient.getColumnmaps().get("namalengkap") != null ? kiclient.getColumnmaps().get("namalengkap") : "-"));
        nik.setText("NIK : "+ (kiclient.getDetails().get("nik") != null ? kiclient.getDetails().get("nik") : "-"));
        husband_name.setText("Nama Suami : "+ (kiclient.getDetails().get("namaSuami") != null ? kiclient.getDetails().get("namaSuami") : "-"));
        dob.setText("Tanggal Lahir : "+ (kiclient.getDetails().get("tanggalLahir") != null ? kiclient.getDetails().get("tanggalLahir") : "-"));
        phone.setText("No HP : "+ (kiclient.getDetails().get("NomorTelponHp") != null ? kiclient.getDetails().get("NomorTelponHp") : "-"));

        //risk
        if(kiclient.getDetails().get("highRiskPregnancyYoungMaternalAge") != null){
            risk1.setText("Ibu Terlalu Muda ");
        }
        if(kiclient.getDetails().get("highRiskPregnancyOldMaternalAge") != null){
            risk1.setText("Ibu terlalu tua ");
        }
        if(kiclient.getDetails().get("highRiskPregnancyProteinEnergyMalnutrition") != null){
            risk2.setText("Kekurangan Energi Kronis ");
        }
        if(kiclient.getDetails().get("HighRiskPregnancyAbortus") != null){
            risk3.setText("Riwayat Abortus ");
            if(kiclient.getDetails().get("HighRiskLabourSectionCesareaRecord") != null){
                risk3.setText("Riwayat Abortus, Riwayat Cesar ");
            }
        }
        if(kiclient.getDetails().get("HighRiskPregnancyAbortus") != null){
            risk4.setText("Riwayat Abortus ");
        }



        village.setText( (kiclient.getDetails().get("desa") != null ? kiclient.getDetails().get("desa") : "-"));
        subvillage.setText( (kiclient.getDetails().get("dusun") != null ? kiclient.getDetails().get("dusun") : "-"));
        age.setText((kiclient.getDetails().get("umur") != null ? kiclient.getDetails().get("umur") : "-"));
        alamat.setText((kiclient.getDetails().get("alamatDomisili") != null ? kiclient.getDetails().get("alamatDomisili") : "-"));
        education.setText((kiclient.getDetails().get("pendidikan") != null ? kiclient.getDetails().get("pendidikan") : "-"));
        religion.setText((kiclient.getDetails().get("agama") != null ? kiclient.getDetails().get("agama") : "-"));
        job.setText((kiclient.getDetails().get("pekerjaan") != null ? kiclient.getDetails().get("pekerjaan") : "-"));
        gakin.setText((kiclient.getDetails().get("gakinTidak") != null ? kiclient.getDetails().get("gakinTidak") : "-"));
        blood_type.setText((kiclient.getDetails().get("golonganDarah") != null ? kiclient.getDetails().get("golonganDarah") : "-"));
        asuransi.setText((kiclient.getDetails().get("jamkesmas") != null ? kiclient.getDetails().get("jamkesmas") : "-"));




        jenisKontrasepsi.setText( (kiclient.getDetails().get("jenisKontrasepsi") != null ? kiclient.getDetails().get("jenisKontrasepsi") : "-"));
        alkihb.setText( (kiclient.getDetails().get("alkihb") != null ? kiclient.getDetails().get("alkihb") : "-"));
        tdSistolik.setText( (kiclient.getDetails().get("tdDiastolik") != null ? kiclient.getDetails().get("tdDiastolik") : "-"));
        td_diastolik.setText( (kiclient.getDetails().get("tdDiastolik") != null ? kiclient.getDetails().get("tdDiastolik") : "-"));
        alkilila.setText((kiclient.getDetails().get("alkilila") != null ? kiclient.getDetails().get("alkilila") : "-"));
        alkiPenyakitIms.setText((kiclient.getDetails().get("alkiPenyakitIms") != null ? kiclient.getDetails().get("alkiPenyakitIms") : "-"));
        keteranganTentangPesertaKB.setText((kiclient.getDetails().get("keteranganTentangPesertaKB") != null ? kiclient.getDetails().get("keteranganTentangPesertaKB") : "-"));
        keteranganTentangPesertaKB2.setText((kiclient.getDetails().get("keterangantentangPesertaKB2") != null ? kiclient.getDetails().get("keterangantentangPesertaKB2") : "-"));
        alkiPenyakitKronis.setText((kiclient.getDetails().get("alkiPenyakitKronis") != null ? kiclient.getDetails().get("alkiPenyakitKronis") : "-"));
        keteranganGantiCara.setText((kiclient.getDetails().get("keteranganGantiCara") != null ? kiclient.getDetails().get("keteranganGantiCara") : "-"));

        /*
        kiview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bindobject = "kartu_ibu";
                entityid = kiclient.entityId();
                dispatchTakePictureIntent(kiview);

            }
        }); */

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
//                kiclient.entityId();
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
