package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.Pair;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.service.AlertService;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.contract.ANCClient;
import org.ei.drishti.view.contract.AlertDTO;
import org.ei.drishti.view.contract.ServiceProvidedDTO;
import org.ei.drishti.view.contract.Village;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.drishti.AllConstants.DEFAULT_WOMAN_IMAGE_PLACEHOLDER_PATH;

public class ANCSmartRegistryController {
    private static final String ANC_CLIENTS_LIST = "ANCClientList";
    private AllEligibleCouples allEligibleCouples;
    private AllBeneficiaries allBeneficiaries;
    private AlertService alertService;
    private Cache<String> cache;

    public ANCSmartRegistryController(AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries, AlertService alertService, Cache<String> cache) {
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.alertService = alertService;
        this.cache = cache;
    }

    public String get() {
        return cache.get(ANC_CLIENTS_LIST, new CacheableData<String>() {
            @Override
            public String fetch() {
                List<ANCClient> ancClients = new ArrayList<ANCClient>();
                List<Pair<Mother, EligibleCouple>> ancsWithEcs = allBeneficiaries.allANCsWithEC();

                for (Pair<Mother, EligibleCouple> ancWithEc : ancsWithEcs) {
                    Mother anc = ancWithEc.getLeft();
                    EligibleCouple ec = ancWithEc.getRight();
                    String photoPath = isBlank(ec.photoPath()) ? DEFAULT_WOMAN_IMAGE_PLACEHOLDER_PATH : ec.photoPath();
                    ancClients.add(new ANCClient(anc.caseId(), ec.ecNumber(), ec.village(), ec.wifeName(), anc.thaayiCardNumber(), ec.age(), ec.husbandName(),
                            anc.getDetail("edd"), anc.referenceDate(), ec.isHighPriority(), anc.isHighRisk(), ec.isOutOfArea(), ec.getDetail("caste"), photoPath,
                            new ArrayList<AlertDTO>(), new ArrayList<ServiceProvidedDTO>()));
                }
                sortByName(ancClients);
                return new Gson().toJson(ancClients);
            }
        });
    }

    public String villages() {
        List<Village> villagesList = new ArrayList<Village>();
        List<String> villages = allEligibleCouples.villages();
        for (String village : villages) {
            villagesList.add(new Village(village));
        }

        return new Gson().toJson(villagesList);
    }

    private void sortByName(List<ANCClient> ancClients) {
        sort(ancClients, new Comparator<ANCClient>() {
            @Override
            public int compare(ANCClient oneANCClient, ANCClient anotherANCClient) {
                return oneANCClient.wifeName().compareToIgnoreCase(anotherANCClient.wifeName());
            }
        });
    }
}
