package org.ei.opensrp.indonesia.pnc;

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
import org.ei.opensrp.indonesia.anc.NativeKIANCSmartRegisterActivity;
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
public class PNCDetailActivity extends Activity {

    //image retrieving
    private static final String TAG = "ImageGridFragment";
    private static final String IMAGE_CACHE_DIR = "thumbs";
    //  private static KmsCalc  kmsCalc;
    private static int mImageThumbSize;
    private static int mImageThumbSpacing;
    private static String showbgm;
    private static ImageFetcher mImageFetcher;

    //image retrieving

    public static CommonPersonObjectClient pncclient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = Context.getInstance();
        setContentView(R.layout.pnc_detail_activity);

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
        TextView txt_keadaanIbu = (TextView) findViewById(R.id.txt_keadaanIbu);
        TextView txt_keadaanBayi = (TextView) findViewById(R.id.txt_keadaanBayi);
        TextView txt_beratLahir = (TextView) findViewById(R.id.txt_beratLahir);
        TextView txt_persalinan = (TextView) findViewById(R.id.txt_persalinan);
        TextView txt_jamKalaIAktif = (TextView) findViewById(R.id.txt_jamKalaIAktif);
        TextView txt_jamKalaII = (TextView) findViewById(R.id.txt_jamKalaII);
        TextView txt_jamPlasentaLahir = (TextView) findViewById(R.id.txt_jamPlasentaLahir);
        TextView txt_perdarahanKalaIV2JamPostpartum = (TextView) findViewById(R.id.txt_perdarahanKalaIV2JamPostpartum);
        TextView txt_persentasi = (TextView) findViewById(R.id.txt_persentasi);
        TextView txt_tempatBersalin = (TextView) findViewById(R.id.txt_tempatBersalin);
        TextView txt_penolong = (TextView) findViewById(R.id.txt_penolong);
        TextView txt_caraPersalinanIbu = (TextView) findViewById(R.id.txt_caraPersalinanIbu);
        TextView txt_namaBayi = (TextView) findViewById(R.id.txt_namaBayi);
        TextView txt_jenisKelamin = (TextView) findViewById(R.id.txt_jenisKelamin);

        TextView txt_tanggalLahirAnak = (TextView) findViewById(R.id.txt_tanggalLahirAnak);
        TextView txt_hariKeKF = (TextView) findViewById(R.id.txt_hariKeKF);
        TextView txt_tandaVitalTDDiastolik = (TextView) findViewById(R.id.txt_tandaVitalTDDiastolik);
        TextView txt_tandaVitalTDSistolik = (TextView) findViewById(R.id.txt_tandaVitalTDSistolik);
        TextView txt_tandaVitalSuhu = (TextView) findViewById(R.id.txt_tandaVitalSuhu);
        TextView txt_pelayananfe = (TextView) findViewById(R.id.txt_pelayananfe);
        TextView txt_vitaminA2jamPP = (TextView) findViewById(R.id.txt_vitaminA2jamPP);
        TextView txt_vitaminA24jamPP = (TextView) findViewById(R.id.txt_vitaminA24jamPP);
        TextView txt_integrasiProgramAntiMalaria = (TextView) findViewById(R.id.txt_integrasiProgramAntiMalaria);
        TextView txt_integrasiProgramantitb = (TextView) findViewById(R.id.txt_integrasiProgramantitb);

        TextView txt_integrasiProgramFotoThorax = (TextView) findViewById(R.id.txt_integrasiProgramFotoThorax);
        TextView txt_komplikasi = (TextView) findViewById(R.id.txt_komplikasi);
        TextView txt_daruratNifas = (TextView) findViewById(R.id.txt_daruratNifas);
        TextView txt_penangananNifas = (TextView) findViewById(R.id.txt_penangananNifas);





        ImageButton back = (ImageButton) findViewById(org.ei.opensrp.R.id.btn_back_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PNCDetailActivity.this, NativeKIPNCSmartRegisterActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        txt_keadaanIbu.setText( (pncclient.getDetails().get("keadaanIbu") != null ? pncclient.getDetails().get("keadaanIbu") : "-"));
        txt_keadaanBayi.setText((pncclient.getDetails().get("keadaanBayi") != null ? pncclient.getDetails().get("keadaanBayi") : "-"));
        txt_beratLahir.setText((pncclient.getDetails().get("beratLahir") != null ? pncclient.getDetails().get("beratLahir") : "-"));
        txt_persalinan.setText((pncclient.getDetails().get("persalinan") != null ? pncclient.getDetails().get("persalinan") : "-"));
        txt_jamKalaIAktif.setText((pncclient.getDetails().get("jamKalaIAktif") != null ? pncclient.getDetails().get("jamKalaIAktif") : "-"));
        txt_jamKalaII.setText((pncclient.getDetails().get("jamKalaII") != null ? pncclient.getDetails().get("jamKalaII") : "-"));
        txt_jamPlasentaLahir.setText((pncclient.getDetails().get("jamPlasentaLahir") != null ? pncclient.getDetails().get("jamPlasentaLahir") : "-"));
        txt_perdarahanKalaIV2JamPostpartum.setText((pncclient.getDetails().get("perdarahanKalaIV2JamPostpartum") != null ? pncclient.getDetails().get("perdarahanKalaIV2JamPostpartum") : "-"));
        txt_persentasi.setText((pncclient.getDetails().get("persentasi") != null ? pncclient.getDetails().get("persentasi") : "-"));
        txt_tempatBersalin.setText((pncclient.getDetails().get("tempatBersalin") != null ? pncclient.getDetails().get("tempatBersalin") : "-"));
        txt_penolong.setText((pncclient.getDetails().get("penolong") != null ? pncclient.getDetails().get("penolong") : "-"));
        txt_caraPersalinanIbu.setText((pncclient.getDetails().get("caraPersalinanIbu") != null ? pncclient.getDetails().get("caraPersalinanIbu") : "-"));
        txt_namaBayi.setText((pncclient.getDetails().get("namaBayi") != null ? pncclient.getDetails().get("namaBayi") : "-"));
        txt_jenisKelamin.setText((pncclient.getDetails().get("jenisKelamin") != null ? pncclient.getDetails().get("jenisKelamin") : "-"));
        txt_tanggalLahirAnak.setText((pncclient.getDetails().get("tanggalLahirAnak") != null ? pncclient.getDetails().get("tanggalLahirAnak") : "-"));

        txt_tandaVitalTDDiastolik.setText((pncclient.getDetails().get("tandaVitalTDDiastolik") != null ? pncclient.getDetails().get("tandaVitalTDDiastolik") : "-"));
        txt_tandaVitalTDSistolik.setText((pncclient.getDetails().get("tandaVitalTDSistolik") != null ? pncclient.getDetails().get("tandaVitalTDSistolik") : "-"));
        txt_tandaVitalSuhu.setText((pncclient.getDetails().get("tandaVitalSuhu") != null ? pncclient.getDetails().get("tandaVitalSuhu") : "-"));
        txt_pelayananfe.setText((pncclient.getDetails().get("pelayananfe") != null ? pncclient.getDetails().get("pelayananfe") : "-"));
        txt_vitaminA2jamPP.setText((pncclient.getDetails().get("vitaminA2jamPP") != null ? pncclient.getDetails().get("vitaminA2jamPP") : "-"));
        txt_vitaminA24jamPP.setText((pncclient.getDetails().get("vitaminA24jamPP") != null ? pncclient.getDetails().get("vitaminA24jamPP") : "-"));
        txt_integrasiProgramAntiMalaria.setText((pncclient.getDetails().get("integrasiProgramAntiMalaria") != null ? pncclient.getDetails().get("integrasiProgramAntiMalaria") : "-"));
        txt_integrasiProgramantitb.setText((pncclient.getDetails().get("integrasiProgramantitb") != null ? pncclient.getDetails().get("integrasiProgramantitb") : "-"));


        txt_integrasiProgramFotoThorax.setText((pncclient.getDetails().get("integrasiProgramFotoThorax") != null ? pncclient.getDetails().get("integrasiProgramFotoThorax") : "-"));
        txt_komplikasi.setText((pncclient.getDetails().get("komplikasi") != null ? pncclient.getDetails().get("komplikasi") : "-"));
        txt_daruratNifas.setText((pncclient.getDetails().get("daruratNifas") != null ? pncclient.getDetails().get("daruratNifas") : "-"));
        txt_penangananNifas.setText((pncclient.getDetails().get("penangananNifas") != null ? pncclient.getDetails().get("penangananNifas") : "-"));



        AllCommonsRepository kiRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("ibu");

        CommonPersonObject kiobject = kiRepository.findByCaseID(pncclient.entityId());

        AllCommonsRepository iburep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("kartu_ibu");

        final CommonPersonObject ibuparent = iburep.findByCaseID(kiobject.getColumnmaps().get("kartuIbuId"));
        txt_hariKeKF.setText((kiobject.getColumnmaps().get("hariKeKF") != null ? kiobject.getColumnmaps().get("hariKeKF") : "-"));

        if(ibuparent.getDetails().get("profilepic")!= null){
            setImagetoHolderFromUri(PNCDetailActivity.this, ibuparent.getDetails().get("profilepic"), kiview, R.mipmap.woman_placeholder);
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


 */
    }


    public static void setImagetoHolderFromUri(Activity activity,String file, ImageView view, int placeholder){
        view.setImageDrawable(activity.getResources().getDrawable(placeholder));
        File externalFile = new File(file);
        Uri external = Uri.fromFile(externalFile);
        view.setImageURI(external);


    }

}
