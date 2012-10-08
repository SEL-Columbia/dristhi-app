package org.ei.drishti.view.contract;

import java.util.List;

public class WorkplanContext {
    private List<WorkplanTodo> overdue;
    private List<WorkplanTodo> upcoming;
    private List<WorkplanTodo> completed;

    public WorkplanContext(List<WorkplanTodo> overdue, List<WorkplanTodo> upcoming, List<WorkplanTodo> completed) {
        this.overdue = overdue;
        this.upcoming = upcoming;
        this.completed = completed;
    }
}
