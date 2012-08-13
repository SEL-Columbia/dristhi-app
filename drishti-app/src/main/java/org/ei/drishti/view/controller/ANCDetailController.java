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
import org.ei.drishti.view.contract.ANCDetail;
import org.ei.drishti.view.contract.FacilityDetails;
import org.ei.drishti.view.contract.LocationDetails;
import org.ei.drishti.view.contract.PregnancyDetails;
import org.joda.time.LocalDate;
import org.joda.time.Months;

public class ANCDetailController {
    private final Context context;
    private final String caseId;
    private final AllEligibleCouples allEligibleCouples;
    private final AllBeneficiaries allBeneficiaries;
    private final AllTimelineEvents allTimelineEvents;
    private CommCareClientService commCareClientService;

    public ANCDetailController(Context context, String caseId, AllEligibleCouples allEligibleCouples, AllBeneficiaries allBeneficiaries, AllTimelineEvents allTimelineEvents, CommCareClientService commCareClientService) {
        this.context = context;
        this.caseId = caseId;
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.allTimelineEvents = allTimelineEvents;
        this.commCareClientService = commCareClientService;
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
                new FacilityDetails(mother.deliveryPlace(), "----", "Shiwani"));

        return new Gson().toJson(detail);
    }

    public void startCommCare(String formId, String caseId) {
        commCareClientService.start(context, formId, caseId);
    }
}
