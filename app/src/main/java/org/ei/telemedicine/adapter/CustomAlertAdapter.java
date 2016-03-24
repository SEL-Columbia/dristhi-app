//package org.ei.telemedicine.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import org.ei.telemedicine.R;
//
//import java.util.ArrayList;
//
//public class CustomAlertAdapter extends BaseAdapter {
//
//    Context ctx = null;
//    ArrayList<String> listarray = null;
//    private LayoutInflater mInflater = null;
//
//    public CustomAlertAdapter(Context context, ArrayList<String> list) {
//        this.ctx = context;
//        mInflater = activty.getLayoutInflater();
//        this.listarray = list;
//    }
//
//    @Override
//    public int getCount() {
//
//        return listarray.size();
//    }
//
//    @Override
//    public Object getItem(int arg0) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int arg0) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup arg2) {
//        final ViewHolder holder;
//        if (convertView == null) {
//            holder = new ViewHolder();
//            convertView = mInflater.inflate(R.layout.custom_village_list_item, null);
//
//            holder.titlename = (TextView) convertView.findViewById(R.id.tv_village_name);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        String datavalue = listarray.get(position);
//        holder.titlename.setText(datavalue);
//
//        return convertView;
//    }
//
//    private static class ViewHolder {
//        TextView titlename;
//    }
//}