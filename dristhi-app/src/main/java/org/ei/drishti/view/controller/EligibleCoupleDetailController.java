package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.view.contract.Child;
import org.ei.drishti.view.contract.ECDetail;
import org.ei.drishti.view.contract.ProfileTodo;
import org.ei.drishti.view.contract.TimelineEvent;
import org.ocpsoft.pretty.time.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EligibleCoupleDetailController {
    private final Context context;
    private String caseId;
    private final AllEligibleCouples allEligibleCouples;
    private AllAlerts allAlerts;
    private final AllTimelineEvents allTimelineEvents;
    private PrettyTime prettyTime;
    private CommCareClientService commCareClientService;

    public EligibleCoupleDetailController(Context context, String caseId, AllEligibleCouples allEligibleCouples, AllAlerts allAlerts, AllTimelineEvents allTimelineEvents, CommCareClientService commCareClientService) {
        this.context = context;
        this.caseId = caseId;
        this.allEligibleCouples = allEligibleCouples;
        this.allAlerts = allAlerts;
        this.allTimelineEvents = allTimelineEvents;
        this.commCareClientService = commCareClientService;
        prettyTime = new PrettyTime(new Date(), new Locale("short"));
    }

    public String get() {
        EligibleCouple eligibleCouple = allEligibleCouples.findByCaseID(caseId);
        List<List<ProfileTodo>> todosAndUrgentTodos = allAlerts.fetchAllActiveAlertsForCase(caseId);

        ECDetail ecContext = new ECDetail(caseId, eligibleCouple.wifeName(), eligibleCouple.village(), eligibleCouple.subCenter(), eligibleCouple.ecNumber(),
                false, null, new ArrayList<Child>(), eligibleCouple.details()).
                addTodos(todosAndUrgentTodos.get(0)).
                addUrgentTodos(todosAndUrgentTodos.get(1)).
                addTimelineEvents(getEvents());

        return new Gson().toJson(ecContext);
    }

    public void startCommCare(String formId, String caseId) {
        commCareClientService.start(context, formId, caseId);
    }

    private List<TimelineEvent> getEvents() {
        List<org.ei.drishti.domain.TimelineEvent> events = allTimelineEvents.forCase(caseId);
        List<TimelineEvent> ecTimeLines = new ArrayList<TimelineEvent>();
        for (org.ei.drishti.domain.TimelineEvent event : events) {
            String dateOfEvent = prettyTime.format(prettyTime.calculatePreciseDuration(event.referenceDate().toDate()).subList(0, 2)).replaceAll(" _", "");
            ecTimeLines.add(new TimelineEvent(event.type(), event.title(), new String[] { event.detail1(), event.detail2()}, dateOfEvent));
        }
        return ecTimeLines;
    }
}
