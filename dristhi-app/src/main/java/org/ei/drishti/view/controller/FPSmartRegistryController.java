package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.contract.FPClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;

public class FPSmartRegistryController {

    private final AllEligibleCouples allEligibleCouples;
    private final AllBeneficiaries allBeneficiaries;
    private Cache<String> cache;
    private final static String FP_CLIENTS_LIST = "FPClientsList";

    public FPSmartRegistryController(AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries, Cache<String> cache) {
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.cache = cache;
    }

    public String get() {
        return cache.get(FP_CLIENTS_LIST, new CacheableData<String>() {
            @Override
            public String fetch() {
                List<EligibleCouple> ecs = allEligibleCouples.all();
                List<FPClient> fpClients = new ArrayList<FPClient>();

                for (EligibleCouple ec : ecs) {
                    Mother mother = allBeneficiaries.findMotherByECCaseId(ec.caseId());
                    String thayiCardNumber = mother == null ? "" : mother.thaayiCardNumber();
                    fpClients.add(new FPClient(ec.wifeName(), ec.husbandName(), ec.age(), thayiCardNumber, ec.ecNumber(), ec.village(), ec.getDetail("currentMethod"),
                            ec.getDetail("sideEffects"), ec.getDetail("numberOfPregnancies"),
                            ec.getDetail("parity"), ec.getDetail("numberOfLivingChildren"),
                            ec.getDetail("numberOfStillBirths"), ec.getDetail("numberOfAbortions"), null,
                            null, ec.isHighPriority(), ec.getDetail("familyPlanningMethodChangeDate")));
                }

                sortByName(fpClients);
                return new Gson().toJson(fpClients);
            }
        });
    }

    private void sortByName(List<FPClient> fpClients) {
        sort(fpClients, new Comparator<FPClient>() {
            @Override
            public int compare(FPClient oneFPClient, FPClient anotherFPClient) {
                return oneFPClient.wifeName().compareToIgnoreCase(anotherFPClient.wifeName());
            }
        });

    }
}
