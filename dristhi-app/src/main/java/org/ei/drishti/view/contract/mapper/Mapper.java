package org.ei.drishti.view.contract.mapper;

import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.view.contract.Beneficiary;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static List<Beneficiary> mapFromEC(List<EligibleCouple> ecs) {
        List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
        for (EligibleCouple ec : ecs) {
            beneficiaries.add(new Beneficiary(ec.caseId(), ec.wifeName(), ec.husbandName(), "", ec.ecNumber(), ec.village(), ec.isHighPriority()));
        }
        return beneficiaries;
    }
}
