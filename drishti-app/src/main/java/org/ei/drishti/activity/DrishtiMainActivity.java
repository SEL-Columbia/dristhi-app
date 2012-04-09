package org.ei.drishti.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import de.akquinet.android.androlog.Log;
import org.ei.drishti.R;
import org.ei.drishti.adapter.AlertAdapter;
import org.ei.drishti.domain.AlertAction;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.repository.SettingsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DrishtiMainActivity extends Activity {
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.init();
        Log.i(this, "Initializing ...");
        setContentView(R.layout.main);
        AllSettings allSettings = new AllSettings(new SettingsRepository(getApplicationContext()));

        final String y = allSettings.fetchANMIdentifier();

        Button button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    populateList(y);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void populateList(String anm) {
        List<AlertAction> mothers = new ArrayList<AlertAction>();
        mothers.add(new AlertAction(anm, "due", new HashMap<String, String>()));
        mothers.add(new AlertAction("Case Y", "late", new HashMap<String, String>()));
        listView.setAdapter(new AlertAdapter(this, R.layout.list_item, mothers));
    }
}

