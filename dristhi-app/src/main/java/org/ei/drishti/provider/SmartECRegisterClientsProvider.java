package org.ei.drishti.provider;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.view.activity.NativeECSmartRegisterActivity;
import org.ei.drishti.view.contract.ECChildClient;
import org.ei.drishti.view.contract.ECClient;
import org.ei.drishti.view.contract.ECClients;

import java.util.*;

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
        ECClientViewHolder holder;
        if (convertView == null) {
            itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_ec_client, null);
            holder = ECClientViewHolder.getViewHolder(itemView);
            itemView.setTag(holder);
        } else {
            itemView = (ViewGroup) convertView;
            holder = (ECClientViewHolder) itemView.getTag();
        }

        setupClientProfileView(client, holder);

        holder.gplsaView.setText(client.gplsa());

        setupFPMethodView(client, holder);

        setupChildrenView(client, holder);

        setupStatusView(client, holder);

        return itemView;
    }

    private void setupFPMethodView(ECClient client, ECClientViewHolder holder) {
        String fpMethod = client.FPMethod();
        if (fpMethod == null || fpMethod.isEmpty()) {
            fpMethod = context().getResources().getString(R.string.ec_register_no_fp);
            holder.FPMethodView.setTextColor(Color.RED);
        } else {
            holder.FPMethodView.setTextColor(Color.BLACK);
        }
        holder.FPMethodView.setText(fpMethod);
    }

    private void setupStatusView(ECClient client, ECClientViewHolder holder) {
        Map<String, String> status = client.status();
        Set<Map.Entry<String, String>> statusSet = status.entrySet();
        Iterator statusIterator = statusSet.iterator();

        String[] values = new String[statusSet.size()];
        int i = 0;
        while (statusIterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) statusIterator.next();
            String key = entry.getKey();
            String value = entry.getValue();

            if (key != null && value != null && !value.isEmpty()) {
                values[i++] = value;
            }
        }
        holder.statusView.setText(
                format(context.getResources().getString(R.string.ec_register_status), "a", "b", "c"));
    }

    private void setupClientProfileView(ECClient client, ECClientViewHolder holder) {
        holder.txtNameView.setText(client.name());
        holder.txtHusbandNameView.setText(client.husbandName());
        holder.txtVillageNameView.setText(client.village());
        holder.txtAgeView.setText(
                format(context.getResources().getString(R.string.ec_register_wife_age), client.age()));
        holder.txtEcNumberView.setText(String.valueOf(client.ecNumber()));
        holder.badgeHPView.setVisibility(client.isHighPriority() ? View.VISIBLE : View.GONE);
        holder.badgeBPLView.setVisibility(client.isBPL() ? View.VISIBLE : View.GONE);
        holder.badgeSCView.setVisibility(client.isSC() ? View.VISIBLE : View.GONE);
        holder.badgeSTView.setVisibility(client.isST() ? View.VISIBLE : View.GONE);
    }

    private void setupChildrenView(ECClient client, ECClientViewHolder holder) {
        List<ECChildClient> children = client.children();
        if (children.size() == 0) {
            holder.maleChildrenView.setVisibility(View.GONE);
            holder.femaleChildrenView.setVisibility(View.GONE);
        } else if (children.size() == 1) {
            ECChildClient child = children.get(0);
            setupChildView(holder, child);
            if (child.isMale()) {
                holder.femaleChildrenView.setVisibility(View.GONE);
                ((LinearLayout.LayoutParams) holder.maleChildrenView.getLayoutParams()).weight = 100;
            } else {
                holder.maleChildrenView.setVisibility(View.GONE);
                ((LinearLayout.LayoutParams) holder.femaleChildrenView.getLayoutParams()).weight = 100;
            }
        } else {
            ((LinearLayout.LayoutParams) holder.maleChildrenView.getLayoutParams()).weight = 50;
            ((LinearLayout.LayoutParams) holder.femaleChildrenView.getLayoutParams()).weight = 50;
            setupChildView(holder, children.get(0));
            setupChildView(holder, children.get(1));
        }
    }

    private void setupChildView(ECClientViewHolder holder, ECChildClient child) {
        if (child.isMale()) {
            holder.maleChildrenView.setVisibility(View.VISIBLE);
            holder.maleChildrenView.setText(
                    format(context.getResources().getString(R.string.ec_register_male_child),
                            child.getAgeInMonths()));
        } else {
            holder.femaleChildrenView.setVisibility(View.VISIBLE);
            holder.femaleChildrenView.setText(
                    format(context.getResources().getString(R.string.ec_register_female_child),
                            child.getAgeInMonths()));
        }
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

    protected static class ECClientViewHolder {
        TextView txtNameView;
        TextView txtHusbandNameView;
        TextView txtVillageNameView;
        TextView txtAgeView;
        TextView txtEcNumberView;
        ImageView badgeHPView;
        ImageView badgeBPLView;
        ImageView badgeSCView;
        ImageView badgeSTView;
        TextView gplsaView;
        TextView FPMethodView;
        TextView maleChildrenView;
        TextView femaleChildrenView;
        TextView statusView;

        public static ECClientViewHolder getViewHolder(ViewGroup itemView) {
            ECClientViewHolder viewHolder = new ECClientViewHolder();

            viewHolder.txtNameView = (TextView) itemView.findViewById(R.id.txt_person_name);
            viewHolder.txtHusbandNameView = (TextView) itemView.findViewById(R.id.txt_husband_name);
            viewHolder.txtVillageNameView = (TextView) itemView.findViewById(R.id.txt_village_name);
            viewHolder.txtAgeView = (TextView) itemView.findViewById(R.id.txt_age);
            viewHolder.txtEcNumberView = (TextView) itemView.findViewById(R.id.txt_ec_number);
            viewHolder.badgeHPView = (ImageView) itemView.findViewById(R.id.img_hp_badge);
            viewHolder.badgeBPLView = (ImageView) itemView.findViewById(R.id.img_bpl_badge);
            viewHolder.badgeSCView = (ImageView) itemView.findViewById(R.id.img_sc_badge);
            viewHolder.badgeSTView = (ImageView) itemView.findViewById(R.id.img_st_badge);
            viewHolder.gplsaView = (TextView) itemView.findViewById(R.id.txt_gplsa);
            viewHolder.FPMethodView = (TextView) itemView.findViewById(R.id.txt_fp);
            viewHolder.maleChildrenView = (TextView) itemView.findViewById(R.id.txt_male_children);
            viewHolder.femaleChildrenView = (TextView) itemView.findViewById(R.id.txt_female_children);
            viewHolder.statusView = (TextView) itemView.findViewById(R.id.txt_status);

            return viewHolder;
        }
    }
}
