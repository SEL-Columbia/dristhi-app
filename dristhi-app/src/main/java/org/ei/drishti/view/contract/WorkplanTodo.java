package org.ei.drishti.view.contract;

public class WorkplanTodo {
    private String caseId;
    private String visitCode;
    private String beneficiaryName;
    private String husbandName;
    private String description;
    private String dueDate;
    private String villageName;

    public WorkplanTodo(String caseId, String beneficiaryName, String husbandName, String visitCode, String dueDate, String villageName) {
        this.caseId = caseId;
        this.beneficiaryName = beneficiaryName;
        this.husbandName = husbandName;
        this.visitCode = visitCode;
        this.villageName = villageName;
        this.description = TodoDetail.from(visitCode).prefix();
        this.dueDate = dueDate;
    }
}
