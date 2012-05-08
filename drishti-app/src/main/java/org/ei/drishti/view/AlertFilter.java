package org.ei.drishti.view;

import org.ei.drishti.view.adapter.ListAdapter;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.view.matcher.Matcher;

import java.util.ArrayList;
import java.util.List;

public class AlertFilter {
    private ListAdapter<Alert> alertAdapter;
    private final ListAdapter.OnDataSourceChangedListener dataSourceChangedListener;
    private List<Matcher> matchers = new ArrayList<Matcher>();

    public AlertFilter(ListAdapter<Alert> alertAdapter) {
        this.alertAdapter = alertAdapter;

        dataSourceChangedListener = new ListAdapter.OnDataSourceChangedListener() {
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
        for (Alert alert : alertAdapter.getItems()) {
            if (allMatchersMatch(alert)) {
                alerts.add(alert);
            }
        }
        alertAdapter.refreshDisplayWithoutUpdatingItems(alerts);
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
