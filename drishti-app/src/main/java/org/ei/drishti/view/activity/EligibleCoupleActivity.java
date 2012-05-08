package org.ei.drishti.view.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.PullToRefreshListView;
import org.ei.drishti.R;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.Displayable;
import org.ei.drishti.view.*;
import org.ei.drishti.view.adapter.AlertAdapter;
import org.ei.drishti.view.matcher.MatchByBeneficiaryOrThaayiCard;
import org.ei.drishti.view.matcher.MatchByTime;
import org.ei.drishti.view.matcher.MatchByVisitCode;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;
import static org.ei.drishti.domain.AlertFilterCriterionForTime.*;
import static org.ei.drishti.domain.AlertFilterCriterionForType.*;

public class EligibleCoupleActivity extends Activity {
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private UpdateAlertsTask updateAlerts;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        context = Context.getInstance().updateApplicationContext(this.getApplicationContext());

        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DrishtiMainActivity.class));
            }
        });

        final AlertAdapter alertAdapter = new AlertAdapter(this, org.ei.drishti.R.layout.list_item, new ArrayList<Alert>());
        updateAlerts = new UpdateAlertsTask(this, context.actionService(), context.alertController(alertAdapter), (ProgressBar) findViewById(org.ei.drishti.R.id.progressBar));

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
                context.userService().changeUser();
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

    private <T extends Displayable> DialogAction<T> createDialog(int icon, String title, T... options) {
        DialogAction<T> filterDialog = new DialogAction<T>(this, icon, title, options);
        ((ActionBar) findViewById(R.id.actionbar)).addAction(filterDialog);
        return filterDialog;
    }

}
