package org.ei.drishti.controller;

import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.view.adapter.AlertAdapter;

public class AlertController {
    private AllAlerts allAlerts;
    private AlertAdapter adapter;

    public AlertController(AlertAdapter adapter, AllAlerts allAlerts) {
        this.allAlerts = allAlerts;
        this.adapter = adapter;
    }

    public void refreshAlertsFromDB() {
        adapter.updateAlerts(allAlerts.fetchAlerts());
    }
}
