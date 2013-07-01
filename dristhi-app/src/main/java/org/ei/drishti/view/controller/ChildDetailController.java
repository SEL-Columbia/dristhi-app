package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.util.TimelineEventComparator;
import org.ei.drishti.view.contract.*;
import org.joda.time.LocalDate;
import org.ocpsoft.pretty.time.Duration;
import org.ocpsoft.pretty.time.PrettyTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.min;

public class ChildDetailController {
    private final Context context;
    private final String caseId;
    private final AllEligibleCouples allEligibleCouples;
    private final AllBeneficiaries allBeneficiaries;
    private final AllAlerts allAlerts;
    private final AllTimelineEvents allTimelineEvents;
    private PrettyTime prettyTime;

    public ChildDetailController(Context context, String caseId, AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries, AllAlerts allAlerts, AllTimelineEvents allTimelineEvents) {
        this.context = context;
        this.caseId = caseId;
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.allAlerts = allAlerts;
        this.allTimelineEvents = allTimelineEvents;
        prettyTime = new PrettyTime(DateUtil.today().toDate(), new Locale("short"));
    }

    public String get() {
        Child child = allBeneficiaries.findChild(caseId);
        Mother mother = allBeneficiaries.findMotherWithOpenStatus(child.motherCaseId());
        EligibleCouple couple = allEligibleCouples.findByCaseID(mother.ecCaseId());
        List<List<ProfileTodo>> todosAndUrgentTodos = allAlerts.fetchAllActiveAlertsForCase(caseId);

        LocalDate deliveryDate = LocalDate.parse(child.dateOfBirth());

        ChildDetail detail = new ChildDetail(caseId, mother.thaayiCardNumber(),
                new CoupleDetails(couple.wifeName(), couple.husbandName(), couple.ecNumber(), couple.isOutOfArea()),
                new LocationDetails(couple.village(), couple.subCenter()),
                new BirthDetails(deliveryDate.toString(), calculateAge(deliveryDate), child.gender()))
                .addTimelineEvents(getEvents())
                .addTodos(todosAndUrgentTodos.get(0))
                .addUrgentTodos(todosAndUrgentTodos.get(1))
                .addExtraDetails(child.details());

        return new Gson().toJson(detail);
    }

    public void markTodoAsCompleted(String caseId, String visitCode) {
        allAlerts.markAsCompleted(caseId, visitCode, LocalDate.now().toString());
    }

    private List<TimelineEvent> getEvents() {
        List<org.ei.drishti.domain.TimelineEvent> events = allTimelineEvents.forCase(caseId);
        List<TimelineEvent> timelineEvents = new ArrayList<TimelineEvent>();

        Collections.sort(events, new TimelineEventComparator());

        for (org.ei.drishti.domain.TimelineEvent event : events) {
            timelineEvents.add(new TimelineEvent(event.type(), event.title(), new String[]{event.detail1(), event.detail2()}, formatDate(event.referenceDate())));
        }

        return timelineEvents;
    }

    private String formatDate(LocalDate date) {
        List<Duration> durationComponents = prettyTime.calculatePreciseDuration(date.toDate());
        return prettyTime.format(durationComponents.subList(0, min(durationComponents.size(), 2))).replaceAll(" _", "");
    }

    private String calculateAge(LocalDate deliveryDate) {
        PrettyTime time = new PrettyTime(DateUtil.today().toDate());
        List<Duration> durationComponents = time.calculatePreciseDuration(deliveryDate.toDate());
        return time.format(durationComponents.subList(0, min(durationComponents.size(), 1))).replaceAll(" ago", "");
    }
}
