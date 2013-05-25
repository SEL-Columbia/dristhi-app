package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.Pair;
import org.ei.drishti.domain.Alert;
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
    private static final String ANC_1_ALERT_NAME = "ANC 1";
    private static final String ANC_2_ALERT_NAME = "ANC 2";
    private static final String ANC_3_ALERT_NAME = "ANC 3";
    private static final String ANC_4_ALERT_NAME = "ANC 4";
    private static final String IFA_1_ALERT_NAME = "IFA 1";
    private static final String IFA_2_ALERT_NAME = "IFA 2";
    private static final String IFA_3_ALERT_NAME = "IFA 3";
    private static final String LAB_REMINDER_ALERT_NAME = "REMINDER";
    private static final String TT_1_ALERT_NAME = "TT 1";
    private static final String TT_2_ALERT_NAME = "TT 2";

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

                    ancClients.add(new ANCClient(anc.caseId(), ec.village(), ec.wifeName(), anc.thaayiCardNumber(), anc.getDetail("edd"), anc.referenceDate())
                            .withHusbandName(ec.husbandName())
                            .withAge(ec.age())
                            .withECNumber(ec.ecNumber())
                            .withIsHighPriority(ec.isHighPriority())
                            .withIsHighRisk(anc.isHighRisk())
                            .withIsOutOfArea(ec.isOutOfArea())
                            .withCaste(ec.getDetail("caste"))
                            .withPhotoPath(photoPath)
                            .withAlerts(getAlertsForANC(anc.caseId()))
                            .withServicesProvided(new ArrayList<ServiceProvidedDTO>())
                    );
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

    private List<AlertDTO> getAlertsForANC(String entityId) {
        List<Alert> alerts = alertService.findByEntityIdAndAlertNames(entityId,
                ANC_1_ALERT_NAME,
                ANC_2_ALERT_NAME,
                ANC_3_ALERT_NAME,
                ANC_4_ALERT_NAME,
                IFA_1_ALERT_NAME,
                IFA_2_ALERT_NAME,
                IFA_3_ALERT_NAME,
                LAB_REMINDER_ALERT_NAME,
                TT_1_ALERT_NAME,
                TT_2_ALERT_NAME
        );
        List<AlertDTO> alertDTOs = new ArrayList<AlertDTO>();
        for (Alert alert : alerts) {
            alertDTOs.add(new AlertDTO(alert.visitCode(), String.valueOf(alert.status()), alert.startDate()));
        }
        return alertDTOs;
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
