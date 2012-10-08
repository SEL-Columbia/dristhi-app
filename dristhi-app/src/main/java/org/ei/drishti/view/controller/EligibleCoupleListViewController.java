package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.activity.EligibleCoupleDetailActivity;
import org.ei.drishti.view.contract.EC;
import org.ei.drishti.view.contract.ECs;
import org.ei.drishti.view.contract.Village;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EligibleCoupleListViewController {
    public static final String ELIGIBLE_COUPLE_LIST = "EligibleCoupleList";
    private AllEligibleCouples allEligibleCouples;
    private AllBeneficiaries allBeneficiaries;
    private Context context;
    private CommCareClientService commCareClientService;
    private Cache<String> eligibleCoupleListCache;

    public EligibleCoupleListViewController(AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries, Cache<String> eligibleCoupleListCache, Context context, CommCareClientService commCareClientService) {
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.context = context;
        this.commCareClientService = commCareClientService;
        this.eligibleCoupleListCache = eligibleCoupleListCache;
    }

    public String get() {
        return eligibleCoupleListCache.get(ELIGIBLE_COUPLE_LIST, new CacheableData<String>() {
            @Override
            public String fetch() {
                List<EligibleCouple> couples = allEligibleCouples.all();
                List<EC> normalPriority = new ArrayList<EC>();
                List<EC> highPriority = new ArrayList<EC>();

                for (EligibleCouple couple : couples) {
                    List<EC> ecListBasedOnPriority = couple.isHighPriority() ? highPriority : normalPriority;
                    Mother mother = allBeneficiaries.findMotherByECCaseId(couple.caseId());
                    String thayiCardNumber = mother == null ? "" : mother.thaayiCardNumber();
                    ecListBasedOnPriority.add(new EC(couple.caseId(), couple.wifeName(), couple.husbandName(), couple.village(), couple.ecNumber(), thayiCardNumber, couple.isHighPriority(), false));
                }

                sort(highPriority);
                sort(normalPriority);
                return new Gson().toJson(new ECs(highPriority, normalPriority));
            }
        });
    }

    public void startCommCare(String formId) {
        commCareClientService.start(context, formId, "");
    }

    public void startEC(String caseId) {
        Intent intent = new Intent(context.getApplicationContext(), EligibleCoupleDetailActivity.class);
        intent.putExtra("caseId", caseId);
        context.startActivity(intent);
    }

    public String villages() {
        List<Village> villagesList = new ArrayList<Village>();
        List<String> villages = allEligibleCouples.villages();
        villagesList.add(new Village("All"));
        for (String village : villages) {
            villagesList.add(new Village(village));
        }

        return new Gson().toJson(villagesList);
    }

    private void sort(List<EC> normalPriority) {
        Collections.sort(normalPriority, new Comparator<EC>() {
            @Override
            public int compare(EC oneEC, EC anotherEC) {
                return oneEC.wifeName().compareToIgnoreCase(anotherEC.wifeName());
            }
        });
    }
}
