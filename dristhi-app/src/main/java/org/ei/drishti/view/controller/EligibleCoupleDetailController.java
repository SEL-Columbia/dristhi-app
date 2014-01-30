package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.AllConstants;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.util.TimelineEventComparator;
import org.ei.drishti.view.activity.CameraLaunchActivity;
import org.ei.drishti.view.contract.Child;
import org.ei.drishti.view.contract.CoupleDetails;
import org.ei.drishti.view.contract.ECDetail;
import org.ei.drishti.view.contract.TimelineEvent;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.ei.drishti.AllConstants.ENTITY_ID;
import static org.ei.drishti.AllConstants.WOMAN_TYPE;

public class EligibleCoupleDetailController {
    private final Context context;
    private String caseId;
    private final AllEligibleCouples allEligibleCouples;
    private final AllTimelineEvents allTimelineEvents;

    public EligibleCoupleDetailController(Context context, String caseId, AllEligibleCouples allEligibleCouples,
                                          AllTimelineEvents allTimelineEvents) {
        this.context = context;
        this.caseId = caseId;
        this.allEligibleCouples = allEligibleCouples;
        this.allTimelineEvents = allTimelineEvents;
    }

    public String get() {
        EligibleCouple eligibleCouple = allEligibleCouples.findByCaseID(caseId);

        ECDetail ecContext = new ECDetail(caseId, eligibleCouple.village(), eligibleCouple.subCenter(), eligibleCouple.ecNumber(),
                eligibleCouple.isHighPriority(), null, eligibleCouple.photoPath(), new ArrayList<Child>(), new CoupleDetails(eligibleCouple.wifeName(),
                eligibleCouple.husbandName(), eligibleCouple.ecNumber(), eligibleCouple.isOutOfArea()),
                eligibleCouple.details()).
                addTimelineEvents(getEvents());

        return new Gson().toJson(ecContext);
    }

    public void takePhoto() {
        Intent intent = new Intent(context, CameraLaunchActivity.class);
        intent.putExtra(AllConstants.TYPE, WOMAN_TYPE);
        intent.putExtra(ENTITY_ID, caseId);
        context.startActivity(intent);
    }

    private List<TimelineEvent> getEvents() {
        List<org.ei.drishti.domain.TimelineEvent> events = allTimelineEvents.forCase(caseId);
        List<TimelineEvent> timelineEvents = new ArrayList<TimelineEvent>();
        Collections.sort(events, new TimelineEventComparator());

        for (org.ei.drishti.domain.TimelineEvent event : events) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-YYYY");
            timelineEvents.add(new TimelineEvent(event.type(), event.title(), new String[]{event.detail1(), event.detail2()}, event.referenceDate().toString(dateTimeFormatter)));
        }

        return timelineEvents;
    }
}
