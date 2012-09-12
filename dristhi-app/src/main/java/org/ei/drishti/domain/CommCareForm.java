package org.ei.drishti.domain;

import java.text.MessageFormat;

public enum CommCareForm {
    ANC_REGISTER("m1", "m1-f0", true),
    ANC_SERVICES("m1", "m1-f1", true),
    ANC_CLOSE("m1", "m1-f4", true),

    EC_ADD("m0", "m0-f0", false),
    EC_FP_UPDATE("m0", "m0-f1", true),
    EC_FP_COMPLICATIONS("m0", "m0-f2", true),
    EC_CLOSE("m0", "m0-f3", true),
    EC_REGISTER_ANC("m0", "m0-f4", true),

    PNC_SERVICES("m1", "m1-f2", true),
    PNC_CLOSE("m1", "m1-f6", true),

    CHILD_IMMUNIZATION("m2", "m2-f0", true);

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

    public String value() {
        return name();
    }
}
