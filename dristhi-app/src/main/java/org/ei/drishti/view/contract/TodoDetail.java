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
