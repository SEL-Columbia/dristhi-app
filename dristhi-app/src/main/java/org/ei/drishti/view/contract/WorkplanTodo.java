package org.ei.drishti.view.contract;

import org.ei.drishti.domain.CommCareForm;

public class WorkplanTodo {
    private String caseId;
    private String visitCode;
    private String beneficiaryName;
    private String description;
    private String dueDate;
    private CommCareForm formToOpen;


    public WorkplanTodo(String caseId, String beneficiaryName, String visitCode, String dueDate) {
        this.caseId = caseId;
        this.beneficiaryName = beneficiaryName;
        this.visitCode = visitCode;
        this.description = TodoDetail.from(visitCode).prefix() + " due";
        this.dueDate = dueDate;
        this.formToOpen = TodoDetail.from(visitCode).formToOpen();
    }
}
