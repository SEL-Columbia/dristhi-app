package org.ei.drishti.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.PullToRefreshListView;
import org.ei.drishti.R;
import org.ei.drishti.controller.EligibleCoupleController;
import org.ei.drishti.domain.Displayable;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.view.*;
import org.ei.drishti.view.adapter.ListAdapter;
import org.ei.drishti.view.matcher.MatchECByWifeNameOrNumber;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;
import static org.ei.drishti.domain.FetchStatus.fetched;

public class EligibleCoupleActivity extends Activity {
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private UpdateActionsTask updateECTask;
    private Context context;
    private EligibleCoupleController controller;
    private ItemFilter<EligibleCouple> itemFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        context = Context.getInstance().updateApplicationContext(this.getApplicationContext());

        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
            }
        });

        EditText searchBox = (EditText) findViewById(R.id.searchEditText);
        searchBox.setHint("Search Couples");
        final ListAdapter<EligibleCouple> ecAdapter = new ListAdapter<EligibleCouple>(this, R.layout.list_item, new ArrayList<EligibleCouple>()) {
            @Override
            protected void populateListItem(View view, EligibleCouple item) {
                setTextView(view, R.id.leftTop, item.wifeName());
                setTextView(view, R.id.rightTop, item.ecNumber());
                setTextView(view, R.id.leftBottom, item.husbandName());
            }

            private void setTextView(View v, int viewId, String text) {
                ((TextView) v.findViewById(viewId)).setText(text);
            }
        };
        controller = context.eligibleCoupleController(ecAdapter);
        updateECTask = new UpdateActionsTask(this, context.actionService(), (ProgressBar) findViewById(org.ei.drishti.R.id.progressBar));
        itemFilter = new ItemFilter<EligibleCouple>(ecAdapter);
        itemFilter.addFilter(new MatchECByWifeNameOrNumber(searchBox));

        final PullToRefreshListView ecList = (PullToRefreshListView) findViewById(org.ei.drishti.R.id.listView);
        ecList.setAdapter(ecAdapter);
        ecList.setTextFilterEnabled(true);

        ecList.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            public void onRefresh() {
                updateECTask.updateFromServer(new AfterFetchListener() {
                    public void afterChangeHappened(FetchStatus status) {
                        if (fetched.equals(status)) {
                            controller.refreshECFromDB();
                        }
                        ecList.onRefreshComplete();
                    }
                });
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
        controller.refreshECFromDB();
        updateECs();
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
                updateECs();
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

    private void updateECs() {
        updateECTask.updateFromServer(new AfterFetchListener() {
            public void afterChangeHappened(FetchStatus status) {
                if (fetched.equals(status)) {
                    controller.refreshECFromDB();
                }
            }
        });
    }
}
