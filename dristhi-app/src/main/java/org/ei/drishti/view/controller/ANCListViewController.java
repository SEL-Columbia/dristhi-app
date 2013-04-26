package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.Pair;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.contract.ANC;
import org.ei.drishti.view.contract.Beneficiaries;
import org.ei.drishti.view.contract.Village;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.ei.drishti.view.controller.ProfileNavigationController.navigationToANCProfile;

public class ANCListViewController {
    public static final String ANC_LIST = "ANCList";
    private final AllBeneficiaries allBeneficiaries;
    private AllEligibleCouples allEligibleCouples;
    private final Context context;
    private final AllSettings allSettings;
    private Cache<String> ancListCache;

    public ANCListViewController(Context context, AllBeneficiaries allBeneficiaries, AllEligibleCouples allEligibleCouples, AllSettings allSettings, Cache<String> ancListCache) {
        this.allBeneficiaries = allBeneficiaries;
        this.allEligibleCouples = allEligibleCouples;
        this.context = context;
        this.allSettings = allSettings;
        this.ancListCache = ancListCache;
    }

    public String get() {
        return ancListCache.get(ANC_LIST, new CacheableData<String>() {
            @Override
            public String fetch() {
                List<Pair<Mother, EligibleCouple>> mothersWithEC = allBeneficiaries.allANCsWithEC();

                List<ANC> highRiskANCs = new ArrayList<ANC>();
                List<ANC> normalRiskANCs = new ArrayList<ANC>();

                for (Pair<Mother, EligibleCouple> motherWithEC : mothersWithEC) {
                    Mother mother = motherWithEC.getLeft();
                    EligibleCouple ec = motherWithEC.getRight();

                    List<ANC> ancListBasedOnRisk = mother.isHighRisk() ? highRiskANCs : normalRiskANCs;
                    ancListBasedOnRisk.add(new ANC(mother.caseId(), mother.thaayiCardNumber(), ec.wifeName(), ec.husbandName(), ec.village(), ec.ecNumber(), mother.isHighRisk()));
                }

                sort(normalRiskANCs);
                sort(highRiskANCs);
                return new Gson().toJson(new Beneficiaries<ANC>(highRiskANCs, normalRiskANCs));
            }
        });
    }

    public void startANC(String caseId) {
        navigationToANCProfile(context, caseId);
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

    public void saveAppliedVillageFilter(String village) {
        allSettings.saveAppliedVillageFilter(village);
    }

    public String appliedVillageFilter(String defaultFilterValue) {
        return allSettings.appliedVillageFilter(defaultFilterValue);
    }
}
