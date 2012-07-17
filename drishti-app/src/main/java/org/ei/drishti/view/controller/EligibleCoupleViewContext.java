package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import org.ei.drishti.view.domain.ECContext;

public class EligibleCoupleViewContext {
    private ECContext ecContext;

    public EligibleCoupleViewContext(ECContext ecContext) {
        this.ecContext = ecContext;
    }

    public String get() {
        return new Gson().toJson(ecContext);
    }
}
