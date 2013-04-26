package org.ei.drishti.view.contract;

import java.util.HashMap;
import java.util.Map;

class TodoDetail {
    private final String messagePrefix;

    private static Map<String, TodoDetail> map = new HashMap<String, TodoDetail>();

    static {
        map.put("ANC 1", new TodoDetail("ANC Visit #1"));
        map.put("ANC 2", new TodoDetail("ANC Visit #2"));
        map.put("ANC 3", new TodoDetail("ANC Visit #3"));
        map.put("ANC 4", new TodoDetail("ANC Visit #4"));
        map.put("EDD", new TodoDetail("EDD"));
        map.put("OPV 0", new TodoDetail("OPV 0"));
        map.put("HEP B0", new TodoDetail("HEP B 0"));
        map.put("DPT 0", new TodoDetail("DPT 0"));
        map.put("Hepatitis B1", new TodoDetail("Hep B 1"));
        map.put("BCG", new TodoDetail("BCG"));
        map.put("IFA 1", new TodoDetail("IFA"));
        map.put("TT 1", new TodoDetail("TT1"));
    }

    public static TodoDetail from(String visitCode) {
        TodoDetail todoDetail = map.get(visitCode);
        return todoDetail == null ? new TodoDetail(visitCode) : todoDetail;
    }

    TodoDetail(String messagePrefix) {
        this.messagePrefix = messagePrefix;
    }

    public String prefix() {
        return messagePrefix;
    }
}
