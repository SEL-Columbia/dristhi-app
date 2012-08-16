package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.view.contract.*;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.ocpsoft.pretty.time.PrettyTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ANCDetailController {
    private final Context context;
    private final String caseId;
    private final AllEligibleCouples allEligibleCouples;
    private final AllBeneficiaries allBeneficiaries;
    private final AllTimelineEvents allTimelineEvents;
    private CommCareClientService commCareClientService;
    private PrettyTime prettyTime;

    public ANCDetailController(Context context, String caseId, AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries, AllTimelineEvents allTimelineEvents, CommCareClientService commCareClientService) {
        this.context = context;
        this.caseId = caseId;
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.allTimelineEvents = allTimelineEvents;
        this.commCareClientService = commCareClientService;
        prettyTime = new PrettyTime(DateUtil.today().toDate(), new Locale("short"));
    }

    public String get() {
        Mother mother = allBeneficiaries.findMother(caseId);
        EligibleCouple couple = allEligibleCouples.findByCaseID(mother.ecCaseId());

        LocalDate lmp = LocalDate.parse(mother.referenceDate());
        String edd = lmp.plusWeeks(40).toString();
        Months numberOfMonthsPregnant = Months.monthsBetween(lmp, DateUtil.today());

        ANCDetail detail = new ANCDetail(caseId, mother.thaayiCardNumber(), couple.wifeName(),
                new LocationDetails(couple.village(), couple.subCenter()),
                new PregnancyDetails(mother.isHighRisk(), "Anaemic", String.valueOf(numberOfMonthsPregnant.getMonths()), edd),
                new FacilityDetails(mother.deliveryPlace(), "----", "Shiwani")).addTimelineEvents(getEvents());

        return new Gson().toJson(detail);
    }

    public void startCommCare(String formId, String caseId) {
        commCareClientService.start(context, formId, caseId);
    }

    private List<TimelineEvent> getEvents() {
        List<org.ei.drishti.domain.TimelineEvent> events = allTimelineEvents.forCase(caseId);
        List<TimelineEvent> timelineEvents = new ArrayList<TimelineEvent>();
        for (org.ei.drishti.domain.TimelineEvent event : events) {
            String dateOfEvent = prettyTime.format(prettyTime.calculatePreciseDuration(event.referenceDate().toDate()).subList(0, 2)).replaceAll(" _", "");
            timelineEvents.add(new TimelineEvent(event.type(), event.title(), new String[]{event.detail1(), event.detail2()}, dateOfEvent));
        }
        return timelineEvents;
    }
}
