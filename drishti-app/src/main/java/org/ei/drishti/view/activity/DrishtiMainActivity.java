package org.ei.drishti.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.*;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.PullToRefreshListView;
import org.ei.drishti.R;
import org.ei.drishti.controller.AlertController;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.Displayable;
import org.ei.drishti.repository.*;
import org.ei.drishti.service.DrishtiService;
import org.ei.drishti.service.HTTPAgent;
import org.ei.drishti.view.AfterChangeListener;
import org.ei.drishti.view.AlertFilter;
import org.ei.drishti.view.DialogAction;
import org.ei.drishti.view.UpdateAlertsTask;
import org.ei.drishti.view.adapter.AlertAdapter;
import org.ei.drishti.view.matcher.MatchByBeneficiaryOrThaayiCard;
import org.ei.drishti.view.matcher.MatchByTime;
import org.ei.drishti.view.matcher.MatchByVisitCode;

import java.util.ArrayList;

import static android.widget.RelativeLayout.LayoutParams;
import static android.widget.RelativeLayout.TRUE;
import static org.ei.drishti.domain.AlertFilterCriterionForTime.*;
import static org.ei.drishti.domain.AlertFilterCriterionForType.*;
import static org.ei.drishti.util.Log.logVerbose;

public class DrishtiMainActivity extends Activity {
    private static DrishtiService drishtiService;
    private UpdateAlertsTask updateAlerts;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private AlertController controller;
    private ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logVerbose("Initializing ...");
        setContentView(R.layout.main);

        final AlertAdapter alertAdapter = new AlertAdapter(this, R.layout.list_item, new ArrayList<Alert>());
        controller = setupController(alertAdapter);
        updateAlerts = new UpdateAlertsTask(this, controller, (ProgressBar) findViewById(R.id.progressBar));

        initActionBar();

        AlertFilter filter = new AlertFilter(alertAdapter);
        filter.addFilter(new MatchByVisitCode(this, createDialog(R.drawable.ic_tab_type, "Filter By Type", All, BCG, HEP, OPV)));
        filter.addFilter(new MatchByBeneficiaryOrThaayiCard((EditText) findViewById(R.id.searchEditText)));
        filter.addFilter(new MatchByTime(this, createDialog(R.drawable.ic_tab_clock, "Filter By Time", ShowAll, PastDue, Upcoming)));

        final PullToRefreshListView alertsList = (PullToRefreshListView) findViewById(R.id.listView);
        alertsList.setAdapter(alertAdapter);
        alertsList.setTextFilterEnabled(true);

        alertsList.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            public void onRefresh() {
                updateAlerts.updateFromServer(new AfterChangeListener() {
                    public void afterChangeHappened() {
                        alertsList.onRefreshComplete();
                    }
                });
            }
        });

        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                controller.changeUser();
                Toast.makeText(getApplicationContext(), "Changes saved.", Toast.LENGTH_SHORT).show();
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

    private <T extends Displayable> DialogAction<T> createDialog(int icon, String title, T... options) {
        DialogAction<T> filterDialog = new DialogAction<T>(this, icon, title, options);
        actionBar.addAction(filterDialog);

        return filterDialog;
    }

    private void initActionBar() {
        actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout actionsView = (LinearLayout) findViewById(R.id.actionbar_actions);
        LayoutParams layoutParams = (LayoutParams) actionsView.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, TRUE);
    }
}
