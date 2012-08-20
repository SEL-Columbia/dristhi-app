package org.ei.drishti.util;

import org.ei.drishti.dto.Action;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.HashMap;

import static org.ei.drishti.dto.ActionData.*;

public class ActionBuilder {
    public static Action actionForCreateAlert(String caseID, String latenessStatus, String beneficiaryName, String visitCode, String thaayiCardNumber, String index, String village, String subCenter, String phc, String dueDate) {
        return new Action(caseID, "alert", "createAlert", createAlert(beneficiaryName, village, subCenter, phc, thaayiCardNumber, visitCode, latenessStatus, new DateTime(dueDate)).data(), index, null);
    }

    public static Action actionForDeleteAlert(String caseID, String visitCode, String index) {
        return new Action(caseID, "alert", "deleteAlert", deleteAlert(visitCode).data(), index, null);
    }

    public static Action actionForDeleteAllAlert(String caseID) {
        return new Action(caseID, "alert", "deleteAllAlerts", new HashMap<String, String>(), "0", null);
    }

    public static Action actionForCreateEC(String caseID, String wifeName, String husbandName, String ecNumber, String currentMethod, String village, String subCenter, String phc) {
        return new Action(caseID, "eligibleCouple", "createEC", createEligibleCouple(wifeName, husbandName, ecNumber, currentMethod, village, subCenter, phc, new HashMap<String, String>()).data(), "0", new HashMap<String, String>());
    }

    public static Action actionForDeleteEC(String caseID) {
        return new Action(caseID, "eligibleCouple", "deleteEC", new HashMap<String, String>(), "0", null);
    }

    public static Action actionForUpdateBeneficiary() {
        return new Action("Case X", "child", "updateBeneficiary", updateBeneficiary("pregnant").data(), "0", null);
    }

    public static Action actionForCreateMother(String motherCaseId) {
        return new Action(motherCaseId, "child", "createBeneficiary", createBeneficiary("ecCaseId", "thaayiCardNumber", LocalDate.now(), true, "Delivery Place").data(), "0", null);
    }

    public static Action actionForCreateChild(String motherCaseId) {
        return new Action("Case X", "child", "createChildBeneficiary", registerChildBirth(motherCaseId, LocalDate.now(), "female").data(), "0", null);
    }

    public static Action unknownAction(String target) {
        return new Action("Case X", target, "UNKNOWN-TYPE", new HashMap<String, String>(), "0", null);
    }
}
