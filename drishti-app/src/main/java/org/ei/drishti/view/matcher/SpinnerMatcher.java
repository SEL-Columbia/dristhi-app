package org.ei.drishti.view.matcher;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.view.AfterChangeListener;

public abstract class SpinnerMatcher implements Matcher {
    private Spinner spinner;
    private Object currentValue;

    protected SpinnerMatcher(Spinner spinner) {
        this.spinner = spinner;
        currentValue = spinner.getSelectedItem();
    }

    public void setOnChangeListener(final AfterChangeListener afterChangeListener) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentValue = parent.getItemAtPosition(position);
                afterChangeListener.afterChangeHappened();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public Object currentValue() {
        return currentValue;
    }

    public abstract boolean matches(Alert alert);
}
