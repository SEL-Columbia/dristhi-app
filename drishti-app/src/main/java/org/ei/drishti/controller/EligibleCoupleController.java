package org.ei.drishti.controller;

import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.view.adapter.ListAdapter;

public class EligibleCoupleController {
    private final ListAdapter<EligibleCouple> adapter;
    private final AllEligibleCouples allEligibleCouples;

    public EligibleCoupleController(ListAdapter<EligibleCouple> adapter, AllEligibleCouples allEligibleCouples) {
        this.adapter = adapter;
        this.allEligibleCouples = allEligibleCouples;
    }

    public void refreshAlertsFromDB() {
        adapter.updateItems(allEligibleCouples.fetchAll());
    }
}
