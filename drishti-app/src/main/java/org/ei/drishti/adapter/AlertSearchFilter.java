package org.ei.drishti.adapter;

import android.widget.Filter;
import org.ei.drishti.domain.Alert;

import java.util.ArrayList;
import java.util.List;

class AlertSearchFilter extends Filter {
    private AlertAdapter alertAdapter;
    private List<Alert> alerts;

    public AlertSearchFilter(AlertAdapter alertAdapter, List<Alert> alerts) {
        this.alertAdapter = alertAdapter;
        this.alerts = alerts;
    }

    @Override
    protected FilterResults performFiltering(CharSequence text) {
        if (text == null || text.toString().length() <= 0) {
            FilterResults results = new FilterResults();
            results.values = alerts;
            results.count = alerts.size();
            return results;
        }

        String textForFilter = text.toString().toLowerCase();
        ArrayList<Alert> filteredItems = new ArrayList<Alert>();

        for (Alert listItem : alerts) {
            if (listItem.beneficiaryName().toLowerCase().contains(textForFilter))
                filteredItems.add(listItem);
        }

        FilterResults results = new FilterResults();
        results.count = filteredItems.size();
        results.values = filteredItems;
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        alertAdapter.refreshDisplayWithoutUpdatingAlerts(new ArrayList<Alert>((List<Alert>) results.values));
    }
}
