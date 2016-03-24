package org.ei.telemedicine.doctor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import org.ei.telemedicine.R;

import java.util.ArrayList;

///**
// * Created by naveen on 6/2/15.
// */
public class DiagnosisArrayAdapter extends ArrayAdapter {
    ArrayList<PocDiagnosis> dataList;
    ArrayList<PocDiagnosis> dataListAll;
    ArrayList<PocDiagnosis> suggestions;
    private String TAG = "DiagnosisArrayAdapter";
    Context context;
    int resource;

    public DiagnosisArrayAdapter(Context context, int resource, ArrayList<PocDiagnosis> datalist) {
        super(context, resource, datalist);
        Log.e(TAG, "Diagnosis Size" + datalist.size());
        this.dataList = datalist;
        this.context = context;
        this.resource = resource;
        this.suggestions = new ArrayList<PocDiagnosis>();
        this.dataListAll = (ArrayList<PocDiagnosis>) datalist.clone();
    }

    private class ViewHolder {
        TextView tv_name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resource, null);
        ViewHolder holder = new ViewHolder();
        holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        holder.tv_name.setText(dataList.get(position).getIcd10_name().toString());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (PocDiagnosis pocDiagnosis : dataListAll) {
                    if (pocDiagnosis.getIcd10_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(pocDiagnosis);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((PocDiagnosis) (resultValue)).getIcd10_name();
            return str;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            ArrayList<PocDiagnosis> filteredList = (ArrayList<PocDiagnosis>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (PocDiagnosis pocDiagnosis : filteredList) {
                    add(pocDiagnosis);
                }
                notifyDataSetChanged();
            }
        }
    };
}


