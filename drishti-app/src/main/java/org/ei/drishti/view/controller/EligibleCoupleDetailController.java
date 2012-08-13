package org.ei.drishti.view.controller;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import com.google.gson.Gson;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.view.contract.ECContext;
import org.ei.drishti.view.contract.TimelineEvent;
import org.ocpsoft.pretty.time.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;

public class EligibleCoupleDetailController {
    private final Context context;
    private String caseId;
    private final AllEligibleCouples allEligibleCouples;
    private final AllBeneficiaries allBeneficiaries;
    private final AllTimelineEvents allTimelineEvents;
    private PrettyTime prettyTime;

    public EligibleCoupleDetailController(Context context, String caseId, AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries, AllTimelineEvents allTimelineEvents) {
        this.context = context;
        this.caseId = caseId;
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.allTimelineEvents = allTimelineEvents;
        prettyTime = new PrettyTime(new Date(), new Locale("short"));
    }

    public String get() {
        EligibleCouple eligibleCouple = allEligibleCouples.findByCaseID(caseId);

        ECContext ecContext = new ECContext(eligibleCouple.wifeName(), eligibleCouple.village(), eligibleCouple.subCenter(), eligibleCouple.ecNumber(),
                false, null, eligibleCouple.currentMethod(), null, null, getEvents());
        return new Gson().toJson(ecContext);
    }

    public void startCommCare() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(ComponentName.unflattenFromString("org.commcare.dalvik/.activities.CommCareHomeActivity"));
        intent.addCategory("android.intent.category.Launcher");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context.getApplicationContext(), "CommCare ODK is not installed.", LENGTH_SHORT).show();
        }
    }

    public void startContacts() {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/")));
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
