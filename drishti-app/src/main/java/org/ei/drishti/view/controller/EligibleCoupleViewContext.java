package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import org.ei.drishti.domain.EligibleCouple;

public class EligibleCoupleViewContext {
    private EligibleCouple eligibleCouple;

    public EligibleCoupleViewContext(EligibleCouple eligibleCouple) {
        this.eligibleCouple = eligibleCouple;
    }

    public String get() {
        return new Gson().toJson(eligibleCouple);
    }
}
