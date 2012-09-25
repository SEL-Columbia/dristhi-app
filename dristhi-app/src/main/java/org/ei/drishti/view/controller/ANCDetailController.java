package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import org.ei.drishti.domain.*;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.view.contract.*;
import org.ei.drishti.view.contract.TimelineEvent;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.ocpsoft.pretty.time.Duration;
import org.ocpsoft.pretty.time.PrettyTime;

import java.util.*;

import static java.lang.Math.min;

public class ANCDetailController {
    private final Context context;
    private final String caseId;
    private final AllEligibleCouples allEligibleCouples;
    private final AllBeneficiaries allBeneficiaries;
    private AllAlerts allAlerts;
    private final AllTimelineEvents allTimelineEvents;
    private CommCareClientService commCareClientService;
    private PrettyTime prettyTime;

    public ANCDetailController(Context context, String caseId, AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries, AllAlerts allAlerts, AllTimelineEvents allTimelineEvents, CommCareClientService commCareClientService) {
        this.context = context;
        this.caseId = caseId;
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.allAlerts = allAlerts;
        this.allTimelineEvents = allTimelineEvents;
        this.commCareClientService = commCareClientService;
        prettyTime = new PrettyTime(DateUtil.today().toDate(), new Locale("short"));
    }

    public String get() {
        Mother mother = allBeneficiaries.findMother(caseId);
        EligibleCouple couple = allEligibleCouples.findByCaseID(mother.ecCaseId());
        List<List<ProfileTodo>> todosAndUrgentTodos = allAlerts.fetchAllActiveAlertsForCase(caseId);

        LocalDate lmp = LocalDate.parse(mother.referenceDate());
        String edd = lmp.plusWeeks(40).toString();
        Months numberOfMonthsPregnant = Months.monthsBetween(lmp, DateUtil.today());

        int months = numberOfMonthsPregnant.getMonths();
        ANCDetail detail = new ANCDetail(caseId, mother.thaayiCardNumber(),
                new CoupleDetails(couple.wifeName(), couple.husbandName(), couple.ecNumber(), couple.isOutOfArea()),
                new LocationDetails(couple.village(), couple.subCenter()),
                new PregnancyDetails(String.valueOf(months), edd))
                .addTimelineEvents(getEvents())
                .addTodos(todosAndUrgentTodos.get(0))
                .addUrgentTodos(todosAndUrgentTodos.get(1))
                .addExtraDetails(mother.details());

        return new Gson().toJson(detail);
    }

    public void startCommCare(String formId, String caseId) {
        commCareClientService.start(context, formId, caseId);
    }

    public void markTodoAsCompleted(String caseId, String visitCode) {
        allAlerts.markAsCompleted(caseId, visitCode, LocalDate.now().toString());
    }

    private List<TimelineEvent> getEvents() {
        List<org.ei.drishti.domain.TimelineEvent> events = allTimelineEvents.forCase(caseId);
        List<TimelineEvent> timelineEvents = new ArrayList<TimelineEvent>();

        for (org.ei.drishti.domain.TimelineEvent event : events) {
            timelineEvents.add(new TimelineEvent(event.type(), event.title(), new String[]{event.detail1(), event.detail2()}, formatDate(event.referenceDate())));
        }
        Collections.reverse(timelineEvents);

        return timelineEvents;
    }

    private String formatDate(LocalDate date) {
        List<Duration> durationComponents = prettyTime.calculatePreciseDuration(date.toDate());
        return prettyTime.format(durationComponents.subList(0, min(durationComponents.size(), 2))).replaceAll(" _", "");
    }
}
