package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.activity.PNCDetailActivity;
import org.ei.drishti.view.contract.PNC;
import org.ei.drishti.view.contract.PNCs;
import org.ei.drishti.view.contract.Village;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PNCListViewController {
    public static final String PNC_LIST = "PNCList";
    private final Context context;
    private final AllBeneficiaries allBeneficiaries;
    private final AllEligibleCouples allEligibleCouples;
    private Cache<String> pncListCache;

    public PNCListViewController(Context context, AllBeneficiaries allBeneficiaries, AllEligibleCouples allEligibleCouples, Cache<String> pncListCache) {
        this.context = context;
        this.allBeneficiaries = allBeneficiaries;
        this.allEligibleCouples = allEligibleCouples;
        this.pncListCache = pncListCache;
    }

    public String get() {
        return pncListCache.get(PNC_LIST, new CacheableData<String>() {
            @Override
            public String fetch() {
                List<Mother> mothers = allBeneficiaries.allPNCs();
                List<PNC> highRiskPncs = new ArrayList<PNC>();
                List<PNC> normalRiskPncs = new ArrayList<PNC>();

                for (Mother mother : mothers) {
                    EligibleCouple couple = allEligibleCouples.findByCaseID(mother.ecCaseId());
                    List<PNC> pncListBasedOnRisk = mother.isHighRisk() ? highRiskPncs : normalRiskPncs;

                    pncListBasedOnRisk.add(new PNC(mother.caseId(), mother.thaayiCardNumber(), couple.wifeName(), couple.husbandName(), couple.ecNumber(), couple.village(), mother.isHighRisk()));
                }

                sort(normalRiskPncs);
                sort(highRiskPncs);
                return new Gson().toJson(new PNCs(highRiskPncs, normalRiskPncs));
            }
        });
    }

    public void startPNC(String caseId) {
        Intent intent = new Intent(context.getApplicationContext(), PNCDetailActivity.class);
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

    private void sort(List<PNC> pncs) {
        Collections.sort(pncs, new Comparator<PNC>() {
            @Override
            public int compare(PNC onePnc, PNC anotherPNC) {
                return onePnc.womanName().compareToIgnoreCase(anotherPNC.womanName());
            }
        });
    }
}
