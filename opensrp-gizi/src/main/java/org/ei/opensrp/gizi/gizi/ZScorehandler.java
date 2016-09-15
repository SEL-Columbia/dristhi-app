package org.ei.opensrp.gizi.gizi;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.gizi.R;
import org.ei.opensrp.service.formSubmissionHandler.FormSubmissionHandler;

import java.util.HashMap;
import java.util.List;

import util.ZScore.ZScoreSystemCalculation;

/**
 * Created by Iq on 15/09/16.
 */
public class ZScorehandler implements FormSubmissionHandler {
    static String bindobject = "anak";
    public ZScorehandler() {

    }

    @Override
    public void handle(FormSubmission submission) {
        String entityID = submission.entityId();
        AllCommonsRepository childRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("anak");
        CommonPersonObject childobject = childRepository.findByCaseID(entityID);
        /**
         * Z-SCORE calculation
         * NOTE - Need a better way to handle z-score data to sqllite
         */
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
        else{



        }

    }
}
