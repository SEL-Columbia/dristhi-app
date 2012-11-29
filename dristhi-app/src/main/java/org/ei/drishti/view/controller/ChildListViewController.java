package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.contract.Children;
import org.ei.drishti.view.contract.Village;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.ei.drishti.view.controller.ProfileNavigationController.navigationToChildProfile;

public class ChildListViewController {
    public static final String CHILD_LIST = "ChildList";
    private final Context context;
    private final AllBeneficiaries allBeneficiaries;
    private final AllEligibleCouples allEligibleCouples;
    private final AllSettings allSettings;
    private Cache<String> childListCache;

    public ChildListViewController(Context context, AllBeneficiaries allBeneficiaries, AllEligibleCouples allEligibleCouples, AllSettings allSettings, Cache<String> childListCache) {
        this.context = context;
        this.allBeneficiaries = allBeneficiaries;
        this.allEligibleCouples = allEligibleCouples;
        this.allSettings = allSettings;
        this.childListCache = childListCache;
    }

    public String get() {
        return childListCache.get(CHILD_LIST, new CacheableData<String>() {
            @Override
            public String fetch() {
                List<Child> children = allBeneficiaries.allChildren();
                List<org.ei.drishti.view.contract.Child> highRiskChildren = new ArrayList<org.ei.drishti.view.contract.Child>();
                List<org.ei.drishti.view.contract.Child> normalRiskChildren = new ArrayList<org.ei.drishti.view.contract.Child>();

                for (Child child : children) {
                    Mother mother = allBeneficiaries.findMother(child.motherCaseId());
                    EligibleCouple couple = allEligibleCouples.findByCaseID(mother.ecCaseId());
                    List<org.ei.drishti.view.contract.Child> childListBasedOnRisk = child.isHighRisk() ? highRiskChildren : normalRiskChildren;

                    childListBasedOnRisk.add(new org.ei.drishti.view.contract.Child(child.caseId(), child.thaayiCardNumber(),
                            couple.wifeName(), couple.husbandName(), couple.ecNumber(), couple.village(), child.isHighRisk()));
                }

                sort(normalRiskChildren);
                sort(highRiskChildren);
                return new Gson().toJson(new Children(highRiskChildren, normalRiskChildren));
            }
        });
    }

    public void startChild(String caseId) {
        navigationToChildProfile(context, caseId);
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

    private void sort(List<org.ei.drishti.view.contract.Child> children) {
        Collections.sort(children, new Comparator<org.ei.drishti.view.contract.Child>() {
            @Override
            public int compare(org.ei.drishti.view.contract.Child oneChild, org.ei.drishti.view.contract.Child anotherChild) {
                return oneChild.motherName().compareToIgnoreCase(anotherChild.motherName());
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
