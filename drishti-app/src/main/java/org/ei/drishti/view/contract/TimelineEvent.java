package org.ei.drishti.view.contract;

public class TimelineEvent {
    private String type;
    private String title;
    private String[] details;
    private String date;

    public TimelineEvent(String type, String title, String[] details, String date) {
        this.type = type;
        this.title = title;
        this.details = details;
        this.date = date;
    }
}
