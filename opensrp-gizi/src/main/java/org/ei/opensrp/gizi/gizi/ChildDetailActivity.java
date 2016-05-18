package org.ei.opensrp.gizi.gizi;

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

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.gizi.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import util.ImageCache;
import util.ImageFetcher;
import util.KmsCalculator;
import util.KmsPerson;

import static org.ei.opensrp.util.StringUtil.humanize;

/**
 * Created by Iq on 26/04/16.
 */
public class ChildDetailActivity extends Activity {

    //image retrieving
    private static final String TAG = "ImageGridFragment";
    private static final String IMAGE_CACHE_DIR = "thumbs";

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
        setContentView(R.layout.gizi_detail_activity);


        //header
        TextView header_name = (TextView) findViewById(R.id.header_name);


        //profile
        TextView nama = (TextView) findViewById(R.id.txt_profile_child_name);
        TextView mother_name = (TextView) findViewById(R.id.txt_profile_mother_name);
     //   TextView father_name = (TextView) findViewById(R.id.txt_profile_father_name);
        TextView posyandu = (TextView) findViewById(R.id.txt_profile_posyandu);
        TextView village_name = (TextView) findViewById(R.id.txt_profile_village_name);
        TextView birth_date = (TextView) findViewById(R.id.txt_profile_birth_date);
        TextView gender = (TextView) findViewById(R.id.txt_profile_child_gender);
        TextView weight = (TextView) findViewById(R.id.txt_profile_last_weight);
        TextView height = (TextView) findViewById(R.id.txt_profile_last_height);

        //child growth

        TextView nutrition_status = (TextView) findViewById(R.id.txt_profile_nutrition_status);
        TextView bgm = (TextView) findViewById(R.id.txt_profile_bgm);
        TextView dua_t = (TextView) findViewById(R.id.txt_profile_2t);
        TextView under_yellow_line = (TextView) findViewById(R.id.txt_profile_under_yellow_line);
        TextView breast_feeding = (TextView) findViewById(R.id.txt_profile_breastfeeding);


        ImageButton back = (ImageButton) findViewById(org.ei.opensrp.R.id.btn_back_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        nama.setText(R.string.child_profile);


        nama.setText(getString(R.string.child_name) +" "+ (childclient.getDetails().get("namaBayi") != null ? childclient.getDetails().get("namaBayi") : "-"));
        mother_name.setText(getString(R.string.parent_name) +" : "+ (childclient.getDetails().get("namaOrtu") != null ? childclient.getDetails().get("namaOrtu") : "-"));
     //   father_name.setText(getString(R.string.father_name) +" "+ (childclient.getDetails().get("namaOrtu") != null ? childclient.getDetails().get("namaOrtu") : "-"));
        posyandu.setText(getString(R.string.posyandu) +" "+ (childclient.getDetails().get("posyandu") != null ? childclient.getDetails().get("posyandu") : "-"));
        village_name.setText(getString(R.string.village) +" "+ (childclient.getDetails().get("desa") != null ? childclient.getDetails().get("desa") : "-"));
        birth_date.setText(getString(R.string.birth_date) +" "+ (childclient.getDetails().get("tanggalLahir") != null ? childclient.getDetails().get("tanggalLahir") : "-"));
        gender.setText(getString(R.string.gender) +" "+ (childclient.getDetails().get("jenisKelamin") != null ? childclient.getDetails().get("jenisKelamin") : "-"));
      //  weight.setText(getString(R.string.weight) +" "+ (childclient.getDetails().get("beratBadan") != null ? childclient.getDetails().get("beratBadan") : "-"));
      //  height.setText(getString(R.string.height) +" "+ (childclient.getDetails().get("tinggiBadan") != null ? childclient.getDetails().get("tinggiBadan") : "-"));


        nutrition_status.setText(getString(R.string.nutrition_status) +" "+ (childclient.getDetails().get("status_gizi") != null ? childclient.getDetails().get("status_gizi") : "-"));


        //KMS calculation
        boolean jenisKelamin = childclient.getDetails().get("jenisKelamin").equalsIgnoreCase("laki-laki")? true:false;
        int umur = Integer.parseInt(childclient.getDetails().get("umur") != null ? childclient.getDetails().get("umur") : "0");
        double berat= Double.parseDouble(childclient.getDetails().get("beratBadan") != null ? childclient.getDetails().get("beratBadan") : "0");
        double beraSebelum = Double.parseDouble(childclient.getDetails().get("beratSebelumnya") != null ? childclient.getDetails().get("beratSebelumnya") : "0");


        KmsPerson data = new KmsPerson(jenisKelamin, //boolean gender
                            umur,  //age(month)
                            berat, //curent weight
                            beraSebelum, //previous weight
                            childclient.getDetails().get("tanggalPenimbangan")!= null ? childclient.getDetails().get("tanggalPenimbangan") :"0"); // last visit date

        KmsCalculator calculator = new KmsCalculator();


        bgm.setText(getString(R.string.bgm) +calculator.cekBGM(data));
        dua_t.setText(getString(R.string.dua_t) +calculator.cek2T(data) );
        under_yellow_line.setText(getString(R.string.under_yellow_line)+calculator.cekBawahKuning(data));
        breast_feeding.setText(getString(R.string.asi) +" "+ (childclient.getDetails().get("asi_eksklusif") != null ? childclient.getDetails().get("asi_eksklusif") : "-"));

       // final ImageView householdview = (ImageView) findViewById(R.id.childprofileview);

      //  if (childclient.getDetails().get("profilepic") != null) {
       //     setImagetoHolder(ChildDetailActivity.this, childclient.getDetails().get("profilepic"), childview, R.mipmap.child_boy_infant);
      //  }
//        childview.setOnClickListener(new View.OnClickListener() {.
//            @Override
//            public void onClick(View v) {
//                bindobject = "anak";
//                entityid = childclient.entityId();
//                dispatchTakePictureIntent(childview);
//
//            }
//        });

        //data tinggi badan
        double data_tinggi[] = new double [13];
        data_tinggi[0] = 0;
        data_tinggi[1] = Double.parseDouble(childclient.getDetails().get("tinggiBadan1") != null ? childclient.getDetails().get("tinggiBadan1") : "0");
        data_tinggi[2] = Double.parseDouble(childclient.getDetails().get("tinggiBadan2") != null ? childclient.getDetails().get("tinggiBadan2") : "0");
        data_tinggi[3] = Double.parseDouble(childclient.getDetails().get("tinggiBadan3") != null ? childclient.getDetails().get("tinggiBadan3") : "0");
        data_tinggi[4] = Double.parseDouble(childclient.getDetails().get("tinggiBadan4") != null ? childclient.getDetails().get("tinggiBadan4") : "0");
        data_tinggi[5] = Double.parseDouble(childclient.getDetails().get("tinggiBadan5") != null ? childclient.getDetails().get("tinggiBadan5") : "0");
        data_tinggi[6] = Double.parseDouble(childclient.getDetails().get("tinggiBadan6") != null ? childclient.getDetails().get("tinggiBadan6") : "0");
        data_tinggi[7] = Double.parseDouble(childclient.getDetails().get("tinggiBadan7") != null ? childclient.getDetails().get("tinggiBadan7") : "0");
        data_tinggi[8] = Double.parseDouble(childclient.getDetails().get("tinggiBadan8") != null ? childclient.getDetails().get("tinggiBadan8") : "0");
        data_tinggi[9] = Double.parseDouble(childclient.getDetails().get("tinggiBadan9") != null ? childclient.getDetails().get("tinggiBadan9") : "0");
        data_tinggi[10] = Double.parseDouble(childclient.getDetails().get("tinggiBadan10") != null ? childclient.getDetails().get("tinggiBadan10") : "0");
        data_tinggi[11] = Double.parseDouble(childclient.getDetails().get("tinggiBadan11") != null ? childclient.getDetails().get("tinggiBadan11") : "0");
        data_tinggi[12] = Double.parseDouble(childclient.getDetails().get("tinggiBadan12") != null ? childclient.getDetails().get("tinggiBadan12") : "0");

        //data for graph
        double datas[] = new double [13];
        datas[0] = 0;
        datas[1] = Double.parseDouble(childclient.getDetails().get("beratBadan1") != null ? childclient.getDetails().get("beratBadan1") : "0");
        datas[2] = Double.parseDouble(childclient.getDetails().get("beratBadan2") != null ? childclient.getDetails().get("beratBadan2") : "0");
        datas[3] = Double.parseDouble(childclient.getDetails().get("beratBadan3") != null ? childclient.getDetails().get("beratBadan3") : "0");
        datas[4] = Double.parseDouble(childclient.getDetails().get("beratBadan4") != null ? childclient.getDetails().get("beratBadan4") : "0");
        datas[5] = Double.parseDouble(childclient.getDetails().get("beratBadan5") != null ? childclient.getDetails().get("beratBadan5") : "0");
        datas[6] = Double.parseDouble(childclient.getDetails().get("beratBadan6") != null ? childclient.getDetails().get("beratBadan6") : "0");
        datas[7] = Double.parseDouble(childclient.getDetails().get("beratBadan7") != null ? childclient.getDetails().get("beratBadan7") : "0");
        datas[8] = Double.parseDouble(childclient.getDetails().get("beratBadan8") != null ? childclient.getDetails().get("beratBadan8") : "0");
        datas[9] = Double.parseDouble(childclient.getDetails().get("beratBadan9") != null ? childclient.getDetails().get("beratBadan9") : "0");
        datas[10] = Double.parseDouble(childclient.getDetails().get("beratBadan10") != null ? childclient.getDetails().get("beratBadan10") : "0");
        datas[11] = Double.parseDouble(childclient.getDetails().get("beratBadan11") != null ? childclient.getDetails().get("beratBadan11") : "0");
        datas[12] = Double.parseDouble(childclient.getDetails().get("beratBadan12") != null ? childclient.getDetails().get("beratBadan12") : "0");


        //fungtion break if found 0 data.
        GraphView graph = (GraphView) findViewById(R.id.graph);
        int counter=0;
        for(int i=0;i<datas.length;i++){
            if(datas[i+1]==0)
                break;
            counter++;
        }

        //set current height and weight
        weight.setText(getString(R.string.weight) +" "+ datas[counter]+" Kg");
        height.setText(getString(R.string.height) +" "+ data_tinggi[counter]+" Cm");


        //set data for graph
        DataPoint dataPoint[] = new DataPoint[counter+1];

        for(int i=0;i<dataPoint.length;i++){
            dataPoint[i]= new DataPoint(i,datas[i]);

        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoint);




        //add series data into chart
        graph.addSeries(series);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(12);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX )+ " Month";
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + " Kg";
                }
            }

        });





    }


    // NOT USING PICTURE AT THE MOMENT
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
}
