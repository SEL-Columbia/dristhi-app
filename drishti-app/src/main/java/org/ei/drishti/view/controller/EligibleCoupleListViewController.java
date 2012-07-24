package org.ei.drishti.view.controller;

import android.app.Activity;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.view.activity.EligibleCoupleViewActivity;
import org.ei.drishti.view.contract.EC;

import java.util.ArrayList;
import java.util.List;

public class EligibleCoupleListViewController {
    private AllEligibleCouples allEligibleCouples;
    private Activity eligibleCoupleListActivity;

    public EligibleCoupleListViewController(AllEligibleCouples allEligibleCouples, Activity eligibleCoupleListActivity) {
        this.allEligibleCouples = allEligibleCouples;
        this.eligibleCoupleListActivity = eligibleCoupleListActivity;
    }

    public String get() {
        List<EligibleCouple> couples = allEligibleCouples.fetchAll();

        List<EC> ecList = new ArrayList<EC>();
        for (EligibleCouple couple : couples) {
            ecList.add(new EC(couple.caseId(), couple.wifeName(), couple.village(), couple.ecNumber(), false));
        }

        return new Gson().toJson(ecList);
    }

    public void startEC(String caseId) {
        Intent intent = new Intent(eligibleCoupleListActivity.getApplicationContext(), EligibleCoupleViewActivity.class);
        intent.putExtra("caseId", caseId);
        eligibleCoupleListActivity.startActivity(intent);
    }
}
