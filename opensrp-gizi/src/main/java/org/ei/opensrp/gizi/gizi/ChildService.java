package org.ei.opensrp.gizi.gizi;

import android.content.ContentValues;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.repository.AllBeneficiaries;
import org.ei.opensrp.repository.AllTimelineEvents;
import org.ei.opensrp.service.AlertService;

import java.util.HashMap;

import util.ZScore.ZScoreSystemCalculation;

/**
 * Created by Iq on 21/11/16.
 */
public class ChildService {

    private AllBeneficiaries allBeneficiaries;
    private AllTimelineEvents allTimelines;
    private AllCommonsRepository allCommonsRepository;
    private AlertService alertService;

    public ChildService(AllBeneficiaries allBeneficiaries,
                        AllTimelineEvents allTimelines, AllCommonsRepository allCommonsRepository, AlertService alertService) {
        this.allBeneficiaries = allBeneficiaries;
        this.alertService = alertService;
        this.allTimelines = allTimelines;
        this.allCommonsRepository = allCommonsRepository;

    }

    public void followup(FormSubmission submission) {
        String entityID = submission.entityId();
        AllCommonsRepository childRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("anak");
        CommonPersonObject childobject = childRepository.findByCaseID(entityID);

        /**
         * kms calculation
         * NOTE - Need a better way to handle z-score data to sqllite
         */
        String berats = submission.getFieldValue("history_berat")!= null ? submission.getFieldValue("history_berat") :"0";
        String[] history_berat = berats.split(",");
        double berat_sebelum = Double.parseDouble((history_berat.length) >=3 ? (history_berat[(history_berat.length)-3]) : "0");
        String umurs = submission.getFieldValue("history_umur")!= null ? submission.getFieldValue("history_umur") :"0";
        String[] history_umur = umurs.split(",");



        if(submission.getFieldValue("tanggalPenimbangan") != null)
        {
            String gender = childobject.getDetails().get("gender") != null ? submission.getFieldValue("gender") : "-";
            String dateOfBirth = submission.getFieldValue("tanggalLahirAnak") != null ? submission.getFieldValue("tanggalLahirAnak") : "-";
            String lastVisitDate = submission.getFieldValue("tanggalPenimbangan") != null ? submission.getFieldValue("tanggalPenimbangan") : "-";
            double weight=Double.parseDouble(submission.getFieldValue("beratBadan")!=null?submission.getFieldValue("beratBadan"):"0");
            double length=Double.parseDouble(submission.getFieldValue("tinggiBadan")!=null?submission.getFieldValue("tinggiBadan"):"0");
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

                z_score.put("preload_umur",umurs);
                z_score.put("berat_preload",berats);
                z_score.put("kunjunganSebelumnya",lastVisitDate);



                //   org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects(bindobject).mergeDetails(entityID,z_score);

               /* ContentValues contentValues = new ContentValues();
                for (String key : z_score.keySet()) {

                    if (z_score.get(key).equalsIgnoreCase("")) {
                        continue;
                    } else {
                        contentValues.put(key, z_score.get(key));
                        //  key
                        //alertService.changeAlertStatusToComplete(submission.entityId(), key.replace("_", " "));

                    }
                }

                allCommonsRepository.update("ec_details", contentValues, submission.entityId());*/

            }
            else {
                //   String hfaStatus = "-";
                //   String wflStatus ="-";
                HashMap<String, String> z_score = new HashMap<String, String>();
                z_score.put("underweight", wfaStatus);
                z_score.put("stunting", "-");
                z_score.put("wasting", "-");
              //  org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects(bindobject).mergeDetails(entityID, z_score);
            }
        }

    }

}
