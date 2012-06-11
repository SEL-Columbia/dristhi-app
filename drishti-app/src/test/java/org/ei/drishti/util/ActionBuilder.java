package org.ei.drishti.util;

import org.ei.drishti.domain.Action;

import java.util.HashMap;
import java.util.Map;

public class ActionBuilder {
    public static Action actionForCreateAlert(String caseID, String latenessStatus, String beneficiaryName, String visitCode, String thaayiCardNumber, String index, String village, String dueDate) {
        return new Action(caseID, "alert", "createAlert", dataForCreateActionForAlert(latenessStatus, beneficiaryName, visitCode, thaayiCardNumber, village, dueDate), index);
    }

    public static Action actionForDeleteAlert(String caseID, String visitCode, String index) {
        return new Action(caseID, "alert", "deleteAlert", dataForDeleteActionForAlert(visitCode), index);
    }

    public static Action actionForDeleteAllAlert(String caseID) {
        return new Action(caseID, "alert", "deleteAllAlerts", new HashMap<String, String>(), "0");
    }

    public static Action actionForCreateEC(String caseID, String wifeName, String husbandName, String ecNumber, String village, String subCenter) {
        return new Action(caseID, "eligibleCouple", "createEC", dataForCreateActionForEC(wifeName, husbandName, ecNumber, village, subCenter), "0");
    }

    public static Action actionForDeleteEC(String caseID) {
        return new Action(caseID, "eligibleCouple", "deleteEC", new HashMap<String, String>(), "0");
    }

    private static Map<String, String> dataForCreateActionForEC(String wifeName, String husbandName, String ecNumber, String village, String subCenter) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("wife", wifeName);
        map.put("husband", husbandName);
        map.put("ecNumber", ecNumber);
        map.put("village", village);
        map.put("subcenter", subCenter);
        return map;
    }

    private static Map<String, String> dataForCreateActionForAlert(String lateness, String beneficiaryName, String visitCode, String thaayiCardNumber, String village, String dueDate) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("latenessStatus", lateness);
        map.put("beneficiaryName", beneficiaryName);
        map.put("visitCode", visitCode);
        map.put("thaayiCardNumber", thaayiCardNumber);
        map.put("village", village);
        map.put("dueDate", dueDate);
        return map;
    }

    private static Map<String, String> dataForDeleteActionForAlert(String visitCode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("visitCode", visitCode);
        return map;
    }
}
