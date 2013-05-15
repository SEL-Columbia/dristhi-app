package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.service.AlertService;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.contract.AlertDTO;
import org.ei.drishti.view.contract.FPClient;
import org.ei.drishti.view.contract.Village;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.drishti.AllConstants.DEFAULT_WOMAN_IMAGE_PLACEHOLDER_PATH;

public class FPSmartRegistryController {
    public static final String OCP_REFILL_ALERT_NAME = "OCP Refill";
    public static final String CONDOM_REFILL_ALERT_NAME = "Condom Refill";
    public static final String DMPA_INJECTABLE_REFILL_ALERT_NAME = "DMPA Injectable Refill";
    public static final String FEMALE_STERILIZATION_FOLLOWUP_1_ALERT_NAME = "Female sterilization followup 1";
    public static final String FEMALE_STERILIZATION_FOLLOWUP_2_ALERT_NAME = "Female sterilization followup 2";
    public static final String FEMALE_STERILIZATION_FOLLOWUP_3_ALERT_NAME = "Female sterilization followup 3";
    public static final String MALE_STERILIZATION_FOLLOWUP_1_ALERT_NAME = "Male sterilization followup 1";
    public static final String MALE_STERILIZATION_FOLLOWUP_2_ALERT_NAME = "Male sterilization followup 2";
    public static final String IUD_FOLLOWUP_1_ALERT_NAME = "IUD followup 1";
    public static final String IUD_FOLLOWUP_2_ALERT_NAME = "IUD followup 2";
    public static final String FP_FOLLOWUP_ALERT_NAME = "FP Followup";
    public static final String FP_REFERRAL_FOLLOWUP_ALERT_NAME = "FP Referral Followup";

    private final static String FP_CLIENTS_LIST = "FPClientsList";

    private final AllEligibleCouples allEligibleCouples;
    private final AllBeneficiaries allBeneficiaries;
    private Cache<String> cache;
    private final AlertService alertService;

    public FPSmartRegistryController(AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries, AlertService alertService, Cache<String> cache) {
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.alertService = alertService;
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
                    String photoPath = isBlank(ec.photoPath()) ? DEFAULT_WOMAN_IMAGE_PLACEHOLDER_PATH : ec.photoPath();
                    List<AlertDTO> alerts = getFPAlertsForEC(ec.caseId());
                    fpClients.add(new FPClient(ec.caseId(), ec.wifeName(), ec.husbandName(), ec.age(), thayiCardNumber,
                            ec.ecNumber(), ec.village(), ec.getDetail("currentMethod"),
                            ec.getDetail("sideEffects"), ec.getDetail("complicationDate"), ec.getDetail("numberOfPregnancies"),
                            ec.getDetail("parity"), ec.getDetail("numberOfLivingChildren"),
                            ec.getDetail("numberOfStillBirths"), ec.getDetail("numberOfAbortions"), null,
                            null, ec.isHighPriority(), ec.getDetail("familyPlanningMethodChangeDate"),
                            photoPath, ec.isYoungestChildUnderTwo(), ec.getDetail("youngestChildAge"), alerts));
                }
                sortByName(fpClients);
                return new Gson().toJson(fpClients);
            }
        });
    }

    private List<AlertDTO> getFPAlertsForEC(String entityId) {
        List<Alert> alerts = alertService.findByECIdAndAlertNames(entityId, asList(OCP_REFILL_ALERT_NAME,
                CONDOM_REFILL_ALERT_NAME, DMPA_INJECTABLE_REFILL_ALERT_NAME,
                FEMALE_STERILIZATION_FOLLOWUP_1_ALERT_NAME,
                FEMALE_STERILIZATION_FOLLOWUP_2_ALERT_NAME,
                FEMALE_STERILIZATION_FOLLOWUP_3_ALERT_NAME,
                MALE_STERILIZATION_FOLLOWUP_1_ALERT_NAME,
                MALE_STERILIZATION_FOLLOWUP_2_ALERT_NAME,
                IUD_FOLLOWUP_1_ALERT_NAME,
                IUD_FOLLOWUP_2_ALERT_NAME,
                FP_FOLLOWUP_ALERT_NAME,
                FP_REFERRAL_FOLLOWUP_ALERT_NAME));
        ArrayList<AlertDTO> alertDTOs = new ArrayList<AlertDTO>();
        for (Alert alert : alerts) {
            alertDTOs.add(new AlertDTO(alert.visitCode(), String.valueOf(alert.priority()), alert.startDate()));
        }
        return alertDTOs;
    }

    private void sortByName(List<FPClient> fpClients) {
        sort(fpClients, new Comparator<FPClient>() {
            @Override
            public int compare(FPClient oneFPClient, FPClient anotherFPClient) {
                return oneFPClient.wifeName().compareToIgnoreCase(anotherFPClient.wifeName());
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
}
