package org.ei.drishti.view.contract;

import java.util.ArrayList;
import java.util.List;

public class ANCDetail {
    private String caseId;
    private String thaayiCardNumber;
    private String womanName;

    private final LocationDetails location;
    private final PregnancyDetails pregnancyDetails;
    private final FacilityDetails facilityDetails;

    private List<Reminder> alerts;
    private List<Reminder> todos;
    private List<TimelineEvent> timelineEvents;

    public ANCDetail(String caseId, String thaayiCardNumber, String womanName, LocationDetails location, PregnancyDetails pregnancyDetails, FacilityDetails facilityDetails) {
        this.caseId = caseId;
        this.thaayiCardNumber = thaayiCardNumber;
        this.womanName = womanName;
        this.location = location;
        this.pregnancyDetails = pregnancyDetails;
        this.facilityDetails = facilityDetails;

        this.alerts = new ArrayList<Reminder>();
        this.todos = new ArrayList<Reminder>();
        this.timelineEvents = new ArrayList<TimelineEvent>();
    }
}

