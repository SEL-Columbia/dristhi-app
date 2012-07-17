package org.ei.drishti.view.domain;

public class ECTimeline {
    private String event;
    private String[] details;
    private String date;

    public ECTimeline(String event, String[] details, String date) {
        this.event = event;
        this.details = details;
        this.date = date;
    }
}
