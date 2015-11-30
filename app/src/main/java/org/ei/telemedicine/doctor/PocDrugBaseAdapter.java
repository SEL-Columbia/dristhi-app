package org.ei.telemedicine.doctor;

import android.content.Context;
import android.graphics.Color;
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
public class PocDrugBaseAdapter extends BaseAdapter {
    ArrayList<PocDrugData> pocDrugDataList;
    Context context;

    public PocDrugBaseAdapter(Context context, ArrayList<PocDrugData> pocDrugDataList) {
        this.context = context;
        this.pocDrugDataList = pocDrugDataList;
    }

    @Override
    public int getCount() {
        return pocDrugDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return pocDrugDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView tv_name, tv_dosage, tv_frequency, tv_direction, tv_no_of_days, tv_qty;
        ImageButton ib_close;
        LinearLayout ll_item, ll_item_drug;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.poc_drugs_list_item, null);

        viewHolder = new ViewHolder();
        viewHolder.ll_item_drug = (LinearLayout) convertView.findViewById(R.id.ll_item);
        viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
//        viewHolder.tv_dosage = (TextView) convertView.findViewById(R.id.tv_dosage);
//        viewHolder.tv_direction = (TextView) convertView.findViewById(R.id.tv_direction);
//        viewHolder.tv_frequency = (TextView) convertView.findViewById(R.id.tv_frequnecy);
//        viewHolder.tv_no_of_days = (TextView) convertView.findViewById(R.id.tv_no_of_days);
//        viewHolder.tv_qty = (TextView) convertView.findViewById(R.id.tv_qty);
//

        viewHolder.ib_close = (ImageButton) convertView.findViewById(R.id.ib_remove);
        viewHolder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
        if (pocDrugDataList.get(position).isDrugDuplicate())
            viewHolder.ll_item_drug.setBackgroundColor(Color.parseColor("#177bbd"));

        viewHolder.tv_name.setText(pocDrugDataList.get(position).getDrugName() + "--" + pocDrugDataList.get(position).getDrugQty() + "--" + pocDrugDataList.get(position).getDosage() + "--" + pocDrugDataList.get(position).getDirection() + "--" + pocDrugDataList.get(position).getFrequncy() + "--" + pocDrugDataList.get(position).getDrugNoofDays());
//        viewHolder.tv_qty.setText(pocDrugDataList.get(position).getDrugQty());
//        viewHolder.tv_dosage.setText(pocDrugDataList.get(position).getDosage());
//        viewHolder.tv_direction.setText(pocDrugDataList.get(position).getDirection());
//        viewHolder.tv_frequency.setText(pocDrugDataList.get(position).getFrequncy());
//        viewHolder.tv_no_of_days.setText(pocDrugDataList.get(position).getDrugNoofDays());


//        viewHolder.tv_name.setText(pocDrugDataList.get(position).getDrugName() + " - " + pocDrugDataList.get(position).getDosage() + " - " + pocDrugDataList.get(position).getDirection() + " - " + pocDrugDataList.get(position).getFrequncy());
        viewHolder.ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pocDrugDataList.remove(position);
                DoctorPlanofCareActivity.pocDrugBaseAdapter.notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
