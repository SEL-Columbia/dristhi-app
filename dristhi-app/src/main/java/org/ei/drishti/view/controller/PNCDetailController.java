package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.util.TimelineEventComparator;
import org.ei.drishti.view.contract.*;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.ocpsoft.pretty.time.Duration;
import org.ocpsoft.pretty.time.PrettyTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.min;

public class PNCDetailController {
    private final Context context;
    private final String caseId;
    private final AllEligibleCouples allEligibleCouples;
    private final AllBeneficiaries allBeneficiaries;
    private AllAlerts allAlerts;
    private final AllTimelineEvents allTimelineEvents;
    private PrettyTime prettyTime;

    public PNCDetailController(Context context, String caseId, AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries, AllAlerts allAlerts, AllTimelineEvents allTimelineEvents) {
        this.context = context;
        this.caseId = caseId;
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.allAlerts = allAlerts;
        this.allTimelineEvents = allTimelineEvents;
        prettyTime = new PrettyTime(DateUtil.today().toDate(), new Locale("short"));
    }

    public String get() {
        Mother mother = allBeneficiaries.findMother(caseId);
        EligibleCouple couple = allEligibleCouples.findByCaseID(mother.ecCaseId());
        List<List<ProfileTodo>> todosAndUrgentTodos = allAlerts.fetchAllActiveAlertsForCase(caseId);

        LocalDate deliveryDate = LocalDate.parse(mother.referenceDate());
        Days postPartumDuration = Days.daysBetween(deliveryDate, DateUtil.today());

        PNCDetail detail = new PNCDetail(caseId, mother.thaayiCardNumber(),
                new CoupleDetails(couple.wifeName(), couple.husbandName(), couple.ecNumber(), couple.isOutOfArea()).withCaste(couple.getDetail("caste")).withEconomicStatus(couple.getDetail("economicStatus")),
                new LocationDetails(couple.village(), couple.subCenter()),
                new PregnancyOutcomeDetails(deliveryDate.toString(), postPartumDuration.getDays()))
                .addTimelineEvents(getEvents())
                .addTodos(todosAndUrgentTodos.get(0))
                .addUrgentTodos(todosAndUrgentTodos.get(1))
                .addExtraDetails(mother.details());

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
}
