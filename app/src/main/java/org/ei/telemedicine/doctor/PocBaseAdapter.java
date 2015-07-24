package org.ei.telemedicine.doctor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.ei.telemedicine.R;

import java.util.ArrayList;

/**
 * Created by naveen on 6/1/15.
 */
public class PocBaseAdapter extends BaseAdapter {
    ArrayList<String> pocDataList;
    Context context;

    public PocBaseAdapter(Context context, ArrayList<String> pocDataList) {
        this.context = context;
        this.pocDataList = pocDataList;
    }

    @Override
    public int getCount() {
        return pocDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return pocDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView tv_name;
        ImageButton ib_close;
        LinearLayout ll_item;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.poc_diagnosis_list_item, null);

        viewHolder = new ViewHolder();
        viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        viewHolder.ib_close = (ImageButton) convertView.findViewById(R.id.ib_remove);
        viewHolder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);

        viewHolder.tv_name.setText(pocDataList.get(position).toString());
        viewHolder.ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pocDataList.remove(position);
                DoctorPlanofCareActivity.pocDiagnosisBaseAdapter.notifyDataSetChanged();
                DoctorPlanofCareActivity.pocTestBaseAdapter.notifyDataSetChanged();
                DoctorPlanofCareActivity.pocDrugBaseAdapter.notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
