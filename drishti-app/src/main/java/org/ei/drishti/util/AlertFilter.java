package org.ei.drishti.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import org.ei.drishti.adapter.AlertAdapter;
import org.ei.drishti.adapter.AlertFilterCriterion;
import org.ei.drishti.domain.Alert;

import java.util.ArrayList;
import java.util.List;

public class AlertFilter {
    private AlertAdapter alertAdapter;
    private String currentVisitCodePrefix = AlertFilterCriterion.All.visitCodePrefix();
    private String currentText = "";

    public AlertFilter(AlertAdapter alertAdapter) {
        this.alertAdapter = alertAdapter;

        alertAdapter.setOnDataSourceChanged(new AlertAdapter.OnDataSourceChangedListener(){
            public void dataSourceChanged() {
                filter();
            }
        });
    }

    public void addSpinner(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AlertFilterCriterion criterion = (AlertFilterCriterion) parent.getItemAtPosition(position);
                currentVisitCodePrefix = criterion.visitCodePrefix();
                filter();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void addTextFilter(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable text) {
                currentText = text.toString().toLowerCase();
                filter();
            }
        });
    }

    private void filter() {
        List<Alert> alerts = new ArrayList<Alert>();
        for (Alert alert : alertAdapter.getAlerts()) {
            if (alert.visitCode().startsWith(currentVisitCodePrefix) &&
                    (alert.beneficiaryName().toLowerCase().contains(currentText) || alert.thaayiCardNo().toLowerCase().contains(currentText))) {
                alerts.add(alert);
            }
        }
        alertAdapter.refreshDisplayWithoutUpdatingAlerts(alerts);
    }
}
