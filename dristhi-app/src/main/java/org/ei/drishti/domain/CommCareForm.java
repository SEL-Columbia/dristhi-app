package org.ei.drishti.domain;

import java.text.MessageFormat;

public enum CommCareForm {
    EC_SERVICES("m0", "m0-f1", true),
    ANC_REGISTER("m1", "m0-f4", false),
    PNC_SERVICES("m1", "m1-f1", true),
    ANC_SERVICES("m1", "m1-f0", true);

    private final String moduleId;
    private final String formId;
    private boolean takesCaseId;

    CommCareForm(String moduleId, String formId, boolean takesCaseId) {
        this.moduleId = moduleId;
        this.formId = formId;
        this.takesCaseId = takesCaseId;
    }

    public String dataFor(String caseId) {
        String caseData = "";
        if (takesCaseId) {
            caseData = MessageFormat.format("CASE_ID case_id {0} ", caseId);
        }
        return MessageFormat.format("COMMAND_ID {0} {1}COMMAND_ID {2}", moduleId, caseData, formId);
    }
}
