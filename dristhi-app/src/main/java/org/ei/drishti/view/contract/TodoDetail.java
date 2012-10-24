package org.ei.drishti.view.contract;

import org.ei.drishti.domain.CommCareForm;

import java.util.HashMap;
import java.util.Map;

class TodoDetail {
    private final String messagePrefix;
    private final CommCareForm formToOpen;

    private static Map<String, TodoDetail> map = new HashMap<String, TodoDetail>();

    static {
        map.put("ANC 1", new TodoDetail("ANC Visit #1", CommCareForm.ANC_SERVICES));
        map.put("ANC 2", new TodoDetail("ANC Visit #2", CommCareForm.ANC_SERVICES));
        map.put("ANC 3", new TodoDetail("ANC Visit #3", CommCareForm.ANC_SERVICES));
        map.put("ANC 4", new TodoDetail("ANC Visit #4", CommCareForm.ANC_SERVICES));
        map.put("EDD", new TodoDetail("EDD", CommCareForm.ANC_DELIVERY_OUTCOME));
        map.put("OPV 0", new TodoDetail("OPV 0", CommCareForm.CHILD_IMMUNIZATION));
        map.put("HEP B0", new TodoDetail("HEP B 0", CommCareForm.CHILD_IMMUNIZATION));
        map.put("DPT 0", new TodoDetail("DPT 0", CommCareForm.CHILD_IMMUNIZATION));
        map.put("Hepatitis B1", new TodoDetail("Hep B 1", CommCareForm.CHILD_IMMUNIZATION));
        map.put("BCG", new TodoDetail("BCG", CommCareForm.CHILD_IMMUNIZATION));
        map.put("IFA 1", new TodoDetail("IFA", CommCareForm.SHORTCUT));
        map.put("TT 1", new TodoDetail("TT1", CommCareForm.SHORTCUT));
    }

    public static TodoDetail from(String visitCode) {
        TodoDetail todoDetail = map.get(visitCode);
        return todoDetail == null ? new TodoDetail(visitCode, null) : todoDetail;
    }

    TodoDetail(String messagePrefix, CommCareForm formToOpen) {
        this.messagePrefix = messagePrefix;
        this.formToOpen = formToOpen;
    }

    public String prefix() {
        return messagePrefix;
    }

    public CommCareForm formToOpen() {
        return formToOpen;
    }
}
