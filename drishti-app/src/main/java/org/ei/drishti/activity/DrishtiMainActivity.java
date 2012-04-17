package org.ei.drishti.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import org.ei.drishti.R;
import org.ei.drishti.adapter.AlertAdapter;
import org.ei.drishti.adapter.AlertFilterCriterion;
import org.ei.drishti.agent.HTTPAgent;
import org.ei.drishti.controller.AlertController;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.repository.*;
import org.ei.drishti.service.DrishtiService;
import org.ei.drishti.view.UpdateAlertsTask;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.ei.drishti.adapter.AlertFilterCriterion.*;
import static org.ei.drishti.util.Log.logVerbose;

public class DrishtiMainActivity extends Activity {
    private static DrishtiService drishtiService;
    private UpdateAlertsTask updateAlerts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logVerbose("Initializing ...");
        setContentView(R.layout.main);

        initSpinner();
        final AlertAdapter alertAdapter = new AlertAdapter(this, R.layout.list_item, new ArrayList<Alert>());
        ((ListView) findViewById(R.id.listView)).setAdapter(alertAdapter);

        updateAlerts = new UpdateAlertsTask(setupController(alertAdapter), (ProgressBar) findViewById(R.id.progressBar));
        updateAlerts.updateDisplay();
        updateAlerts.updateFromServer();
    }

    private void initSpinner() {
        Spinner filterSpinner = (Spinner) findViewById(R.id.filterSpinner);

        ArrayAdapter<AlertFilterCriterion> criteriaAdapter = new ArrayAdapter<AlertFilterCriterion>(this, android.R.layout.simple_spinner_item, asList(All, ANC, BCG, HEP, OPV));
        criteriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(criteriaAdapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AlertFilterCriterion criterion = (AlertFilterCriterion) parent.getItemAtPosition(position);
                updateAlerts.changeAlertFilterCriterion(criterion.visitCodePrefix());
                updateAlerts.updateDisplay();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public static void setDrishtiService(DrishtiService value) {
        drishtiService = value;
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
}
