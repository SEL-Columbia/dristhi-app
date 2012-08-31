package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.view.activity.EligibleCoupleDetailActivity;
import org.ei.drishti.view.contract.EC;

import java.util.*;

public class EligibleCoupleListViewController {
    private AllEligibleCouples allEligibleCouples;
    private Context context;
    private CommCareClientService commCareClientService;

    public EligibleCoupleListViewController(AllEligibleCouples allEligibleCouples, Context context, CommCareClientService commCareClientService) {
        this.allEligibleCouples = allEligibleCouples;
        this.context = context;
        this.commCareClientService = commCareClientService;
    }

    public String get() {
        List<EligibleCouple> couples = allEligibleCouples.all();

        List<EC> ecList = new ArrayList<EC>();
        for (EligibleCouple couple : couples) {
            ecList.add(new EC(couple.caseId(), couple.wifeName(), couple.village(), couple.ecNumber(), false));
        }
        Collections.sort(ecList, new Comparator<EC>() {
            @Override
            public int compare(EC oneEC, EC anotherEC) {
                return oneEC.wifeName().compareToIgnoreCase(anotherEC.wifeName());
            }
        });
        return new Gson().toJson(ecList);
    }

    public void startCommCare(String formId) {
        commCareClientService.start(context, formId, "");
    }

    public void startEC(String caseId) {
        Intent intent = new Intent(context.getApplicationContext(), EligibleCoupleDetailActivity.class);
        intent.putExtra("caseId", caseId);
        context.startActivity(intent);
    }
}
