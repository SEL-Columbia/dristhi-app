package org.ei.drishti.view.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.markupartist.android.widget.PullToRefreshListView;
import org.ei.drishti.R;
import org.ei.drishti.controller.EligibleCoupleController;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.view.AfterFetchListener;
import org.ei.drishti.view.ItemFilter;
import org.ei.drishti.view.UpdateActionsTask;
import org.ei.drishti.view.adapter.ListAdapter;
import org.ei.drishti.view.matcher.MatchECByWifeNameOrNumber;

import java.util.ArrayList;

import static org.ei.drishti.domain.FetchStatus.fetched;

public class EligibleCoupleActivity extends SecuredActivity {
    private UpdateActionsTask updateECTask;
    private EligibleCoupleController controller;
    private ItemFilter<EligibleCouple> itemFilter;

    @Override
    protected void onCreation() {
        setContentView(R.layout.main);

        findViewById(R.id.switchViewButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
                finish();
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
                    public void afterFetch(FetchStatus status) {
                        if (fetched.equals(status)) {
                            controller.refreshECFromDB();
                        }
                        ecList.onRefreshComplete();
                    }
                });
            }
        });
        ecList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                EligibleCouple item = (EligibleCouple) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), EligibleCoupleViewActivity.class);
                intent.putExtra("wifeName", item.wifeName());
                intent.putExtra("caseId", item.caseId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResumption() {
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateECs() {
        updateECTask.updateFromServer(new AfterFetchListener() {
            public void afterFetch(FetchStatus status) {
                if (fetched.equals(status)) {
                    controller.refreshECFromDB();
                }
            }
        });
    }
}
