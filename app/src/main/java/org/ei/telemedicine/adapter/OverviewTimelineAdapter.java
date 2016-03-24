package org.ei.telemedicine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.ei.telemedicine.R;
import org.ei.telemedicine.domain.TimelineEvent;

import java.util.List;

/**
 * Adapter for timeline event in overview screen
 */
public class OverviewTimelineAdapter extends BaseAdapter {
    List<TimelineEvent> timelineEvents;
    Context context;

    public OverviewTimelineAdapter(Context context, List<TimelineEvent> timelineEvents) {
        this.context = context;
        this.timelineEvents = timelineEvents;
    }

    @Override
    public int getCount() {
        return timelineEvents.size();
    }

    @Override
    public TimelineEvent getItem(int position) {
        return timelineEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView tv_event_name, tv_event_info, tv_event_date;
        ImageView iv_event_logo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.overview_list_item, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tv_event_name = (TextView) convertView.findViewById(R.id.tv_event_name);
        viewHolder.tv_event_info = (TextView) convertView.findViewById(R.id.tv_event_info);
        viewHolder.tv_event_date = (TextView) convertView.findViewById(R.id.tv_event_date);
        viewHolder.iv_event_logo = (ImageView) convertView.findViewById(R.id.iv_event_logo);
        switch (timelineEvents.get(position).type()) {
            case "ECREGISTERED":
                viewHolder.iv_event_logo.setImageResource(R.drawable.icon_event_ec);
                break;
            case "PREGNANCY":
                viewHolder.iv_event_logo.setImageResource(R.drawable.icon_event_anc);
                break;
            case "CHILD-BIRTH":
                viewHolder.iv_event_logo.setImageResource(R.drawable.icon_event_birth);
                break;
            case "CHILD-DELIVERYPLAN":
                viewHolder.iv_event_logo.setImageResource(R.drawable.icon_event_birth);
                break;
            case "FPCHANGE":
                viewHolder.iv_event_logo.setImageResource(R.drawable.icon_event_fp);
                break;
            case "ANCVISIT":
                viewHolder.iv_event_logo.setImageResource(R.drawable.icon_event_anc);
                break;
            case "IFAPROVIDED":
                viewHolder.iv_event_logo.setImageResource(R.drawable.icon_event_ifa);
                break;
            case "TTSHOTPROVIDED":
                viewHolder.iv_event_logo.setImageResource(R.drawable.icon_event_inject);
                break;
            case "PNCVISIT":
                viewHolder.iv_event_logo.setImageResource(R.drawable.icon_event_pnc);
                break;
            case "IMMUNIZATIONSGIVEN":
                viewHolder.iv_event_logo.setImageResource(R.drawable.icon_event_inject);
                break;
            case "FPRENEW":
                viewHolder.iv_event_logo.setImageResource(R.drawable.icon_event_fp);
                break;
            default:
                viewHolder.iv_event_logo.setImageResource(R.drawable.icon_event_pnc);
                break;
        }
        viewHolder.tv_event_name.setText(timelineEvents.get(position).title());
        viewHolder.tv_event_info.setText(timelineEvents.get(position).detail1());
        viewHolder.tv_event_date.setText(timelineEvents.get(position).referenceDate() + "");

        return convertView;
    }
}
