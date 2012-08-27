package org.ei.drishti.view.contract;

public class WorkplanTodo {
    private String beneficiaryName;
    private String description;
    private String dueDate;

    public WorkplanTodo(String beneficiaryName, String visitCode, String dueDate) {
        this.beneficiaryName = beneficiaryName;
        this.description = TodoDetail.from(visitCode).prefix() + " due";
        this.dueDate = dueDate;
    }
}
