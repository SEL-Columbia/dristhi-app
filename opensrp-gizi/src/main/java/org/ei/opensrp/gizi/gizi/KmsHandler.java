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
         * kms calculation
         * NOTE - Need a better way to handle z-score data to sqllite
         */
        String berats = childobject.getDetails().get("history_berat")!= null ? childobject.getDetails().get("history_berat") :"0";
        String[] history_berat = berats.split(",");
        double berat_sebelum = Double.parseDouble((history_berat.length) >=3 ? (history_berat[(history_berat.length)-3]) : "0");
        String umurs = childobject.getDetails().get("history_umur")!= null ? childobject.getDetails().get("history_umur") :"0";
        String[] history_umur = umurs.split(",");

        boolean jenisKelamin = childobject.getDetails().get("jenisKelamin").equalsIgnoreCase("laki-laki") || childobject.getDetails().get("jenisKelamin").equalsIgnoreCase("male")? true:false;
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

            if(childobject.getDetails().get("vitA") != null){
                if(childobject.getDetails().get("vitA").equalsIgnoreCase("yes") || childobject.getDetails().get("vitA").equalsIgnoreCase("ya")){
                    kms.put("lastVitA",childobject.getDetails().get("tanggalPenimbangan"));
                }
            }else{
                kms.put("lastVitA",childobject.getDetails().get("lastVitA"));
            }
            if(childobject.getDetails().get("obatcacing") != null){
                if(childobject.getDetails().get("obatcacing").equalsIgnoreCase("yes") || childobject.getDetails().get("obatcacing").equalsIgnoreCase("ya")){
                    kms.put("lastAnthelmintic",childobject.getDetails().get("tanggalPenimbangan"));
                }
            }else{
                kms.put("lastAnthelmintic",childobject.getDetails().get("lastAnthelmintic"));
            }
            org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects(bindobject).mergeDetails(entityID,kms);
        }

        if(childobject.getDetails().get("tanggalPenimbangan") != null)
        {
            String gender = childobject.getDetails().get("jenisKelamin") != null ? childobject.getDetails().get("jenisKelamin") : "-";
            String dateOfBirth = childobject.getDetails().get("tanggalLahir") != null ? childobject.getDetails().get("tanggalLahir") : "-";
            String lastVisitDate = childobject.getDetails().get("tanggalPenimbangan") != null ? childobject.getDetails().get("tanggalPenimbangan") : "-";
            double weight=Double.parseDouble(childobject.getDetails().get("beratBadan")!=null?childobject.getDetails().get("beratBadan"):"0");
            double length=Double.parseDouble(childobject.getDetails().get("tinggiBadan")!=null?childobject.getDetails().get("tinggiBadan"):"0");
            ZScoreSystemCalculation zScore = new ZScoreSystemCalculation();

            double weight_for_age = zScore.countWFA(gender, dateOfBirth, lastVisitDate, weight);
            String wfaStatus = zScore.getWFAZScoreClassification(weight_for_age);
            if(length != 0) {
                double heigh_for_age = zScore.countHFA(gender, dateOfBirth, lastVisitDate, length);
                String hfaStatus = zScore.getHFAZScoreClassification(heigh_for_age);

                double wight_for_lenght = 0.0;
                String wflStatus = "";
                if (zScore.dailyUnitCalculationOf(dateOfBirth, lastVisitDate) < 730) {
                    wight_for_lenght = zScore.countWFL(gender, weight, length);
                } else {
                    wight_for_lenght = zScore.countWFH(gender, weight, length);
                }
                wflStatus = zScore.getWFLZScoreClassification(wight_for_lenght);
                HashMap<String,String> z_score = new HashMap<String,String>();
                z_score.put("underweight",wfaStatus);
                z_score.put("stunting",hfaStatus);
                z_score.put("wasting",wflStatus);
                org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects(bindobject).mergeDetails(entityID,z_score);
            }
            else {
                //   String hfaStatus = "-";
                //   String wflStatus ="-";
                HashMap<String, String> z_score = new HashMap<String, String>();
                z_score.put("underweight", wfaStatus);
                z_score.put("stunting", "-");
                z_score.put("wasting", "-");
                org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects(bindobject).mergeDetails(entityID, z_score);
            }
        }
    }
}
