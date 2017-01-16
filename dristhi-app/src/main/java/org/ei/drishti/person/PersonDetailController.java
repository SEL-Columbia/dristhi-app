package org.ei.drishti.person;

import android.content.Context;
import com.google.gson.Gson;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.util.TimelineEventComparator;
import org.ei.drishti.view.contract.TimelineEvent;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonDetailController {

    private final Context context;
    private String caseId;
    private final AllPersons allPersons;
    private final AllTimelineEvents allTimelineEvents;

    public PersonDetailController(Context context, String caseId, AllPersons allPersons,
                                          AllTimelineEvents allTimelineEvents) {
        this.context = context;
        this.caseId = caseId;
        this.allPersons = allPersons;
        this.allTimelineEvents = allTimelineEvents;
    }

    public String get() {
        Person person = allPersons.findByCaseID(caseId);
        PersonDetail personContext = new PersonDetail(caseId, person.getDetails()).addTimelineEvents(getEvents());
        return new Gson().toJson(personContext);
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
