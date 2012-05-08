package org.ei.drishti.controller;

import org.ei.drishti.domain.Alert;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.view.adapter.ListAdapter;

public class AlertController {
    private AllAlerts allAlerts;
    private ListAdapter<Alert> adapter;

    public AlertController(ListAdapter<Alert> adapter, AllAlerts allAlerts) {
        this.allAlerts = allAlerts;
        this.adapter = adapter;
    }

    public void refreshAlertsFromDB() {
        adapter.updateItems(allAlerts.fetchAlerts());
    }
}
