package org.ei.opensrp.gizi.gizi;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.service.formSubmissionHandler.FormSubmissionHandler;

import java.util.HashMap;

import util.KMS.KmsCalc;
import util.KMS.KmsPerson;
import util.ZScore.ZScoreSystemCalculation;

/**
 * Created by Iq on 15/09/16.
 */
public class KmsHandler implements FormSubmissionHandler {
    static String bindobject = "anak";
    public KmsHandler() {

    }

    @Override
    public void handle(FormSubmission submission){
        String entityID = submission.entityId();
        AllCommonsRepository childRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("anak");
        CommonPersonObject childobject = childRepository.findByCaseID(entityID);
        /**
         /**
         * kms calculation
         * NOTE - Need a better way to handle z-score data to sqllite
         */
        String berats = childobject.getDetails().get("history_berat")!= null ? childobject.getDetails().get("history_berat") :"0";
        String[] history_berat = berats.split(",");
        double berat_sebelum = Double.parseDouble((history_berat.length) >=3 ? (history_berat[(history_berat.length)-3]) : "0");
        String umurs = childobject.getDetails().get("history_umur")!= null ? childobject.getDetails().get("history_umur") :"0";
        String[] history_umur = umurs.split(",");

        boolean jenisKelamin = childobject.getDetails().get("jenisKelamin").equalsIgnoreCase("laki-laki")? true:false;
        String tanggal_lahir = childobject.getDetails().get("tanggalLahir") != null ? childobject.getDetails().get("tanggalLahir") : "0";
        double berat= Double.parseDouble(childobject.getDetails().get("beratBadan") != null ? childobject.getDetails().get("beratBadan") : "0");
        double beraSebelum = Double.parseDouble((history_berat.length) >=2 ? (history_berat[(history_berat.length)-2]) : "0");
        String tanggal = (childobject.getDetails().get("tanggalPenimbangan") != null ? childobject.getDetails().get("tanggalPenimbangan") : "0");

        String tanggal_sebelumnya = (childobject.getDetails().get("kunjunganSebelumnya") != null ? childobject.getDetails().get("kunjunganSebelumnya") : "0");

        if(childobject.getDetails().get("tanggalPenimbangan") != null) {
            //KMS calculation
            KmsPerson data = new KmsPerson(jenisKelamin, tanggal_lahir, berat, beraSebelum, tanggal, berat_sebelum, tanggal_sebelumnya);
            KmsCalc calculator = new KmsCalc();
            int satu = Integer.parseInt(history_umur[history_umur.length-2]);
            int dua = Integer.parseInt(history_umur[history_umur.length-1]);
            String duat = history_berat.length <= 2  ? "-" : dua - satu >=2 ? "-" :calculator.cek2T(data);
            String status = history_berat.length <= 2 ? "Tidak" : calculator.cekWeightStatus(data);
            HashMap <String,String> kms = new HashMap<String,String>();
            kms.put("bgm",calculator.cekBGM(data));
            kms.put("dua_t",duat);
            kms.put("garis_kuning",calculator.cekBawahKuning(data));
            kms.put("nutrition_status",status);
            org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects(bindobject).mergeDetails(entityID,kms);


        }
    }
}
