package org.ei.drishti.view.contract;

public class WorkplanAlert {
    private String beneficiaryName;
    private String dueDate;
    private String description;

    public WorkplanAlert(String beneficiaryName, String dueDate, String description) {
        this.beneficiaryName = beneficiaryName;
        this.dueDate = dueDate;
        this.description = description;
    }
}
