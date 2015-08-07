package org.ei.telemedicine.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.ei.telemedicine.R;
import org.ei.telemedicine.adapter.OverviewTimelineAdapter;
import org.ei.telemedicine.domain.EligibleCouple;
import org.ei.telemedicine.view.customControls.CustomFontTextView;

import java.util.List;

public class NativeOverviewActivity extends Activity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {
    ImageButton ib_overview_options;
    ImageView iv_home;
    CustomFontTextView tv_mother_name, tv_woman_name, tv_priority, tv_priority_value;
    ListView lv_timeline_events;
    List<org.ei.telemedicine.domain.TimelineEvent> timelineEvents;
    String caseId;
    EligibleCouple eligibleCouple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_layout);
        if (getIntent().getExtras() != null) {
            caseId = getIntent().getExtras().getString("caseId");
            timelineEvents = org.ei.telemedicine.Context.getInstance().allTimelineEvents().forCase(caseId);
            eligibleCouple = org.ei.telemedicine.Context.getInstance().allEligibleCouples().findByCaseID(caseId);
            tv_priority_value = (CustomFontTextView) findViewById(R.id.tv_priority_value);
            tv_priority = (CustomFontTextView) findViewById(R.id.tv_priority);
            tv_mother_name = (CustomFontTextView) findViewById(R.id.tv_mother_name);
            tv_woman_name = (CustomFontTextView) findViewById(R.id.tv_woman_name);
            ib_overview_options = (ImageButton) findViewById(R.id.ib_overview_options);
            lv_timeline_events = (ListView) findViewById(R.id.lv_timeline_events);
            iv_home = (ImageView) findViewById(R.id.iv_home);
            ib_overview_options.setOnClickListener(this);
            iv_home.setOnClickListener(this);
            tv_mother_name.setText(eligibleCouple.wifeName().substring(0, 1).toUpperCase() + eligibleCouple.wifeName().substring(1));
            tv_woman_name.setText(eligibleCouple.wifeName().substring(0, 1).toUpperCase() + eligibleCouple.wifeName().substring(1));
            tv_priority.setTextColor(eligibleCouple.getDetail("isHighPriority") != null && eligibleCouple.getDetail("isHighPriority").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));
            tv_priority.setText(eligibleCouple.getDetail("isHighPriority") != null && eligibleCouple.getDetail("isHighPriority").equals("yes") ? "High Priority" : "Normal Priority");
            tv_priority_value.setText(eligibleCouple.getDetail("isHighPriority") != null && eligibleCouple.getDetail("isHighPriority").equals("yes") ? "High Priority" : "Normal Priority");
            tv_priority_value.setTextColor(eligibleCouple.getDetail("isHighPriority") != null && eligibleCouple.getDetail("isHighPriority").equals("yes") ? getResources().getColor(android.R.color.holo_red_dark) : getResources().getColor(android.R.color.holo_blue_dark));
            lv_timeline_events.setAdapter(new OverviewTimelineAdapter(this, timelineEvents));

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ec_settings:
                Toast.makeText(NativeOverviewActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_overview_options:
                PopupMenu popup = new PopupMenu(this, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.overview_ec_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(this);
                break;
            case R.id.iv_home:
                this.finish();
                break;
        }
    }
}
