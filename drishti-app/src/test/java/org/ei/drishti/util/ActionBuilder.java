package org.ei.drishti.util;

import org.ei.drishti.domain.Action;

import java.util.HashMap;
import java.util.Map;

public class ActionBuilder {
    public static Action actionForCreate(String caseID, String latenessStatus, String beneficiaryName, String visitCode, String thaayiCardNumber) {
        return new Action(caseID, "createAlert", dataForCreateAction(latenessStatus, beneficiaryName, visitCode, thaayiCardNumber), "0");
    }

    public static Action actionForCreate(String caseID, String latenessStatus, String beneficiaryName, String visitCode, String thaayiCardNumber, String index) {
        return new Action(caseID, "createAlert", dataForCreateAction(latenessStatus, beneficiaryName, visitCode, thaayiCardNumber), index);
    }

    public static Action actionForDelete(String caseID, String visitCode) {
        return new Action(caseID, "deleteAlert", dataForDeleteAction(visitCode), "0");
    }

    public static Action actionForDelete(String caseID, String visitCode, String index) {
        return new Action(caseID, "deleteAlert", dataForDeleteAction(visitCode), index);
    }

    public static Action actionForDeleteAll(String caseID) {
        return new Action(caseID, "deleteAllAlerts", new HashMap<String, String>(), "0");
    }

    private static Map<String, String> dataForCreateAction(String lateness, String beneficiaryName, String visitCode, String thaayiCardNumber) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("latenessStatus", lateness);
        map.put("beneficiaryName", beneficiaryName);
        map.put("visitCode", visitCode);
        map.put("thaayiCardNumber", thaayiCardNumber);
        return map;
    }

    private static Map<String, String> dataForDeleteAction(String visitCode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("visitCode", visitCode);
        return map;
    }
}
