package org.ei.drishti.provider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.view.activity.NativeECSmartRegisterActivity;
import org.ei.drishti.view.contract.ECClient;
import org.ei.drishti.view.contract.ECClients;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.text.MessageFormat.format;

public class SmartECRegisterClientsProvider
        implements SmartRegisterClientsProvider, View.OnClickListener {

    private final LayoutInflater inflater;
    private final Context context;
    protected ECClients clients;

    public SmartECRegisterClientsProvider(Context context, ECClients clients) {
        this.clients = clients;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(ECClient client, View convertView, ViewGroup viewGroup) {
        ViewGroup itemView;
        BaseViewHolder holder;
        if (convertView == null || isFooterView(convertView)) {
            itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_ec_client, null);
            holder = BaseViewHolder.getViewHolder(itemView);
            itemView.setTag(holder);
        } else {
            itemView = (ViewGroup) convertView;
            holder = (BaseViewHolder) itemView.getTag();
        }

        holder.txtNameView.setText(client.name());
        holder.txtHusbandNameView.setText(client.husbandName());
        holder.txtVillageNameView.setText(client.village());
        holder.txtAgeView.setText(
                format(context.getResources().getString(R.string.ec_register_wife_age), client.age()));
        holder.txtEcNumberView.setText(String.valueOf(client.ecNumber()));

        return itemView;
    }

    //#TODO: This is HACK, to identify the footer view while recycling the list items.
    private boolean isFooterView(View convertView) {
        return convertView instanceof RelativeLayout;
    }

    @Override
    public void onClick(View view) {
    }

    //#TODO: Very inefficient - Try some good algorithm
    @Override
    public List<String> getAllUniqueVillageNames() {
        List<String> villages = new ArrayList<String>();
        for (ECClient client : clients) {
            String village = client.village();
            if (!isVillageNameAlreadyPresent(villages, village)) {
                villages.add(village);
            }
        }
        return villages;
    }

    @Override
    public ECClients getListItems() {
        return clients;
    }

    @Override
    public void sort(String sortBy) {
        Collections.sort(clients, getComparator(sortBy));
    }

    @Override
    public ECClients filter(CharSequence cs) {
        ECClients results;
        if (cs.length() > 0) {
            String filter = cs.toString().toLowerCase();
            ECClients filteredPeople = new ECClients();
            for (ECClient aPeople : clients) {
                if (aPeople.willFilterMatches(filter)) {
                    filteredPeople.add(aPeople);
                }
            }
            results = filteredPeople;
        } else {
            results = clients;
        }
        return results;
    }

    @Override
    public ViewGroup getFooterView() {
        return (ViewGroup) inflater.inflate(R.layout.smart_register_pagination, null);
    }

    public LayoutInflater inflater() {
        return inflater;
    }

    public Context context() {
        return context;
    }

    @Override
    public void showSection(String section) {
        // do Nothing;
    }

    private boolean isVillageNameAlreadyPresent(List<String> villages, String newVillage) {
        boolean found = false;
        for (String village : villages) {
            if (village.equalsIgnoreCase(newVillage)) {
                found = true;
            }
        }
        return found;
    }

    private Comparator<? super ECClient> getComparator(String sortBy) {
        if (sortBy.equalsIgnoreCase(NativeECSmartRegisterActivity.SORT_BY_EC_NO)) {
            return ECClient.EC_NUMBER_COMPARATOR;
        }
        return ECClient.NAME_COMPARATOR;
    }

    protected static class BaseViewHolder {
        TextView txtNameView;
        TextView txtHusbandNameView;
        TextView txtVillageNameView;
        TextView txtAgeView;
        TextView txtEcNumberView;

        public static BaseViewHolder getViewHolder(ViewGroup itemView) {
            BaseViewHolder viewHolder = new BaseViewHolder();
            viewHolder.txtNameView = (TextView) itemView.findViewById(R.id.txt_person_name);
            viewHolder.txtHusbandNameView = (TextView) itemView.findViewById(R.id.txt_husband_name);
            viewHolder.txtVillageNameView = (TextView) itemView.findViewById(R.id.txt_village_name);
            viewHolder.txtAgeView = (TextView) itemView.findViewById(R.id.txt_age);
            viewHolder.txtEcNumberView = (TextView) itemView.findViewById(R.id.txt_ec_number);
            return viewHolder;
        }
    }
}
