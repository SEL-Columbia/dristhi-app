package org.ei.drishti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.domain.AlertAction;

import java.util.List;

public class AlertAdapter extends ArrayAdapter<AlertAction> {
    private List<AlertAction> alerts;

    public AlertAdapter(Context context, int listItemResourceId, List<AlertAction> alerts) {
        super(context, listItemResourceId, alerts);
        this.alerts = alerts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item, null);
        }
        AlertAction alert = alerts.get(position);
        TextView textView = (TextView) v.findViewById(R.id.textView1);
        textView.setText(alert.caseID());

        RatingBar bar = (RatingBar) v.findViewById(R.id.ratingBar1);
        if (alert.type().equals("due")) {
            bar.setNumStars(4);
            bar.setRating(1f);
        }
        return v;
    }
}
