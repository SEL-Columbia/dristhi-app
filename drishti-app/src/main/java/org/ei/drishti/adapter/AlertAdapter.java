package org.ei.drishti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.domain.Alert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AlertAdapter extends ArrayAdapter<Alert> {
    private List<Alert> alerts;

    public AlertAdapter(Context context, int listItemResourceId, List<Alert> alerts) {
        super(context, listItemResourceId, alerts);
        this.alerts = alerts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater viewInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = viewInflater.inflate(R.layout.list_item, null);
        }

        Alert alert = alerts.get(position);
        setTextView(view, R.id.beneficiaryName, alert.beneficiaryName());
        setTextView(view, R.id.dueDate, formattedDueDate(alert.dueDate()));
        setTextView(view, R.id.thaayiCardNo, alert.thaayiCardNo());
        setTextView(view, R.id.visitCode, alert.visitCode());

        return view;
    }

    private String formattedDueDate(String dueDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");

        try {
            return outputFormat.format(inputFormat.parse(dueDate));
        } catch (ParseException e) {
            return dueDate;
        }
    }

    public void refresh(List<Alert> alerts) {
        this.alerts.clear();
        this.alerts.addAll(alerts);
        this.notifyDataSetChanged();
    }

    private void setTextView(View v, int viewId, String text) {
        TextView beneficiaryName = (TextView) v.findViewById(viewId);
        beneficiaryName.setText(text);
    }
}
