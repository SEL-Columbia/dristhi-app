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
        List<Matcher> activeMatchers = activeMatchers();

        List<T> alerts = new ArrayList<T>();
        for (T alert : alertAdapter.getItems()) {
            if (allMatchersMatch(activeMatchers, alert)) {
                alerts.add(alert);
            }
        }
        alertAdapter.refreshDisplayWithoutUpdatingItems(alerts);
    }

    private boolean allMatchersMatch(List<Matcher> activeMatchers, T alert) {
        for (Matcher matcher : activeMatchers) {
            if (!matcher.matches(alert)) {
                return false;
            }
        }
        return true;
    }

    private List<Matcher> activeMatchers() {
        ArrayList activeMatchers = new ArrayList<Matcher>();
        for (Matcher matcher : matchers) {
            if (matcher.isActive()) {
                activeMatchers.add(matcher);
            }
        }
        return activeMatchers;
    }

}
