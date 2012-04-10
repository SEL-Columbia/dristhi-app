package org.ei.drishti.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import de.akquinet.android.androlog.Log;
import org.ei.drishti.R;
import org.ei.drishti.adapter.AlertAdapter;
import org.ei.drishti.agent.HTTPAgent;
import org.ei.drishti.controller.AlertController;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.repository.*;
import org.ei.drishti.service.DrishtiService;

import java.util.ArrayList;

public class DrishtiMainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.init();
        Log.i(this, "Initializing ...");
        setContentView(R.layout.main);

        AlertAdapter alertAdapter = new AlertAdapter(this, R.layout.list_item, new ArrayList<Alert>());
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(alertAdapter);

        final AlertController alertController = createController(alertAdapter);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    alertController.refreshAlerts();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private AlertController createController(AlertAdapter alertAdapter) {
        SettingsRepository settingsRepository = new SettingsRepository();
        AlertRepository alertRepository = new AlertRepository();
        new Repository(getApplicationContext(), settingsRepository, alertRepository);
        AllSettings allSettings = new AllSettings(settingsRepository);
        AllAlerts allAlerts = new AllAlerts(alertRepository);
        DrishtiService drishtiService = new DrishtiService(new HTTPAgent(), "http://drishti.modilabs.org");
        return new AlertController(drishtiService, allSettings, allAlerts, alertAdapter);
    }
}

