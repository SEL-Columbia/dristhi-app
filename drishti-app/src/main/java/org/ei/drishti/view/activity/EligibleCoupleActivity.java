package org.ei.drishti.view.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
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
import org.ei.drishti.service.ActionService;
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

import static android.widget.RelativeLayout.TRUE;
import static android.widget.Toast.LENGTH_SHORT;
import static org.ei.drishti.domain.AlertFilterCriterionForTime.*;
import static org.ei.drishti.domain.AlertFilterCriterionForType.*;

public class EligibleCoupleActivity extends Activity {
    private static DrishtiService drishtiService;
    private UpdateAlertsTask updateAlerts;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private ActionBar actionBar;
    private AllSettings allSettings;
    private AllAlerts allAlerts;
    private AllEligibleCouples allEligibleCouples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DrishtiMainActivity.class));
            }
        });
        final AlertAdapter alertAdapter = new AlertAdapter(this, org.ei.drishti.R.layout.list_item, new ArrayList<Alert>());
        AlertController controller = setupController(alertAdapter);
        final ActionService actionService = setUpActionService();
        updateAlerts = new UpdateAlertsTask(this, actionService, controller, (ProgressBar) findViewById(org.ei.drishti.R.id.progressBar));

        initActionBar();

        AlertFilter filter = new AlertFilter(alertAdapter);
        filter.addFilter(new MatchByVisitCode(this, createDialog(org.ei.drishti.R.drawable.ic_tab_type, "Filter By Type", All, BCG, HEP, OPV)));
        filter.addFilter(new MatchByBeneficiaryOrThaayiCard((EditText) findViewById(org.ei.drishti.R.id.searchEditText)));
        filter.addFilter(new MatchByTime(this, createDialog(org.ei.drishti.R.drawable.ic_tab_clock, "Filter By Time", ShowAll, PastDue, Upcoming)));

        final PullToRefreshListView alertsList = (PullToRefreshListView) findViewById(org.ei.drishti.R.id.listView);
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

        alertsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.setComponent(ComponentName.unflattenFromString("org.commcare.android/.activities.CommCareHomeActivity"));
                intent.addCategory("android.intent.category.Launcher");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "CommCare ODK is not installed.", LENGTH_SHORT).show();
                }
            }
        });

        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                actionService.changeUser();
                Toast.makeText(getApplicationContext(), "Changes saved.", LENGTH_SHORT).show();
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
        inflater.inflate(org.ei.drishti.R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case org.ei.drishti.R.id.updateMenuItem:
                updateAlerts.updateFromServer();
                return true;
            case org.ei.drishti.R.id.settingsMenuItem:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public AlertController setupController(AlertAdapter alertAdapter) {
        SettingsRepository settingsRepository = new SettingsRepository();
        AlertRepository alertRepository = new AlertRepository();
        EligibleCoupleRepository eligibleCoupleRepository = new EligibleCoupleRepository();
        new Repository(getApplicationContext(), settingsRepository, alertRepository, eligibleCoupleRepository);

        allSettings = new AllSettings(PreferenceManager.getDefaultSharedPreferences(this), settingsRepository);
        allAlerts = new AllAlerts(alertRepository);
        allEligibleCouples = new AllEligibleCouples(eligibleCoupleRepository);
        if (drishtiService == null) {
            drishtiService = new DrishtiService(new HTTPAgent(), "http://drishti.modilabs.org");
        }

        return new AlertController(alertAdapter, allAlerts);
    }

    private ActionService setUpActionService() {
        return new ActionService(drishtiService, allSettings, allAlerts, allEligibleCouples);
    }

    private <T extends Displayable> DialogAction<T> createDialog(int icon, String title, T... options) {
        DialogAction<T> filterDialog = new DialogAction<T>(this, icon, title, options);
        actionBar.addAction(filterDialog);

        return filterDialog;
    }

    private void initActionBar() {
        actionBar = (ActionBar) findViewById(org.ei.drishti.R.id.actionbar);
        actionBar.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout actionsView = (LinearLayout) findViewById(org.ei.drishti.R.id.actionbar_actions);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) actionsView.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, TRUE);
    }
}
