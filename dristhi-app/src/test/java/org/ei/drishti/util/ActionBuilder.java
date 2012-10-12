package org.ei.drishti.util;

import org.ei.drishti.dto.Action;
import org.ei.drishti.dto.ActionData;
import org.ei.drishti.dto.AlertPriority;
import org.ei.drishti.dto.BeneficiaryType;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.Map;

import static org.ei.drishti.dto.ActionData.*;
import static org.ei.drishti.dto.BeneficiaryType.child;
import static org.ei.drishti.dto.BeneficiaryType.mother;
import static org.ei.drishti.util.EasyMap.mapOf;

public class ActionBuilder {
    public static Action actionForCreateAlert(String caseID, String alertPriority, String beneficiaryType, String visitCode, String startDate, String expiryDate, String index) {
        return new Action(caseID, "alert", "createAlert", createAlert(BeneficiaryType.from(beneficiaryType), visitCode, AlertPriority.from(alertPriority), new DateTime(startDate), new DateTime(expiryDate)).data(), index, new HashMap<String, String>());
    }

    public static Action actionForCloseAlert(String caseID, String visitCode, String completionDate, String index) {
        return new Action(caseID, "alert", "closeAlert", markAlertAsClosed(visitCode, completionDate).data(), index, new HashMap<String, String>());
    }

    public static Action actionForDeleteAllAlert(String caseID) {
        return new Action(caseID, "alert", "deleteAllAlerts", new HashMap<String, String>(), "0", new HashMap<String, String>());
    }

    public static Action actionForCreateEC(String caseID, String wifeName, String husbandName, String ecNumber, String village, String subCenter, String phc) {
        return new Action(caseID, "eligibleCouple", "createEC", createEligibleCouple(wifeName, husbandName, ecNumber, village, subCenter, phc, new HashMap<String, String>()).data(), "0", new HashMap<String, String>());
    }

    public static Action actionForUpdateECDetails(String caseId, Map<String, String> details) {
        ActionData actionData = updateEligibleCoupleDetails(details);
        return new Action(caseId, "eligibleCouple", "updateDetails", actionData.data(), "0", actionData.details());
    }

    public static Action actionForDeleteEC(String caseID) {
        return new Action(caseID, "eligibleCouple", "deleteEC", new HashMap<String, String>(), "0", new HashMap<String, String>());
    }

    public static Action actionForUpdateBeneficiary() {
        return new Action("Case X", "mother", "closeANC", closeANC("death").data(), "0", new HashMap<String, String>());
    }

    public static Action actionForRegisterPregnancy(String motherCaseId) {
        ActionData actionData = registerPregnancy("ecCaseId", "thaayiCardNumber", LocalDate.now(), mapOf("some-key", "some-field"));
        return new Action(motherCaseId, "mother", "registerPregnancy", actionData.data(), "0", actionData.details());
    }

    public static Action actionForCreateChild(String motherCaseId) {
        return new Action("Case X", "child", "register", registerChildBirth(motherCaseId, "TC 1", LocalDate.now(), "female", new HashMap<String, String>()).data(), "0", new HashMap<String, String>());
    }

    public static Action actionForOutOfAreaANCRegistration(String caseId) {
        ActionData actionData = ActionData.registerOutOfAreaANC("EC Case ID", "Wife 1", "Husband 1", "Village X", "SubCenter X", "PHC X", "TC 1", LocalDate.parse("2012-09-17"), new HashMap<String, String>());
        return new Action(caseId, "mother", "registerOutOfAreaANC", actionData.data(), "0", mapOf("some-key", "some-field"));
    }

    public static Action unknownAction(String target) {
        return new Action("Case X", target, "UNKNOWN-TYPE", new HashMap<String, String>(), "0", new HashMap<String, String>());
    }

    public static Action actionForUpdateMotherDetails(String motherCaseId, Map<String, String> details) {
        ActionData actionData = updateMotherDetails(details);
        return new Action(motherCaseId, "mother", "updateDetails", actionData.data(), "0", actionData.details());
    }

    public static Action actionForANCCareProvided(String motherCaseId, int visitNumber, int numberOfIFATabletsProvided, LocalDate visitDate, String ttDose) {
        ActionData actionData = ancCareProvided(visitNumber, visitDate, numberOfIFATabletsProvided, ttDose, new HashMap<String, String>());
        return new Action(motherCaseId, "mother", "ancCareProvided", actionData.data(), "0", new HashMap<String, String>());
    }

    public static Action actionForUpdateANCOutcome(String caseId, Map<String, String> details) {
        ActionData actionData = updateANCOutcome(details);
        return new Action(caseId, "mother", "updateANCOutcome", actionData.data(), "0", actionData.details());
    }

    public static Action actionForMotherPNCVisit(String caseId, Map<String, String> details) {
        ActionData actionData = pncVisitHappened(mother, LocalDate.parse("2012-01-01"), 1, 10, details);
        return new Action(caseId, "mother", "pncVisitHappened", actionData.data(), "0", actionData.details());
    }

    public static Action actionForChildPNCVisit(String caseId, Map<String, String> details) {
        ActionData actionData = pncVisitHappened(child, LocalDate.parse("2012-01-01"), 1, 10, details);
        return new Action(caseId, "child", "pncVisitHappened", actionData.data(), "0", actionData.details());
    }
}
