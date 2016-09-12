package org.ei.opensrp.indonesia.anc;

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
import org.ei.opensrp.view.activity.NativeANCSmartRegisterActivity;

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
public class ANCDetailActivity extends Activity {

    //image retrieving
    private static final String TAG = "ImageGridFragment";
    private static final String IMAGE_CACHE_DIR = "thumbs";
    //  private static KmsCalc  kmsCalc;
    private static int mImageThumbSize;
    private static int mImageThumbSpacing;
    private static String showbgm;
    private static ImageFetcher mImageFetcher;

    //image retrieving

    public static CommonPersonObjectClient ancclient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = Context.getInstance();
        setContentView(R.layout.anc_detail_activity);

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
        TextView Keterangan_k1k4 = (TextView) findViewById(R.id.txt_Keterangan_k1k4);
        TextView ancDate = (TextView) findViewById(R.id.txt_ancDate);
        TextView tanggalHPHT = (TextView) findViewById(R.id.txt_tanggalHPHT);
        TextView usiaKlinis = (TextView) findViewById(R.id.txt_usiaKlinis);
        TextView trimesterKe = (TextView) findViewById(R.id.txt_trimesterKe);
        TextView kunjunganKe = (TextView) findViewById(R.id.txt_kunjunganKe);
        TextView ancKe = (TextView) findViewById(R.id.txt_ancKe);
        TextView bbKg = (TextView) findViewById(R.id.txt_bbKg);
        TextView tandaVitalTDSistolik = (TextView) findViewById(R.id.txt_tandaVitalTDSistolik);
        TextView tandaVitalTDDiastolik = (TextView) findViewById(R.id.txt_tandaVitalTDDiastolik);
        TextView hasilPemeriksaanLILA = (TextView) findViewById(R.id.txt_hasilPemeriksaanLILA);
        TextView statusGiziibu = (TextView) findViewById(R.id.txt_statusGiziibu);
        TextView tfu = (TextView) findViewById(R.id.txt_tfu);
        TextView refleksPatelaIbu = (TextView) findViewById(R.id.txt_refleksPatelaIbu);
        TextView djj = (TextView) findViewById(R.id.txt_djj);
        TextView kepalaJaninTerhadapPAP = (TextView) findViewById(R.id.txt_kepalaJaninTerhadapPAP);
        TextView taksiranBeratJanin = (TextView) findViewById(R.id.txt_taksiranBeratJanin);
        TextView persentasiJanin = (TextView) findViewById(R.id.txt_persentasiJanin);
        TextView jumlahJanin = (TextView) findViewById(R.id.txt_jumlahJanin);


        TextView statusImunisasitt = (TextView) findViewById(R.id.txt_statusImunisasitt);
        TextView pelayananfe = (TextView) findViewById(R.id.txt_pelayananfe);
        TextView komplikasidalamKehamilan = (TextView) findViewById(R.id.txt_komplikasidalamKehamilan);

        TextView integrasiProgrampmtctvct = (TextView) findViewById(R.id.txt_integrasiProgrampmtctvct);
        TextView integrasiProgrampmtctPeriksaDarah = (TextView) findViewById(R.id.txt_integrasiProgrampmtctPeriksaDarah);
        TextView integrasiProgrampmtctSerologi = (TextView) findViewById(R.id.txt_integrasiProgrampmtctSerologi);
        TextView integrasiProgrampmtctarvProfilaksis = (TextView) findViewById(R.id.txt_integrasiProgrampmtctarvProfilaksis);
        TextView integrasiProgramMalariaPeriksaDarah = (TextView) findViewById(R.id.txt_integrasiProgramMalariaPeriksaDarah);
        TextView integrasiProgramMalariaObat = (TextView) findViewById(R.id.txt_integrasiProgramMalariaObat);
        TextView integrasiProgramMalariaKelambuBerinsektisida = (TextView) findViewById(R.id.txt_integrasiProgramMalariaKelambuBerinsektisida);
        TextView integrasiProgramtbDahak = (TextView) findViewById(R.id.txt_integrasiProgramtbDahak);
        TextView integrasiProgramtbObat = (TextView) findViewById(R.id.txt_integrasiProgramtbObat);

        TextView laboratoriumPeriksaHbHasil = (TextView) findViewById(R.id.txt_laboratoriumPeriksaHbHasil);
        TextView laboratoriumPeriksaHbAnemia = (TextView) findViewById(R.id.txt_laboratoriumPeriksaHbAnemia);
        TextView laboratoriumProteinUria = (TextView) findViewById(R.id.txt_laboratoriumProteinUria);
        TextView laboratoriumGulaDarah = (TextView) findViewById(R.id.txt_laboratoriumGulaDarah);
        TextView laboratoriumThalasemia = (TextView) findViewById(R.id.txt_laboratoriumThalasemia);
        TextView laboratoriumSifilis = (TextView) findViewById(R.id.txt_laboratoriumSifilis);
        TextView laboratoriumHbsAg = (TextView) findViewById(R.id.txt_laboratoriumHbsAg);



        ImageButton back = (ImageButton) findViewById(org.ei.opensrp.R.id.btn_back_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ANCDetailActivity.this, NativeKIANCSmartRegisterActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        Keterangan_k1k4.setText( (ancclient.getDetails().get("Keterangan_k1k4") != null ? ancclient.getDetails().get("Keterangan_k1k4") : "-"));
        tanggalHPHT.setText((ancclient.getDetails().get("tanggalHPHT") != null ? ancclient.getDetails().get("tanggalHPHT") : "-"));
        usiaKlinis.setText((ancclient.getDetails().get("usiaKlinis") != null ? ancclient.getDetails().get("usiaKlinis") : "-"));
        trimesterKe.setText((ancclient.getDetails().get("trimesterKe") != null ? ancclient.getDetails().get("trimesterKe") : "-"));
        kunjunganKe.setText((ancclient.getDetails().get("kunjunganKe") != null ? ancclient.getDetails().get("kunjunganKe") : "-"));
        bbKg.setText((ancclient.getDetails().get("bbKg") != null ? ancclient.getDetails().get("bbKg") : "-"));
        tandaVitalTDSistolik.setText((ancclient.getDetails().get("tandaVitalTDSistolik") != null ? ancclient.getDetails().get("tandaVitalTDSistolik") : "-"));
        tandaVitalTDDiastolik.setText((ancclient.getDetails().get("tandaVitalTDDiastolik") != null ? ancclient.getDetails().get("tandaVitalTDDiastolik") : "-"));
        hasilPemeriksaanLILA.setText((ancclient.getDetails().get("hasilPemeriksaanLILA") != null ? ancclient.getDetails().get("hasilPemeriksaanLILA") : "-"));
        statusGiziibu.setText((ancclient.getDetails().get("statusGiziibu") != null ? ancclient.getDetails().get("statusGiziibu") : "-"));
        tfu.setText((ancclient.getDetails().get("tfu") != null ? ancclient.getDetails().get("tfu") : "-"));
        refleksPatelaIbu.setText((ancclient.getDetails().get("refleksPatelaIbu") != null ? ancclient.getDetails().get("refleksPatelaIbu") : "-"));
        djj.setText((ancclient.getDetails().get("djj") != null ? ancclient.getDetails().get("djj") : "-"));
        kepalaJaninTerhadapPAP.setText((ancclient.getDetails().get("kepalaJaninTerhadapPAP") != null ? ancclient.getDetails().get("kepalaJaninTerhadapPAP") : "-"));
        taksiranBeratJanin.setText((ancclient.getDetails().get("taksiranBeratJanin") != null ? ancclient.getDetails().get("taksiranBeratJanin") : "-"));
        persentasiJanin.setText((ancclient.getDetails().get("persentasiJanin") != null ? ancclient.getDetails().get("persentasiJanin") : "-"));
        jumlahJanin.setText((ancclient.getDetails().get("jumlahJanin") != null ? ancclient.getDetails().get("jumlahJanin") : "-"));


        statusImunisasitt.setText( (ancclient.getDetails().get("statusImunisasitt") != null ? ancclient.getDetails().get("statusImunisasitt") : "-"));
        pelayananfe.setText((ancclient.getDetails().get("pelayananfe") != null ? ancclient.getDetails().get("pelayananfe") : "-"));
        komplikasidalamKehamilan.setText((ancclient.getDetails().get("komplikasidalamKehamilan") != null ? ancclient.getDetails().get("komplikasidalamKehamilan") : "-"));
        integrasiProgrampmtctvct.setText((ancclient.getDetails().get("integrasiProgrampmtctvct") != null ? ancclient.getDetails().get("integrasiProgrampmtctvct") : "-"));
        integrasiProgrampmtctPeriksaDarah.setText((ancclient.getDetails().get("integrasiProgrampmtctPeriksaDarah") != null ? ancclient.getDetails().get("integrasiProgrampmtctPeriksaDarah") : "-"));
        integrasiProgrampmtctSerologi.setText((ancclient.getDetails().get("integrasiProgrampmtctSerologi") != null ? ancclient.getDetails().get("integrasiProgrampmtctSerologi") : "-"));
        integrasiProgrampmtctarvProfilaksis.setText((ancclient.getDetails().get("integrasiProgrampmtctarvProfilaksis") != null ? ancclient.getDetails().get("integrasiProgrampmtctarvProfilaksis") : "-"));
        integrasiProgramMalariaPeriksaDarah.setText((ancclient.getDetails().get("integrasiProgramMalariaPeriksaDarah") != null ? ancclient.getDetails().get("integrasiProgramMalariaPeriksaDarah") : "-"));
        integrasiProgramMalariaObat.setText((ancclient.getDetails().get("integrasiProgramMalariaObat") != null ? ancclient.getDetails().get("integrasiProgramMalariaObat") : "-"));
        integrasiProgramMalariaKelambuBerinsektisida.setText((ancclient.getDetails().get("integrasiProgramMalariaKelambuBerinsektisida") != null ? ancclient.getDetails().get("integrasiProgramMalariaKelambuBerinsektisida") : "-"));
        integrasiProgramtbDahak.setText((ancclient.getDetails().get("integrasiProgramtbDahak") != null ? ancclient.getDetails().get("integrasiProgramtbDahak") : "-"));
        integrasiProgramtbObat.setText((ancclient.getDetails().get("integrasiProgramtbObat") != null ? ancclient.getDetails().get("integrasiProgramtbObat") : "-"));
        laboratoriumPeriksaHbHasil.setText((ancclient.getDetails().get("laboratoriumPeriksaHbHasil") != null ? ancclient.getDetails().get("laboratoriumPeriksaHbHasil") : "-"));
        laboratoriumPeriksaHbAnemia.setText((ancclient.getDetails().get("laboratoriumPeriksaHbAnemia") != null ? ancclient.getDetails().get("laboratoriumPeriksaHbAnemia") : "-"));
        laboratoriumProteinUria.setText((ancclient.getDetails().get("laboratoriumProteinUria") != null ? ancclient.getDetails().get("laboratoriumProteinUria") : "-"));
        laboratoriumGulaDarah.setText((ancclient.getDetails().get("laboratoriumGulaDarah") != null ? ancclient.getDetails().get("laboratoriumGulaDarah") : "-"));
        laboratoriumThalasemia.setText((ancclient.getDetails().get("laboratoriumThalasemia") != null ? ancclient.getDetails().get("laboratoriumThalasemia") : "-"));
        laboratoriumSifilis.setText((ancclient.getDetails().get("laboratoriumSifilis") != null ? ancclient.getDetails().get("laboratoriumSifilis") : "-"));
        laboratoriumHbsAg.setText((ancclient.getDetails().get("laboratoriumHbsAg") != null ? ancclient.getDetails().get("laboratoriumHbsAg") : "-"));


        AllCommonsRepository kiRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("ibu");

        CommonPersonObject kiobject = kiRepository.findByCaseID(ancclient.entityId());

        AllCommonsRepository iburep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("kartu_ibu");

        final CommonPersonObject ibuparent = iburep.findByCaseID(kiobject.getColumnmaps().get("kartuIbuId"));
        ancKe.setText((kiobject.getColumnmaps().get("ancKe") != null ? kiobject.getColumnmaps().get("ancKe") : "-"));

        ancDate.setText( (kiobject.getColumnmaps().get("ancDate") != null ? kiobject.getColumnmaps().get("ancDate") : "-"));
        if(ibuparent.getDetails().get("profilepic")!= null){
                setImagetoHolderFromUri(ANCDetailActivity.this, ibuparent.getDetails().get("profilepic"), kiview, R.mipmap.woman_placeholder);
        }
        else {
                kiview.setImageDrawable(getResources().getDrawable(R.mipmap.woman_placeholder));
        }


        Date currentDateandTime = new Date();
        today.setText(" "+currentDateandTime);
       
        nama.setText("Nama : "+ (ibuparent.getColumnmaps().get("namalengkap") != null ? ibuparent.getColumnmaps().get("namalengkap") : "-"));
        nik.setText("NIK : "+ (ibuparent.getDetails().get("nik") != null ? ibuparent.getDetails().get("nik") : "-"));
        husband_name.setText("Nama Suami : "+ (ibuparent.getColumnmaps().get("namaSuami") != null ? ibuparent.getColumnmaps().get("namaSuami") : "-"));
        dob.setText("Tanggal Lahir : "+ (ibuparent.getDetails().get("tanggalLahir") != null ? ibuparent.getDetails().get("tanggalLahir") : "-"));
        phone.setText("No HP : "+ (ibuparent.getDetails().get("NomorTelponHp") != null ? ibuparent.getDetails().get("NomorTelponHp") : "-"));

        //risk
        if(ibuparent.getDetails().get("highRiskPregnancyYoungMaternalAge") != null){
            risk1.setText("Ibu Terlalu Muda ");
        }
        if(ibuparent.getDetails().get("highRiskPregnancyOldMaternalAge") != null){
            risk1.setText("Ibu terlalu tua ");
        }
        if(ibuparent.getDetails().get("highRiskPregnancyProteinEnergyMalnutrition") != null){
            risk2.setText("Kekurangan Energi Kronis ");
        }
        if(ibuparent.getDetails().get("HighRiskPregnancyAbortus") != null){
            risk3.setText("Riwayat Abortus ");
            if(ibuparent.getDetails().get("HighRiskLabourSectionCesareaRecord") != null){
                risk3.setText("Riwayat Abortus, Riwayat Cesar ");
            }
        }
        if(ibuparent.getDetails().get("HighRiskPregnancyAbortus") != null){
            risk4.setText("Riwayat Abortus ");
        }

/*

        village.setText( (ancclient.getDetails().get("desa") != null ? ancclient.getDetails().get("desa") : "-"));
        subvillage.setText( (ancclient.getDetails().get("dusun") != null ? ancclient.getDetails().get("dusun") : "-"));
        age.setText((ancclient.getDetails().get("umur") != null ? ancclient.getDetails().get("umur") : "-"));
        alamat.setText((ancclient.getDetails().get("alamatDomisili") != null ? ancclient.getDetails().get("alamatDomisili") : "-"));
        education.setText((ancclient.getDetails().get("pendidikan") != null ? ancclient.getDetails().get("pendidikan") : "-"));
        religion.setText((ancclient.getDetails().get("agama") != null ? ancclient.getDetails().get("agama") : "-"));
        job.setText((ancclient.getDetails().get("pekerjaan") != null ? ancclient.getDetails().get("pekerjaan") : "-"));
        gakin.setText((ancclient.getDetails().get("gakinTidak") != null ? ancclient.getDetails().get("gakinTidak") : "-"));
        blood_type.setText((ancclient.getDetails().get("golonganDarah") != null ? ancclient.getDetails().get("golonganDarah") : "-"));
        asuransi.setText((ancclient.getDetails().get("jamkesmas") != null ? ancclient.getDetails().get("jamkesmas") : "-"));



        kiview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bindobject = "kartu_ibu";
                entityid = ancclient.entityId();
                dispatchTakePictureIntent(kiview);

            }
        });
 */
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
//                ancclient.entityId();
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
