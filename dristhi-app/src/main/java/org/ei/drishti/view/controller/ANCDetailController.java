package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.AllConstants;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.util.TimelineEventComparator;
import org.ei.drishti.view.activity.CameraLaunchActivity;
import org.ei.drishti.view.contract.*;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.ocpsoft.pretty.time.Duration;
import org.ocpsoft.pretty.time.PrettyTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.min;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.drishti.AllConstants.*;

public class ANCDetailController {
    public static final int DURATION_OF_PREGNANCY_IN_WEEKS = 40;
    private final Context context;
    private final String caseId;
    private final AllEligibleCouples allEligibleCouples;
    private final AllBeneficiaries allBeneficiaries;
    private AllAlerts allAlerts;
    private final AllTimelineEvents allTimelineEvents;
    private PrettyTime prettyTime;

    public ANCDetailController(Context context, String caseId, AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries, AllAlerts allAlerts, AllTimelineEvents allTimelineEvents) {
        this.context = context;
        this.caseId = caseId;
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.allAlerts = allAlerts;
        this.allTimelineEvents = allTimelineEvents;
        prettyTime = new PrettyTime(DateUtil.today().toDate(), new Locale("short"));
    }

    public String get() {
        Mother mother = allBeneficiaries.findMotherWithOpenStatus(caseId);
        EligibleCouple couple = allEligibleCouples.findByCaseID(mother.ecCaseId());
        List<List<ProfileTodo>> todosAndUrgentTodos = allAlerts.fetchAllActiveAlertsForCase(caseId);

        LocalDate lmp = LocalDate.parse(mother.referenceDate());
        String edd = lmp.plusWeeks(DURATION_OF_PREGNANCY_IN_WEEKS).toString();
        Months numberOfMonthsPregnant = Months.monthsBetween(lmp, DateUtil.today());
        String photoPath = isBlank(couple.photoPath()) ? DEFAULT_WOMAN_IMAGE_PLACEHOLDER_PATH : couple.photoPath();

        int months = numberOfMonthsPregnant.getMonths();
        LocalDate eddDate = LocalDate.parse(edd);
        Days daysPastEdd = Days.daysBetween(eddDate, DateUtil.today());
        ANCDetail detail = new ANCDetail(caseId, mother.thayiCardNumber(),
                new CoupleDetails(couple.wifeName(), couple.husbandName(), couple.ecNumber(),
                        couple.isOutOfArea())
                        .withCaste(couple.details().get("caste"))
                        .withEconomicStatus(couple.details().get("economicStatus"))
                        .withPhotoPath(photoPath),
                new LocationDetails(couple.village(), couple.subCenter()),
                new PregnancyDetails(String.valueOf(months), edd, daysPastEdd.getDays()))
                .addTimelineEvents(getEvents())
                .addTodos(todosAndUrgentTodos.get(0))
                .addUrgentTodos(todosAndUrgentTodos.get(1))
                .addExtraDetails(mother.details());

        return new Gson().toJson(detail);
    }

    public void markTodoAsCompleted(String caseId, String visitCode) {
        allAlerts.markAsCompleted(caseId, visitCode, LocalDate.now().toString());
    }

    public void takePhoto() {
        Intent intent = new Intent(context, CameraLaunchActivity.class);
        intent.putExtra(AllConstants.TYPE, WOMAN_TYPE);
        Mother mother = allBeneficiaries.findMotherWithOpenStatus(caseId);
        intent.putExtra(ENTITY_ID, mother.ecCaseId());
        context.startActivity(intent);
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
