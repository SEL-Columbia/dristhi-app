package org.ei.drishti.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import de.akquinet.android.androlog.Log;
import org.ei.drishti.R;
import org.ei.drishti.adapter.AlertAdapter;
import org.ei.drishti.agent.HTTPAgent;
import org.ei.drishti.controller.AlertController;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.repository.*;
import org.ei.drishti.service.DrishtiService;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class DrishtiMainActivity extends Activity {
    private static DrishtiService drishtiService;
    private Lazy<AlertController> lazyAlertController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.init();
        Log.i(this, "Initializing ...");
        setContentView(R.layout.main);

        final AlertAdapter alertAdapter = new AlertAdapter(this, R.layout.list_item, new ArrayList<Alert>());
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(alertAdapter);

        lazyAlertController = new Lazy<AlertController>() {
            @Override
            public AlertController setupPayload() {
                SettingsRepository settingsRepository = new SettingsRepository();
                AlertRepository alertRepository = new AlertRepository();
                new Repository(getApplicationContext(), settingsRepository, alertRepository);
                AllSettings allSettings = new AllSettings(settingsRepository);
                AllAlerts allAlerts = new AllAlerts(alertRepository);
                if (drishtiService == null) {
                    drishtiService = new DrishtiService(new HTTPAgent(), "http://drishti.modilabs.org");
                }
                return new AlertController(drishtiService, allSettings, allAlerts, alertAdapter);
            }
        };

        lazyAlertController.getPayload().refreshAlertsOnView();
        updateAlerts();
    }

    private void updateAlerts() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    lazyAlertController.getPayload().fetchNewAlerts();
                    return null;
                }

                @Override
                protected void onPreExecute() {
                    final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    progressBar.setVisibility(VISIBLE);
                }

                @Override
                protected void onPostExecute(Void result) {
                    final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    progressBar.setVisibility(INVISIBLE);

                    lazyAlertController.getPayload().refreshAlertsOnView();
                }
            }.execute(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
                updateAlerts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
