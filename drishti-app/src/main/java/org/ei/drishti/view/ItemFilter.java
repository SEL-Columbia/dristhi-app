package org.ei.drishti.view;

import org.ei.drishti.view.adapter.ListAdapter;
import org.ei.drishti.view.matcher.Matcher;

import java.util.ArrayList;
import java.util.List;

public class ItemFilter<T> {
    private ListAdapter<T> alertAdapter;
    private final ListAdapter.OnDataSourceChangedListener dataSourceChangedListener;
    private List<Matcher<?, T>> matchers = new ArrayList<Matcher<?, T>>();

    public ItemFilter(ListAdapter<T> alertAdapter) {
        this.alertAdapter = alertAdapter;

        dataSourceChangedListener = new ListAdapter.OnDataSourceChangedListener() {
            public void dataSourceChanged() {
                filter();
            }
        };
        alertAdapter.setOnDataSourceChanged(dataSourceChangedListener);
    }

    public void addFilter(Matcher<?, T> matcher) {
        this.matchers.add(matcher);

        matcher.setOnChangeListener(new AfterChangeListener() {
            public void afterChangeHappened() {
                filter();
            }
        });
    }

    private void filter() {
        List<T> alerts = new ArrayList<T>();
        for (T alert : alertAdapter.getItems()) {
            if (allMatchersMatch(alert)) {
                alerts.add(alert);
            }
        }
        alertAdapter.refreshDisplayWithoutUpdatingItems(alerts);
    }

    private boolean allMatchersMatch(T alert) {
        for (Matcher matcher : matchers) {
            if (!matcher.matches(alert)) {
                return false;
            }
        }
        return true;
    }

}
