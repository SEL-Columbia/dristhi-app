package org.ei.drishti.util;

import org.ei.drishti.domain.AlertAction;

import java.util.HashMap;
import java.util.Map;

public class AlertActionBuilder {
    public static AlertAction actionForCreate(String caseID, String latenessStatus, String motherName, String visitCode, String thaayiCardNumber) {
        return new AlertAction(caseID, "create", dataForCreateAction(latenessStatus, motherName, visitCode, thaayiCardNumber), "0");
    }

    public static AlertAction actionForCreate(String caseID, String latenessStatus, String motherName, String visitCode, String thaayiCardNumber, String index) {
        return new AlertAction(caseID, "create", dataForCreateAction(latenessStatus, motherName, visitCode, thaayiCardNumber), index);
    }

    public static AlertAction actionForDelete(String caseID, String visitCode) {
        return new AlertAction(caseID, "delete", dataForDeleteAction(visitCode), "0");
    }

    public static AlertAction actionForDelete(String caseID, String visitCode, String index) {
        return new AlertAction(caseID, "delete", dataForDeleteAction(visitCode), index);
    }

    public static AlertAction actionForDeleteAll(String caseID) {
        return new AlertAction(caseID, "deleteAll", new HashMap<String, String>(), "0");
    }

    private static Map<String, String> dataForCreateAction(String lateness, String motherName, String visitCode, String thaayiCardNumber) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("latenessStatus", lateness);
        map.put("motherName", motherName);
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
