package org.ei.drishti.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;
import com.markupartist.android.widget.ActionBar;
import org.ei.drishti.R;
import org.ei.drishti.adapter.AlertAdapter;
import org.ei.drishti.adapter.AlertFilterCriterion;
import org.ei.drishti.agent.HTTPAgent;
import org.ei.drishti.controller.AlertController;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.matcher.MatchByBeneficiaryOrThaayiCard;
import org.ei.drishti.matcher.MatchByVisitCode;
import org.ei.drishti.repository.*;
import org.ei.drishti.service.DrishtiService;
import org.ei.drishti.util.AlertFilter;
import org.ei.drishti.view.UpdateAlertsTask;

import java.util.ArrayList;

import static org.ei.drishti.adapter.AlertFilterCriterion.valuesInOrder;
import static org.ei.drishti.util.Log.logVerbose;

public class DrishtiMainActivity extends Activity {
    private static DrishtiService drishtiService;
    private UpdateAlertsTask updateAlerts;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private AlertController controller;
    private AlertFilter filter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logVerbose("Initializing ...");
        setContentView(R.layout.main);

        final AlertAdapter alertAdapter = new AlertAdapter(this, R.layout.list_item, new ArrayList<Alert>());
        controller = setupController(alertAdapter);
        updateAlerts = new UpdateAlertsTask(controller, (ProgressBar) findViewById(R.id.progressBar));

        filter = new AlertFilter(alertAdapter);
        filter.addFilter(new MatchByVisitCode(initSpinner()));
        filter.addFilter(new MatchByBeneficiaryOrThaayiCard((EditText) findViewById(R.id.searchEditText)));

        ListView alertsList = (ListView) findViewById(R.id.listView);
        alertsList.setAdapter(alertAdapter);
        alertsList.setTextFilterEnabled(true);

        ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setTitle("Workplan");
        actionBar.addAction(new DialogAction(this));

        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                controller.changeUser();
                Toast.makeText(getApplicationContext(), "Changes saved.", Toast.LENGTH_LONG).show();
            }
        };
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAlerts.updateDisplay();
        updateAlerts.updateFromServer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updateMenuItem:
                updateAlerts.updateFromServer();
                return true;
            case R.id.settingsMenuItem:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public AlertController setupController(AlertAdapter alertAdapter) {
        SettingsRepository settingsRepository = new SettingsRepository();
        AlertRepository alertRepository = new AlertRepository();
        new Repository(getApplicationContext(), settingsRepository, alertRepository);
        AllSettings allSettings = new AllSettings(PreferenceManager.getDefaultSharedPreferences(this), settingsRepository);
        AllAlerts allAlerts = new AllAlerts(alertRepository);
        if (drishtiService == null) {
            drishtiService = new DrishtiService(new HTTPAgent(), "http://drishti.modilabs.org");
        }
        return new AlertController(drishtiService, allSettings, allAlerts, alertAdapter);
    }

    public static void setDrishtiService(DrishtiService value) {
        drishtiService = value;
    }

    private Spinner initSpinner() {
        Spinner filterSpinner = (Spinner) findViewById(R.id.filterSpinner);

        ArrayAdapter<AlertFilterCriterion> criteriaAdapter = new ArrayAdapter<AlertFilterCriterion>(this, android.R.layout.simple_spinner_item, valuesInOrder());
        criteriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(criteriaAdapter);

        return filterSpinner;

    }
}
