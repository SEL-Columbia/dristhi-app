package org.ei.drishti.view.controller;

import android.app.Activity;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.view.activity.EligibleCoupleViewActivity;
import org.ei.drishti.view.domain.EC;

import java.util.List;

public class EligibleCoupleListViewContext {
    private List<EC> ecList;
    private Activity eligibleCoupleListActivity;

    public EligibleCoupleListViewContext(List<EC> ecList, Activity eligibleCoupleListActivity) {
        this.ecList = ecList;
        this.eligibleCoupleListActivity = eligibleCoupleListActivity;
    }

    public String get() {
        return new Gson().toJson(ecList);
    }

    public void startEC(String caseId) {
        Intent intent = new Intent(eligibleCoupleListActivity.getApplicationContext(), EligibleCoupleViewActivity.class);
        intent.putExtra("caseId", caseId);
        eligibleCoupleListActivity.startActivity(intent);
    }
}
