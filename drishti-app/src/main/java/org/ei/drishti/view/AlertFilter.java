package org.ei.drishti.view;

import org.ei.drishti.view.adapter.AlertAdapter;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.view.matcher.Matcher;

import java.util.ArrayList;
import java.util.List;

public class AlertFilter {
    private AlertAdapter alertAdapter;
    private final AlertAdapter.OnDataSourceChangedListener dataSourceChangedListener;
    private List<Matcher> matchers = new ArrayList<Matcher>();

    public AlertFilter(AlertAdapter alertAdapter) {
        this.alertAdapter = alertAdapter;

        dataSourceChangedListener = new AlertAdapter.OnDataSourceChangedListener() {
            public void dataSourceChanged() {
                filter();
            }
        };
        alertAdapter.setOnDataSourceChanged(dataSourceChangedListener);
    }

    public void addFilter(Matcher matcher) {
        this.matchers.add(matcher);

        matcher.setOnChangeListener(new AfterChangeListener() {
            public void afterChangeHappened() {
                filter();
            }
        });
    }

    private void filter() {
        List<Alert> alerts = new ArrayList<Alert>();
        for (Alert alert : alertAdapter.getAlerts()) {
            if (allMatchersMatch(alert)) {
                alerts.add(alert);
            }
        }
        alertAdapter.refreshDisplayWithoutUpdatingAlerts(alerts);
    }

    private boolean allMatchersMatch(Alert alert) {
        for (Matcher matcher : matchers) {
            if (!matcher.matches(alert)) {
                return false;
            }
        }
        return true;
    }

}
