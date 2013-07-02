package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.activity.FormActivity;
import org.ei.drishti.view.contract.Beneficiaries;
import org.ei.drishti.view.contract.EC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.ei.drishti.AllConstants.ENTITY_ID_PARAM;
import static org.ei.drishti.AllConstants.FORM_NAME_PARAM;
import static org.ei.drishti.view.controller.ProfileNavigationController.navigateToECProfile;

public class EligibleCoupleListViewController {
    public static final String ELIGIBLE_COUPLE_LIST = "EligibleCoupleList";
    private AllEligibleCouples allEligibleCouples;
    private AllBeneficiaries allBeneficiaries;
    private AllSettings allSettings;
    private Context context;
    private Cache<String> eligibleCoupleListCache;

    public EligibleCoupleListViewController(AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries,
                                            AllSettings allSettings, Cache<String> eligibleCoupleListCache, Context context) {
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.allSettings = allSettings;
        this.context = context;
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
                    String thayiCardNumber = mother == null ? "" : mother.thayiCardNumber();
                    ecListBasedOnPriority.add(new EC(couple.caseId(), couple.wifeName(), couple.husbandName(), couple.village(),
                            couple.ecNumber(), thayiCardNumber, couple.photoPath(), couple.isHighPriority(), false));
                }

                sort(highPriority);
                sort(normalPriority);
                return new Gson().toJson(new Beneficiaries<EC>(highPriority, normalPriority));
            }
        });
    }

    public void startFormActivity(String formName, String entityId) {
        Intent intent = new Intent(context, FormActivity.class);
        intent.putExtra(FORM_NAME_PARAM, formName);
        intent.putExtra(ENTITY_ID_PARAM, entityId);
        context.startActivity(intent);
    }

    public void startEC(String caseId) {
        navigateToECProfile(context, caseId);
    }

    private void sort(List<EC> normalPriority) {
        Collections.sort(normalPriority, new Comparator<EC>() {
            @Override
            public int compare(EC oneEC, EC anotherEC) {
                return oneEC.wifeName().compareToIgnoreCase(anotherEC.wifeName());
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
