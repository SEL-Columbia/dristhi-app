package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.contract.ECClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.drishti.AllConstants.DEFAULT_WOMAN_IMAGE_PLACEHOLDER_PATH;

public class ECSmartRegisterController {
    private final AllEligibleCouples allEligibleCouples;
    private final AllBeneficiaries allBeneficiaries;
    private final Cache<String> cache;
    private final static String EC_CLIENTS_LIST = "ECClientsList";

    public ECSmartRegisterController(AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries, Cache<String> cache) {
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.cache = cache;
    }

    public String get() {
        return cache.get(EC_CLIENTS_LIST, new CacheableData<String>() {
            @Override
            public String fetch() {
                List<EligibleCouple> ecs = allEligibleCouples.all();
                List<ECClient> ecClients = new ArrayList<ECClient>();

                for (EligibleCouple ec : ecs) {
//                    Mother mother = allBeneficiaries.findMotherByECCaseId(ec.caseId());
                    String photoPath = isBlank(ec.photoPath()) ? DEFAULT_WOMAN_IMAGE_PLACEHOLDER_PATH : ec.photoPath();
                    ECClient ecClient = new ECClient(ec.caseId(), ec.wifeName(), ec.husbandName(), ec.village(), ec.ecNumber())
                            .withDateOfBirth(ec.getDetail("womanDOB"))
                            .withFPMethod(ec.getDetail("currentMethod"))
                            .withFamilyPlanningMethodChangeDate(ec.getDetail("familyPlanningMethodChangeDate"))
                            .withIUDPlace(ec.getDetail("iudPlace"))
                            .withIUDPerson(ec.getDetail("iudPerson"))
                            .withNumberOfCondomsSupplied(ec.getDetail("numberOfCondomsSupplied"))
                            .withNumberOfCentchromanPillsDelivered(ec.getDetail("numberOfCentchromanPillsDelivered"))
                            .withNumberOfOCPDelivered(ec.getDetail("numberOfOCPDelivered"))
                            .withCaste(ec.getDetail("caste"))
                            .withEconomicStatus(ec.getDetail("economicStatus"))
                            .withNumberOfPregnancies(ec.getDetail("numberOfPregnancies"))
                            .withParity(ec.getDetail("parity"))
                            .withNumberOfLivingChildren(ec.getDetail("numberOfLivingChildren"))
                            .withNumberOfStillBirths(ec.getDetail("numberOfStillBirths"))
                            .withNumberOfAbortions(ec.getDetail("numberOfAbortions"))
                            .withIsHighPriority(ec.isHighPriority())
                            .withPhotoPath(photoPath)
                            .withHighPriorityReason(ec.getDetail("highPriorityReason"))
                            .withIsOutOfArea(ec.isOutOfArea());
                    ecClients.add(ecClient);
                }
                sortByName(ecClients);
                return new Gson().toJson(ecClients);
            }
        });
    }

    private void sortByName(List<ECClient> ecClients) {
        sort(ecClients, new Comparator<ECClient>() {
            @Override
            public int compare(ECClient oneECClient, ECClient anotherECClient) {
                return oneECClient.wifeName().compareToIgnoreCase(anotherECClient.wifeName());
            }
        });
    }
}
