package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.Pair;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.activity.ANCDetailActivity;
import org.ei.drishti.view.contract.ANC;
import org.ei.drishti.view.contract.ANCs;
import org.ei.drishti.view.contract.Village;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ANCListViewController {
    public static final String ANC_LIST = "ANCList";
    private final AllBeneficiaries allBeneficiaries;
    private AllEligibleCouples allEligibleCouples;
    private final Context context;
    private Cache<String> ancListCache;
    private CommCareClientService commCareClientService;

    public ANCListViewController(Context context, AllBeneficiaries allBeneficiaries, AllEligibleCouples allEligibleCouples, Cache<String> ancListCache, CommCareClientService commCareClientService) {
        this.allBeneficiaries = allBeneficiaries;
        this.allEligibleCouples = allEligibleCouples;
        this.context = context;
        this.ancListCache = ancListCache;
        this.commCareClientService = commCareClientService;
    }

    public String get() {
        return ancListCache.get(ANC_LIST, new CacheableData<String>() {
            @Override
            public String fetch() {
                List<Pair<Mother,EligibleCouple>> mothersWithEC = allBeneficiaries.allANCsWithEC();

                List<ANC> highRiskAncs = new ArrayList<ANC>();
                List<ANC> normalRiskAncs = new ArrayList<ANC>();

                for (Pair<Mother, EligibleCouple> motherWithEC : mothersWithEC) {
                    Mother mother = motherWithEC.getLeft();
                    EligibleCouple ec = motherWithEC.getRight();

                    List<ANC> ancListBasedOnRisk = mother.isHighRisk() ? highRiskAncs : normalRiskAncs;
                    ancListBasedOnRisk.add(new ANC(mother.caseId(), mother.thaayiCardNumber(), ec.wifeName(), ec.husbandName(), ec.village(), mother.isHighRisk()));
                }

                sort(normalRiskAncs);
                sort(highRiskAncs);
                return new Gson().toJson(new ANCs(highRiskAncs, normalRiskAncs));
            }
        });
    }

    public void startCommCare(String formId) {
        commCareClientService.start(context, formId, "");
    }


    public void startANC(String caseId) {
        Intent intent = new Intent(context.getApplicationContext(), ANCDetailActivity.class);
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

    private void sort(List<ANC> ancs) {
        Collections.sort(ancs, new Comparator<ANC>() {
            @Override
            public int compare(ANC oneAnc, ANC anotherANC) {
                return oneAnc.womanName().compareToIgnoreCase(anotherANC.womanName());
            }
        });
    }
}
