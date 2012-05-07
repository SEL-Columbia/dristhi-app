package org.ei.drishti.util;

import org.ei.drishti.domain.Action;

import java.util.HashMap;
import java.util.Map;

public class ActionBuilder {
    public static Action actionForCreateAlert(String caseID, String latenessStatus, String beneficiaryName, String visitCode, String thaayiCardNumber, String index) {
        return new Action(caseID, "createAlert", dataForCreateActionForAlert(latenessStatus, beneficiaryName, visitCode, thaayiCardNumber), index);
    }

    public static Action actionForDeleteAlert(String caseID, String visitCode, String index) {
        return new Action(caseID, "deleteAlert", dataForDeleteActionForAlert(visitCode), index);
    }

    public static Action actionForDeleteAllAlert(String caseID) {
        return new Action(caseID, "deleteAllAlerts", new HashMap<String, String>(), "0");
    }

    public static Action actionForCreateEC(String caseID, String wifeName, String husbandName, String ecNumber) {
        return new Action(caseID, "createEC", dataForCreateActionForEC(wifeName, husbandName, ecNumber), "0");
    }

    public static Action actionForDeleteEC(String caseID) {
        return new Action(caseID, "deleteEC", new HashMap<String, String>(), "0");
    }

    private static Map<String, String> dataForCreateActionForEC(String wifeName, String husbandName, String ecNumber) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("wife", wifeName);
        map.put("husband", husbandName);
        map.put("ecNumber", ecNumber);
        return map;
    }

    private static Map<String, String> dataForCreateActionForAlert(String lateness, String beneficiaryName, String visitCode, String thaayiCardNumber) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("latenessStatus", lateness);
        map.put("beneficiaryName", beneficiaryName);
        map.put("visitCode", visitCode);
        map.put("thaayiCardNumber", thaayiCardNumber);
        return map;
    }

    private static Map<String, String> dataForDeleteActionForAlert(String visitCode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("visitCode", visitCode);
        return map;
    }
}
